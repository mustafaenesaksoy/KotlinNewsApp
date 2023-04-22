package com.enesaksoy.kotlinnewsapp.appmodule

import android.content.Context
import androidx.room.Room
import com.enesaksoy.kotlinnewsapp.api.NewsAPI
import com.enesaksoy.kotlinnewsapp.repo.NewsRepository
import com.enesaksoy.kotlinnewsapp.repo.NewsRepositoryInterface
import com.enesaksoy.kotlinnewsapp.roomdb.NewDao
import com.enesaksoy.kotlinnewsapp.roomdb.NewDatabase
import com.enesaksoy.kotlinnewsapp.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun injectDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(
        context,
        NewDatabase::class.java,
        "NewsDatabase"
    ).build()

    @Provides
    @Singleton
    fun injectDao(database : NewDatabase) = database.getNewDao()

    @Provides
    @Singleton
    fun injectNewsApi() : NewsAPI{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsAPI::class.java)
    }

    @Provides
    @Singleton
    fun injectRepo(newsDao: NewDao,newsAPI: NewsAPI) : NewsRepositoryInterface{
        return NewsRepository(newsDao,newsAPI)
    }
}