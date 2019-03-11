package com.chucknorrisfacts.search.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JokeModel(
    @field:Json(name = "category") val category: List<String>?,
    @field:Json(name = "icon_url") val iconUrl: String,
    @field:Json(name = "id") val id: String,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "value") val value: String
) : Parcelable
