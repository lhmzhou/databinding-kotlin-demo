package com.lhmzhou.databindingDemo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ShoeEntry::class], version = 1, exportSchema = false)
@TypeConverters(MapConverter::class, ProcurementTypeConverter::class, GenderTypeConverter::class)
abstract class ShoeDatabase : RoomDatabase() {

    abstract fun shoeDao(): ShoeDao

    companion object {

        private const val DATABASE_NAME = "shoe_inventory"
        @Volatile private var sInstance: ShoeDatabase? = null

        fun getInstance(context: Context): ShoeDatabase {
            return sInstance ?: synchronized(this) {
                sInstance ?: Room.databaseBuilder(context.applicationContext,
                    ShoeDatabase::class.java, ShoeDatabase.DATABASE_NAME)
                    .build()
                    .also { sInstance = it }
            }

        }
    }
}