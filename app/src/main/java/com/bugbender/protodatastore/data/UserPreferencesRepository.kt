package com.bugbender.protodatastore.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException

import kotlinx.coroutines.flow.catch

class UserPreferencesRepository(
    private val dataStore: DataStore<UserPreferences>
) {

    val userPreferences = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(UserPreferences(showCompleted = false, sortOrder = SortOrder.NONE))
            } else {
                throw exception
            }
        }

    suspend fun updateShowCompleted(showCompleted: Boolean) {
        dataStore.updateData { preferences ->
            preferences.copy(showCompleted = showCompleted)
        }
    }

    suspend fun enableSortByDeadline(enable: Boolean) {
        dataStore.updateData { currentPreferences ->
            val currentOrder = currentPreferences.sortOrder
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
            currentPreferences.copy(sortOrder = newSortOrder)
        }
    }

    suspend fun enableSortByPriority(enable: Boolean) {
        dataStore.updateData { currentPreferences ->
            val currentOrder = currentPreferences.sortOrder
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

            currentPreferences.copy(sortOrder = newSortOrder)
        }
    }
}