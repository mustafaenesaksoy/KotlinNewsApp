package com.enesaksoy.kotlinnewsapp.response

data class NewResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)