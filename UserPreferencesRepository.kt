package com.example.splashmaniaapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Add data store as param
class UserPreferencesRepository constructor(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        // Define a key for expand all boolean value
        val EXPAND_ALL_KEY = booleanPreferencesKey("expand_all")
    }

    // Read the value out from data store
    val expandAll: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[EXPAND_ALL_KEY] ?: false // Default to false if not set
        }

    // Create suspend function to save expand all boolean value into data store
    suspend fun setExpandAll(expandAll: Boolean) {
        dataStore.edit { preferences ->
            preferences[EXPAND_ALL_KEY] = expandAll
        }
    }
}