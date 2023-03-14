package com.ecommerce.calendly.adapter

import android.content.Context
import android.graphics.Color
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.Response

class Coin(item:JsonObject,context:Context,adapter: CoinAdapter) {
    val id =  item.get("id").asString
    val name = item.get("name").asString
    val symbol = item.get("symbol").asString
    val type = item.get("type").asString
    val is_active = item.get("is_active").asBoolean
    val rank = item.get("rank").asInt
    val is_new = item.get("is_new").asBoolean
    var iconUrl="https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Bitcoin.svg/1200px-Bitcoin.svg.png"
    var context = context
    var adapter = adapter

    fun lookForUrl(){
        val url = "https://api.coinpaprika.com/v1/coins/$id"
        Ion.with(context)
            .load(url)
            .asJsonObject()
            .withResponse()
            .setCallback(FutureCallback<Response<JsonObject>> { e, result ->
                val itemObject = result.result.asJsonObject
                iconUrl =
                    if (itemObject["logo"] != null) itemObject["logo"].asString else "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Bitcoin.svg/1200px-Bitcoin.svg.png"
                adapter.notifyDataSetChanged()
            })


    }

    fun getIndicatorColor():Int{
        return if (is_active)  Color.GREEN else  Color.RED
    }
}