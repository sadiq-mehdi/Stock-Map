package com.example.stockmap.di

import android.content.Context
import androidx.room.Room
import com.example.stockmap.data.local.dao.BinDao
import com.example.stockmap.data.local.dao.ProductDao
import com.example.stockmap.data.local.database.StockMapDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): StockMapDatabase{
        return Room.databaseBuilder(
            context = context,
            klass = StockMapDatabase::class.java,
            name = "stockmap_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(db: StockMapDatabase): ProductDao{
        return db.productDao()
    }

    @Provides
    @Singleton
    fun provideBinDao(db: StockMapDatabase): BinDao{
        return db.binDao()
    }

}