package com.enesaksoy.kotlinnewsapp.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewModel (
        val title : String,
        val author : String,
        val url : String,
        ){
        @PrimaryKey(autoGenerate = true)
        var id : Int = 0
}