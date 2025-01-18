package com.bugbender.preferencesdatastore.ui

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)