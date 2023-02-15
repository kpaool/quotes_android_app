package com.ecommerce.calendly.adapter

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.text.SimpleDateFormat


class Quote(quote: JsonObject) {
    val author = quote.get("author").asString
    val content = quote.get("content").asString
    val tags = quote.get("tags").asJsonArray
    val dateAdded =  quote.get("dateAdded").asString
    val dateModified = quote.get("dateModified").asString
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")


    fun getAddedDate():String{
        var date = dateFormat.parse(dateAdded)
        return SimpleDateFormat("dd MMMM yyyy").format(date)
    }

    fun getTags():List<String>{
        var data = ArrayList<String>()
        tags.forEach { value-> data.add(value.asString)  }
        return data;
    }
}