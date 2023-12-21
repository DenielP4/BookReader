package com.example.bookreader.data.locale

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.bookreader.data.remote.responses.Book
import com.example.bookreader.data.remote.responses.DeskOfBook
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun fromListDeskToString(list: List<DeskOfBook>?): String? {
        if (list == null) return null
        val gson = Gson()
        return gson.toJson(list)    }

    @TypeConverter
    fun fromStringToListDesk(string: String?): List<DeskOfBook>? {
        if (string == null) return null
        val gson = Gson()
        val type = object : TypeToken<List<DeskOfBook>>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun fromListBookToString(list: List<Book>?): String? {
        if (list == null) return null
        val gson = Gson()
        return gson.toJson(list)    }

    @TypeConverter
    fun fromStringToListBook(string: String?): List<Book>? {
        if (string == null) return null
        val gson = Gson()
        val type = object : TypeToken<List<Book>>() {}.type
        return gson.fromJson(string, type)
    }

}