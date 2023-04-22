package com.enesaksoy.kotlinnewsapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNews(vararg newModel: NewModel)

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()

    @Query("SELECT * FROM news WHERE id = :newId")
    suspend fun getNew(newId : Int) : NewModel

    @Query("SELECT * FROM news")
    suspend fun getAllNews() : List<NewModel>
}