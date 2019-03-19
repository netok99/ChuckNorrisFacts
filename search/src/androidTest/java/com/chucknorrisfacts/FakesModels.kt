package com.chucknorrisfacts

import com.chucknorrisfacts.search.data.model.JokeModel

val jokeModelValueLessThan80 = JokeModel(
    category = listOf("movie", "food"),
    iconUrl = "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
    id = "7ver3y48qqsfktpelir7ua",
    value = "Chuck Norris`s test cases cover your code too.",
    url = "https://api.chucknorris.io/jokes/7ver3y48qqsfktpelir7ua"
)

val jokeModelValueMoreThan80 = JokeModel(
    category = listOf("movie", "food"),
    iconUrl = "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
    id = "7ver3y48qqsfktpelir7ua",
    value = "Chuck Norris`s test cases cover your code too. Chuck Norris`s test cases cover your code too. Chuck Norris`s test cases cover your code too." +
            "Chuck Norris`s test cases cover your code too. Chuck Norris`s test cases cover your code too.",
    url = "https://api.chucknorris.io/jokes/7ver3y48qqsfktpelir7ua"
)

val jokeModelValueWithOutCategories = JokeModel(
    category = listOf(""),
    iconUrl = "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
    id = "7ver3y48qqsfktpelir7ua",
    value = "Chuck Norris`s test cases cover your code too. Chuck Norris`s test cases cover your code too. Chuck Norris`s test cases cover your code too." +
            "Chuck Norris`s test cases cover your code too. Chuck Norris`s test cases cover your code too.",
    url = "https://api.chucknorris.io/jokes/7ver3y48qqsfktpelir7ua"
)