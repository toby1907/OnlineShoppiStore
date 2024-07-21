package com.example.onlineshoppistore.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.onlineshoppistore.data.datastore.FavoriteDataStore
import com.example.onlineshoppistore.data.room.CartDatabase
import com.example.onlineshoppistore.data.room.CartRepositoryImpl
import com.example.onlineshoppistore.dataStore
import com.example.onlineshoppistore.network.ApiRepository
import com.example.onlineshoppistore.network.ApiService
import com.example.onlineshoppistore.network.ServiceBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext



    @Module
    // Specifies that the lifetime of dependencies provided by this module will be the same as the application's.
    @InstallIn(SingletonComponent::class)
    object DataModule {

        // Provides a singleton instance of ApiService to be used across the application.
        @Provides
        @Singleton
        fun provideApiService(): ApiService {
            return ServiceBuilder.apiService
        }

        // Provides a singleton instance of CharacterRepository. It takes an ApiService instance as a parameter.
        @Provides
        @Singleton
        fun provideCharacterRepository(apiService: ApiService): ApiRepository {
            return ApiRepository(apiService)
        }

        @Provides
        @Singleton
        fun provideGoalDatabase(app: Application): CartDatabase {
            return Room.databaseBuilder(app.applicationContext, CartDatabase::class.java, "cart_database")
                .build()
        }

        @Provides
        @Singleton
        fun provideGoalRepository(db: CartDatabase): CartRepositoryImpl {
            return CartRepositoryImpl(db.cartDao())
        }

        @Provides
        @Singleton
        fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            return context.dataStore
        }

        @Provides
        @Singleton
        fun provideSettingsRepository(dataStore: DataStore<Preferences>): FavoriteDataStore {
            return FavoriteDataStore(dataStore)
        }
    }


}