package com.bugbender.protodatastore.di

import android.view.View
import androidx.datastore.core.DataStore
import com.bugbender.protodatastore.data.UserPreferences
import com.bugbender.protodatastore.data.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class TaskModule {

    @Provides
    fun userPreferencesRepository(dataStore: DataStore<UserPreferences>): UserPreferencesRepository {
        return UserPreferencesRepository(dataStore = dataStore)
    }

}