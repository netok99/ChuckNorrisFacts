package com.chucknorrisfacts.search.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchModel(
    @field:Json(name = "total") val total: Int,
    @field:Json(name = "result") val result: List<JokeModel>
) : Parcelable
