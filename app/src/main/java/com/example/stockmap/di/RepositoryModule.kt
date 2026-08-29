package com.example.stockmap.di

import com.example.stockmap.data.repository.BinRepositoryImpl
import com.example.stockmap.data.repository.ProductRepositoryImpl
import com.example.stockmap.data.repository.SettingsRepositoryImpl
import com.example.stockmap.domain.repository.BinRepository
import com.example.stockmap.domain.repository.ProductRepository
import com.example.stockmap.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    abstract fun bindBinRepository(
        impl: BinRepositoryImpl
    ): BinRepository

    @Binds
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ) : SettingsRepository

}