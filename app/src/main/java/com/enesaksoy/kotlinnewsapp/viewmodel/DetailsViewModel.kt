package com.enesaksoy.kotlinnewsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesaksoy.kotlinnewsapp.repo.NewsRepository
import com.enesaksoy.kotlinnewsapp.roomdb.NewDao
import com.enesaksoy.kotlinnewsapp.roomdb.NewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailsViewModel@Inject constructor(private val newDao: NewDao) : ViewModel() {

    private var selectedNew = MutableLiveData<NewModel>()
    val selectednew : LiveData<NewModel>
    get() = selectedNew


    fun getNew(id : Int) = viewModelScope.launch{
        selectedNew.value = newDao.getNew(id)
    }

}