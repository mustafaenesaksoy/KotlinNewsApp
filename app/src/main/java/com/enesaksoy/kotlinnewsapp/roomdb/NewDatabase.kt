package com.enesaksoy.kotlinnewsapp.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(NewModel::class), version = 1)
abstract class NewDatabase : RoomDatabase(){
    abstract fun getNewDao() : NewDao
}