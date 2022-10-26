package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.source.local.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false)

        val application = requireNotNull(this.activity).application

        val asteroids = listOf<Asteroid>(
            Asteroid(
                id = 1,
                codename = "(2015 RC)",
                closeApproachDate = "2015-09-22",
                absoluteMagnitude = 0.5034568,
                estimatedDiameter = 3.4445168,
                relativeVelocity = 18.1234568,
                distanceFromEarth = 0.3027479,
                isPotentiallyHazardous = true
            ),
            Asteroid(
                id = 2,
                codename = "(2016 RC)",
                closeApproachDate = "2016-12-12",
                absoluteMagnitude = 1.3456232,
                estimatedDiameter = 4.0034531,
                relativeVelocity = 22.1112345,
                distanceFromEarth = 0.7511675,
                isPotentiallyHazardous = false
            ),
            Asteroid(
                id = 3,
                codename = "(2017 RC)",
                closeApproachDate = "2017-11-18",
                absoluteMagnitude = 3.3312447,
                estimatedDiameter = 2.0224514,
                relativeVelocity = 18.2334545,
                distanceFromEarth = 1.2214315,
                isPotentiallyHazardous = true
            ),
            Asteroid(
                id = 4,
                codename = "(2020 RC)",
                closeApproachDate = "2020-10-01",
                absoluteMagnitude = 3.3312447,
                estimatedDiameter = 2.0224514,
                relativeVelocity = 18.2334545,
                distanceFromEarth = 1.2214315,
                isPotentiallyHazardous = true
            ),
            Asteroid(
                id = 4,
                codename = "(2020 RC)",
                closeApproachDate = "2020-10-01",
                absoluteMagnitude = 3.3312447,
                estimatedDiameter = 2.0224514,
                relativeVelocity = 18.2334545,
                distanceFromEarth = 1.2214315,
                isPotentiallyHazardous = true
            ),
            Asteroid(
                id = 4,
                codename = "(2020 RC)",
                closeApproachDate = "2020-10-01",
                absoluteMagnitude = 3.3312447,
                estimatedDiameter = 2.0224514,
                relativeVelocity = 18.2334545,
                distanceFromEarth = 1.2214315,
                isPotentiallyHazardous = true
            ),
            Asteroid(
                id = 4,
                codename = "(2020 RC)",
                closeApproachDate = "2020-10-01",
                absoluteMagnitude = 3.3312447,
                estimatedDiameter = 2.0224514,
                relativeVelocity = 18.2334545,
                distanceFromEarth = 1.2214315,
                isPotentiallyHazardous = true
            ),
            Asteroid(
                id = 4,
                codename = "(2020 RC)",
                closeApproachDate = "2020-10-01",
                absoluteMagnitude = 3.3312447,
                estimatedDiameter = 2.0224514,
                relativeVelocity = 18.2334545,
                distanceFromEarth = 1.2214315,
                isPotentiallyHazardous = true
            )
        )

        val adapter = AsteroidAdapter()
        binding.asteroidRecycler.adapter = adapter

        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
