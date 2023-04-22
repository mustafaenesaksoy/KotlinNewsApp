package com.enesaksoy.kotlinnewsapp.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class CustomSharedPreferences {
    companion object{
        private val PREFERENCES_TIME = "preferences_time"
        private var sharedpreferences : SharedPreferences? = null
        @Volatile private var instance : CustomSharedPreferences? = null
        private val lock = Any()
        operator fun invoke(context: Context) : CustomSharedPreferences= instance ?: synchronized(lock){
            instance ?: makeSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeSharedPreferences(context : Context) : CustomSharedPreferences{
                sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }

    }

    fun savetime(time : Long){
        sharedpreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME,time)
        }
    }

    fun gettime() = sharedpreferences?.getLong(PREFERENCES_TIME,0)
}