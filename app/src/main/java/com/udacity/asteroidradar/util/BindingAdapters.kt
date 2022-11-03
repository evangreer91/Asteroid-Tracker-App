package com.udacity.asteroidradar

import android.content.Context
import android.provider.Settings.Global.getString
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.domain.Asteroid

@BindingAdapter("statusIcon")
fun ImageView.setAsteroidStatusImage(item: Asteroid) {
    if (item.isPotentiallyHazardous) {
        setImageResource(R.drawable.ic_status_potentially_hazardous)
        contentDescription = context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        setImageResource(R.drawable.ic_status_normal)
        contentDescription = context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("codenameString")
fun TextView.setCodename(item: Asteroid) {
    item?.let {
        text = item.codename
    }
}

@BindingAdapter("closeApproachDateString")
fun TextView.setCloseApproachDate(item: Asteroid) {
    item?.let {
        text = item.closeApproachDate
    }
}

@BindingAdapter("asteroidStatusImage")
fun ImageView.bindDetailsStatusImage(isHazardous: Boolean) {
    if (isHazardous) {
        setImageResource(R.drawable.asteroid_hazardous)
        contentDescription = context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        setImageResource(R.drawable.asteroid_safe)
        contentDescription = context.getString(R.string.not_hazardous_asteroid_image)
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

// image of the day binding adapter
// this binding adapter runs image processing using the Picasso library
// we want this binding adapter executed when an XML item has the imageUrl attribute
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Picasso
            .get()
            .load(imgUrl)
            .into(imgView)
    }
}