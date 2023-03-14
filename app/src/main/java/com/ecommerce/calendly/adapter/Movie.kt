package com.ecommerce.calendly.adapter

import com.google.gson.JsonObject

class Movie(`object`: JsonObject) {
    var imageUrl: String
    var title: String
    var year: String
    var imdbID: String
    var type: String

    init {
        title = `object`["Title"].asString
        imageUrl = `object`["Poster"].asString
        year = `object`["Year"].asString
        imdbID = `object`["imdbID"].asString
        type = `object`["Type"].asString
    }
}