package com.enesaksoy.kotlinnewsapp.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesaksoy.kotlinnewsapp.repo.NewsRepositoryInterface
import com.enesaksoy.kotlinnewsapp.response.Article
import com.enesaksoy.kotlinnewsapp.roomdb.NewModel
import com.enesaksoy.kotlinnewsapp.util.CustomSharedPreferences
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository : NewsRepositoryInterface,val application : Application): ViewModel() {

    private val getNewsList = MutableLiveData<List<NewModel>>()
    val getnewsList : LiveData<List<NewModel>>
    get() = getNewsList

    private val newsError = MutableLiveData<Boolean>()
    val newserror : LiveData<Boolean>
    get() = newsError

    private val newsLoading = MutableLiveData<Boolean>()
    val newsloading : LiveData<Boolean>
        get() = newsLoading

    private var customPreferences = CustomSharedPreferences(application)
    private val refreshTime = 10 * 60 * 1000 * 1000 * 1000L
    fun refreshdata(){
        val updateTime = customPreferences.gettime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        }else {
            getDataFromApi()
        }
    }


    fun getDataFromApi()=viewModelScope.launch{
        newsLoading.value = true
        newsError.value = false
        val resource = repository.getNewsApi()
        resource.data?.let {
            subscribeToNewsRoom(it.articles)
            Toast.makeText(application,"DATA RECEIVED FROM API",Toast.LENGTH_SHORT).show()
        }
    }

    fun subscribeToNewsRoom(articles : List<Article>) = viewModelScope.launch{
        val modelList = ArrayList<NewModel>()
        for(article in articles){
            val newModel = NewModel(article.title,article.author,article.url)
            modelList.add(newModel)
        }
        repository.insertAllNews(modelList)
        showNews(modelList)
        customPreferences.savetime(System.nanoTime())
    }

    fun getDataFromSQLite() = viewModelScope.launch{
        val roomNewList = repository.getAllNews()
        showNews(roomNewList)
        Toast.makeText(application,"DATA RECEIVED FROM SQLite",Toast.LENGTH_SHORT).show()
    }

    fun showNews(list : List<NewModel>){
        getNewsList.value = list
        newsError.value = false
        newsLoading.value = false
    }
}