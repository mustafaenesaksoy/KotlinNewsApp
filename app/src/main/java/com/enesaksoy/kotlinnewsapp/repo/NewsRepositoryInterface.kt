package com.enesaksoy.kotlinnewsapp.repo

import androidx.lifecycle.LiveData
import com.enesaksoy.kotlinnewsapp.response.NewResponse
import com.enesaksoy.kotlinnewsapp.roomdb.NewModel
import com.enesaksoy.kotlinnewsapp.util.Resource

interface NewsRepositoryInterface {

    suspend fun insertAllNews(newModel: List<NewModel>)

    suspend fun deleteAllNews()

    suspend fun getNew(newId : Int) : NewModel

    suspend fun getAllNews() : List<NewModel>

    suspend fun getNewsApi() : Resource<NewResponse>
}