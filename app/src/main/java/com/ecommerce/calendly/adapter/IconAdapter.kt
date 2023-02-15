package com.ecommerce.calendly.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.calendly.R
import java.lang.Math.abs
import java.util.*


class IconAdapter(var data:List<String>) :RecyclerView.Adapter<IconViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        return IconViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.circular_profile_card,parent,false))
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        holder.setBackground()
        holder.setText(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class IconViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var textView : TextView = itemView.findViewById(R.id.textViewForIcon)

    fun setText(text:String){
        textView.text= text.first().toString()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    fun setBackground(){
        val (bgColor, textColor) = generateRandomColors(10.0)
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.setColor(bgColor)
        shape.setStroke(2, Color.WHITE);
        textView.background = shape
        textView.setTextColor(textColor)
    }

    fun generateRandomColors(contrast:Double): Pair<Int, Int> {
        var bgColor: Int
        var textColor: Int
        do {
            // Generate a random color for the background
            bgColor = Color.argb(255, (0..255).random(), (0..255).random(), (0..255).random())

            // Generate a random color for the text
            textColor = Color.argb(255, (150..255).random(), (150..255).random(), (150..255).random())

            // Calculate the contrast ratio between the two colors
            val bgLuminance = ColorUtils.calculateLuminance(bgColor)
            val textLuminance = ColorUtils.calculateLuminance(textColor)
            val contrastRatio = ColorUtils.calculateContrast(bgColor, textColor)

            // Ensure that the contrast ratio is at least 4.5 and that the
            // background and text colors are not too close in luminance
        } while (contrastRatio < contrast|| abs(bgLuminance - textLuminance) < 0.2)

        return Pair(bgColor, textColor)
    }

}