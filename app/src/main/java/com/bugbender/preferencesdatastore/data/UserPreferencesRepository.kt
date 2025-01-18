package com.bugbender.preferencesdatastore.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
) {

    val userPreferences: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            Log.d("k0dm", "error ${exception.message}")

            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            Log.d("k0dm", "map $preferences")
            val showCompleted = preferences[SHOW_COMPLETED] ?: false
            val sortOrder = SortOrder.valueOf(preferences[SORT_ORDER] ?: SortOrder.NONE.name)
            UserPreferences(showCompleted = showCompleted, sortOrder = sortOrder)
        }

    suspend fun fetchInitialPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())


    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        // Get the sort order from preferences and convert it to a [SortOrder] object
        val sortOrder = SortOrder.valueOf(
            preferences[SORT_ORDER] ?: SortOrder.NONE.name
        )

        // Get our show completed value, defaulting to false if not set:
        val showCompleted = preferences[SHOW_COMPLETED] ?: false
        return UserPreferences(showCompleted, sortOrder)
    }

    suspend fun updateShowCompleted(showCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[SHOW_COMPLETED] = showCompleted
        }
    }

    suspend fun enableSortByDeadline(enable: Boolean) {
        // edit handles data transactionally, ensuring that if the sort is updated at the same
        // time from another thread, we won't have conflicts
        Log.d("k0dm", "$enable enableSortByDeadline")
        dataStore.edit { preferences ->
            // Get the current SortOrder as an enum
            val currentOrder = SortOrder.valueOf(
                preferences[SORT_ORDER] ?: SortOrder.NONE.name
            )

            val newSortOrder =
                if (enable) {
                    if (currentOrder == SortOrder.BY_PRIORITY) {
                        SortOrder.BY_DEADLINE_AND_PRIORITY
                    } else {
                        SortOrder.BY_DEADLINE
                    }
                } else {
                    if (currentOrder == SortOrder.BY_DEADLINE_AND_PRIORITY) {
                        SortOrder.BY_PRIORITY
                    } else {
                        SortOrder.NONE
                    }
                }
            preferences[SORT_ORDER] = newSortOrder.name
        }
    }

    suspend fun enableSortByPriority(enable: Boolean) {
        // edit handles data transactionally, ensuring that if the sort is updated at the same
        // time from another thread, we won't have conflicts
        Log.d("k0dm", "$enable enableSortByPriority")

        dataStore.edit { preferences ->
            // Get the current SortOrder as an enum
            val currentOrder = SortOrder.valueOf(
                preferences[SORT_ORDER] ?: SortOrder.NONE.name
            )

            val newSortOrder =
                if (enable) {
                    if (currentOrder == SortOrder.BY_DEADLINE) {
                        SortOrder.BY_DEADLINE_AND_PRIORITY
                    } else {
                        SortOrder.BY_PRIORITY
                    }
                } else {
                    if (currentOrder == SortOrder.BY_DEADLINE_AND_PRIORITY) {
                        SortOrder.BY_DEADLINE
                    } else {
                        SortOrder.NONE
                    }
                }
            preferences[SORT_ORDER] = newSortOrder.name
        }
    }

    companion object {
        val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
        val SORT_ORDER = stringPreferencesKey("sort_order")
    }
}