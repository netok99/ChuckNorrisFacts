package com.chucknorrisfacts.search

import com.chucknorrisfacts.search.data.model.JokeModel
import com.chucknorrisfacts.search.data.model.SearchModel

val jokeModel = JokeModel(
    category = listOf("movie", "food"),
    iconUrl = "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
    id = "7ver3y48qqsfktpelir7ua",
    value = "Chuck Norris`s test cases cover your code too.",
    url = "https://api.chucknorris.io/jokes/7ver3y48qqsfktpelir7ua"
)

val searchModel = SearchModel(
    total = 1,
    result = listOf(jokeModel)
)

val categories = listOf(
    "explicit", "dev", "movie", "food", "celebrity", "science", "sport", "political",
    "religion", "animal", "history", "music", "travel", "career", "money", "fashion"
)
