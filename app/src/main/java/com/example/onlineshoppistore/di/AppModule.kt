package com.example.onlineshoppistore.di

import android.app.Application
import android.content.Context
import com.example.onlineshoppistore.network.ApiRepository
import com.example.onlineshoppistore.network.ApiService
import com.example.onlineshoppistore.network.ServiceBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    }


}