package com.enesaksoy.kotlinnewsapp.repo

import androidx.lifecycle.LiveData
import com.enesaksoy.kotlinnewsapp.api.NewsAPI
import com.enesaksoy.kotlinnewsapp.response.NewResponse
import com.enesaksoy.kotlinnewsapp.roomdb.NewDao
import com.enesaksoy.kotlinnewsapp.roomdb.NewModel
import com.enesaksoy.kotlinnewsapp.util.Resource
import javax.inject.Inject

class NewsRepository @Inject constructor(val newDao: NewDao, val newsAPI: NewsAPI) : NewsRepositoryInterface {
    override suspend fun insertAllNews(newModel: List<NewModel>) {
        newDao.insertAllNews(*newModel.toTypedArray())
    }

    override suspend fun deleteAllNews() {
        newDao.deleteAllNews()
    }

    override suspend fun getNew(newId: Int): NewModel {
        return newDao.getNew(newId)
    }

    override suspend fun getAllNews(): List<NewModel> {
        return newDao.getAllNews()
    }

    override suspend fun getNewsApi(): Resource<NewResponse> {
        return try{
            val response = newsAPI.getNewsApi()
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : Exception){
            Resource.error("No data!",null)
        }
    }
}