package com.dct.androidexam.model

data class MoviesListModel (
    // on below line we are creating a two variable
    // one for course name and other for course image.
    var backdrop_path: String,
    var strPoster: String,
    var title: String,
    var movie_id: Int
    )