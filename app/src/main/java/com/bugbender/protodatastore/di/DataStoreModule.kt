package com.bugbender.protodatastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import com.bugbender.protodatastore.data.UserPreferencesSerializer
import com.bugbender.protodatastore.proto.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val DATA_STORE_FILE_NAME = "user_preferences.pb"

    @Singleton
    @Provides
    fun provideProtoDataStore(@ApplicationContext context: Context): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = { context.dataStoreFile(DATA_STORE_FILE_NAME) }
        )
    }
}