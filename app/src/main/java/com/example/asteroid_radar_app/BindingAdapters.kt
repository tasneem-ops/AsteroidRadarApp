package com.example.asteroid_radar_app

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import java.net.URI

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = "Hazardous Asteroid Icon"
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = "Safe Asteroid Icon"
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = "Potentially hazardous asteroid image"
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = "Not hazardous asteroid image"
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}



@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl : String?){
    imageUrl?.let{
        val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Picasso.get()
            .load(imageUri)
            .into(imageView)
    }
}
