package com.example.stockmap.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

object PreferenceKeys{
    val WAREHOUSE_NAME = stringPreferencesKey("warehouse_name")
    val ROW_COUNT = intPreferencesKey("row_count")
    val SHELVES_COUNT = intPreferencesKey("shelves_count")
    val BIN_COUNT = intPreferencesKey("bin_count")
}

class WarehousePreferences @Inject constructor(private val dataStore: DataStore<Preferences>){

    val name: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.WAREHOUSE_NAME] ?: ""
    }
    val rows: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.ROW_COUNT] ?: 0
    }
    val shelves: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.SHELVES_COUNT] ?: 0
    }
    val bins: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.BIN_COUNT] ?: 0
    }

    suspend fun addName(name: String){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.WAREHOUSE_NAME] = name
        }
    }

    suspend fun addRows(rows: Int){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.ROW_COUNT] = rows
        }
    }

    suspend fun addShelves(shelves: Int){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.SHELVES_COUNT] = shelves
        }
    }

    suspend fun addBins(bins: Int){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.BIN_COUNT] = bins
        }
    }

}
