package com.example.bookreader.data.locale

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bookreader.data.remote.responses.DeskOfBook

@Entity(tableName = "user_info_table")
data class UserInfo(
    @PrimaryKey
    val id: Int? = null,
    val name: String?,
    val registerDate: String?,
    var shelf: List<DeskOfBook>?,
    val userId: Int?
)
