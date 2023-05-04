package com.example.assignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignment.model.Repos


@Database(entities = arrayOf(Repos::class), version = 3, exportSchema = false)
abstract class MainDb : RoomDatabase() {

    abstract fun getDao(): RepoDao

    companion object {

        @Volatile
        private var INSTANCE: MainDb? = null

        fun getdb(context: Context): MainDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDb::class.java,
                    "DB"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }


}