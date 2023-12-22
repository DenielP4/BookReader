package com.example.bookreader.domain.models

import com.example.bookreader.data.remote.responses.DeskOfBook

data class UserReg(
    val name: String,
    val registerDate: String,
    var password: String,
    val shelf: List<DeskOfBook>
)
