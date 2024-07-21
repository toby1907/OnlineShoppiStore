package com.example.onlineshoppistore.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

data class FavoriteItem(val id: String)
class FavoriteDataStore(private val dataStore: DataStore<Preferences>) {
    object PreferencesKeys {
        val FAVORITES = stringSetPreferencesKey("favorite_items")
    }

    suspend fun addFavorite(item: FavoriteItem) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITES] ?: emptySet()
            val updatedFavorites = currentFavorites + item.id
            preferences[PreferencesKeys.FAVORITES] = updatedFavorites
        }
    }
    suspend fun removeFavorite( item: FavoriteItem) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITES] ?: emptySet()
            preferences[PreferencesKeys.FAVORITES] = currentFavorites - item.id
            Log.d("datastore","${preferences[PreferencesKeys.FAVORITES]}")
        }
    }
    fun getFavoriteItemsFlow(): Flow<List<FavoriteItem>> {
        return dataStore.data.map { preferences ->
            val favoriteIds = preferences[PreferencesKeys.FAVORITES] ?: emptySet()
            favoriteIds.map { FavoriteItem(it) }
        }
    }
}