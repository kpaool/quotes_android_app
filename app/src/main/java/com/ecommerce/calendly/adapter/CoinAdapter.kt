package com.ecommerce.calendly.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ecommerce.calendly.R
import com.squareup.picasso.Picasso

class CoinAdapter(private val coins:List<Coin>) : Adapter<CoinViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.coin_item,parent,false))
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        var coin: Coin = coins[position]
        holder.title.text=coin.name
        holder.description.text=coin.symbol
        holder.indicator.setCardBackgroundColor(coin.getIndicatorColor())
        Picasso.get().load(coin.iconUrl).into(holder.icon)
    }

    override fun getItemCount(): Int {
        return  coins.size;
    }
}

