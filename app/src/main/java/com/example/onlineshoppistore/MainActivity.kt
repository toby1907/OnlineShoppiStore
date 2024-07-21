package com.example.onlineshoppistore

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import com.example.onlineshoppistore.ui.HomeScreen
import com.example.onlineshoppistore.ui.theme.OnlineShoppiStoreTheme
import dagger.hilt.android.AndroidEntryPoint

    private const val FAVORITE_ITEM = "favorite_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FAVORITE_ITEM)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnlineShoppiStoreTheme {
                // A surface container using the 'background' color from the theme
               Nav()
            }
        }
    }
}

