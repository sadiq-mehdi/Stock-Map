package com.example.stockmap.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stockmap.data.local.dao.BinDao
import com.example.stockmap.data.local.dao.ProductDao
import com.example.stockmap.data.local.entity.BinEntity
import com.example.stockmap.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class, BinEntity::class], version = 2)
abstract class StockMapDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun binDao(): BinDao

}