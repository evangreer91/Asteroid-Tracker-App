package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidAdapter: ListAdapter<Asteroid,
        AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ListItemAsteroidBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid) {
            binding.codenameString.text = item.codename
            binding.closeApproachDateString.text = item.closeApproachDate
            binding.isPotentiallyHazardousIcon.setImageResource(
                when (item.isPotentiallyHazardous) {
                    true -> R.drawable.ic_status_potentially_hazardous
                    else -> R.drawable.ic_status_normal
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemAsteroidBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid) : Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid) : Boolean {
        return oldItem == newItem
    }
}