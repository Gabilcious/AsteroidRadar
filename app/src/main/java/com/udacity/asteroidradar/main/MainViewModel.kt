package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class MainViewModel : ViewModel() {
    var asteroids = getAllAsteroids()

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail
        get() = _navigateToAsteroidDetail

    private fun getAllAsteroids(): LiveData<List<Asteroid>> {
        val asteroids = arrayListOf<Asteroid>()
        val a1 = Asteroid(10, "a1", "20-20-20", 1.4, 1.4, 1.4, 1.4, false)
        val a2 = Asteroid(15, "a2", "20-20-20", 1.4, 1.4, 1.4, 1.4, true)
        asteroids.add(a1)
        asteroids.add(a2)
        asteroids.add(a1)
        asteroids.add(a1)
        asteroids.add(a1)
        asteroids.add(a2)
        asteroids.add(a1)
        asteroids.add(a2)
        asteroids.add(a1)
        asteroids.add(a1)
        asteroids.add(a1)
        asteroids.add(a2)
        asteroids.add(a1)
        asteroids.add(a2)
        asteroids.add(a1)
        asteroids.add(a1)
        asteroids.add(a1)
        asteroids.add(a2)
        asteroids.add(a1)
        asteroids.add(a2)
        asteroids.add(a1)
        asteroids.add(a1)
        asteroids.add(a1)
        asteroids.add(a2)
        return MutableLiveData(asteroids)
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }
}