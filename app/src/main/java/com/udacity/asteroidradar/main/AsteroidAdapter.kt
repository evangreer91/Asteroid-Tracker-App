package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.TextItemViewHolder

class AsteroidAdapter: RecyclerView.Adapter<AsteroidAdapter.ViewHolder>() {

    var data = listOf<Asteroid>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(holder, item)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val codename: TextView = itemView.findViewById(R.id.codename_string)
        val closeApproachDate: TextView = itemView.findViewById(R.id.close_approach_date_string)
        val isPotentiallyHazardousIcon: ImageView = itemView.findViewById(R.id.is_potentially_hazardous_icon)

        fun bind(holder: ViewHolder, item: Asteroid) {
            holder.codename.text = item.codename
            holder.closeApproachDate.text = item.closeApproachDate
            holder.isPotentiallyHazardousIcon.setImageResource(
                when (item.isPotentiallyHazardous) {
                    true -> R.drawable.ic_status_potentially_hazardous
                    else -> R.drawable.ic_status_normal
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_asteroid, parent, false)

                return ViewHolder(view)
            }
        }
    }
}