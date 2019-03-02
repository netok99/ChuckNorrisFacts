package com.chucknorrisfacts.search.data.model

import com.squareup.moshi.Json

data class SearchModel(
    @field:Json(name = "total") val total: Int,
    @field:Json(name = "result") val result : List<JokeModel>
)
