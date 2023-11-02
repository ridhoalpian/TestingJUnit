package com.example.teamapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

data class ResponseUserGithub(
    val incomplete_results: Boolean,
    val items: MutableList<Item>,
    val total_count: Int
){
    @Parcelize
    @Entity(tableName = "user")
    data class Item(
        @ColumnInfo(name = "avatar_url")
        val avatar_url: String,
        @ColumnInfo(name = "id")
        val id: Int,
        @ColumnInfo(name = "Login")
        val login: String,
    )  : Parcelable
}