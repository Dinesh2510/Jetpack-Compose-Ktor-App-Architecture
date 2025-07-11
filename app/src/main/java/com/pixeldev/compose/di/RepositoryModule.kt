package com.pixeldev.compose.di

import com.pixeldev.compose.data.local.DataStoreManager
import com.pixeldev.compose.data.remote.ApiService
import com.pixeldev.compose.data.repository.LoginRepositoryImpl
import com.pixeldev.compose.data.repository.QuoteRepositoryImpl
import com.pixeldev.compose.data.repository.UploadRepositoryImpl
import com.pixeldev.compose.domain.repository.LoginRepository
import com.pixeldev.compose.domain.repository.QuoteRepository
import com.pixeldev.compose.domain.repository.UploadRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(
        apiService: ApiService,
        dataStoreManager: DataStoreManager
    ): LoginRepository {
        return LoginRepositoryImpl(apiService, dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideUploadRepository(
        apiService: ApiService
    ): UploadRepository {
        return UploadRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideQuoteRepository(
        apiService: ApiService
    ): QuoteRepository {
        return QuoteRepositoryImpl(apiService)
    }
}
