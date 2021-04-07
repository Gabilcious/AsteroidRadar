package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(MainViewModel::class.java)

        binding.viewModel = viewModel

        val adapter = AsteroidAdapter(AsteroidAdapter.AsteroidListener { asteroid ->
            viewModel.onAsteroidClicked(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        setHasOptionsMenu(true)

        viewModel.navigateToAsteroidDetail.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid?.let {
                this.findNavController().navigate(
                    MainFragmentDirections
                        .actionShowDetail(asteroid)
                )
                viewModel.onAsteroidDetailNavigated()
            }
        })

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
