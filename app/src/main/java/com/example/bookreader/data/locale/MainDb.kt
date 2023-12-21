package com.example.bookreader.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        UserInfo::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MainDb : RoomDatabase() {
    abstract val bookDao: BookDao
}