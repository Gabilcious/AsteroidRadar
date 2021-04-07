package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import kotlinx.coroutines.launch

class MainViewModel(
    val database: AsteroidDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    var asteroids = database.getAsteroids()

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail
        get() = _navigateToAsteroidDetail

    init {
        init()
    }

    private fun init() {
        val asteroids = arrayListOf<Asteroid>()
        val a1 = Asteroid(1, "a5", "2021-04-07", 1.4, 1.4, 1.4, 1.4, false)
        val a2 = Asteroid(2, "a4", "2021-04-07", 1.4, 1.4, 1.4, 1.4, true)
        val a3 = Asteroid(3, "a3", "2021-04-07", 1.4, 1.4, 1.4, 1.4, true)
        asteroids.add(a1)
        asteroids.add(a2)
        asteroids.add(a3)
        viewModelScope.launch {
            database.clear()
            database.insertAsteroids(asteroids)
        }
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }
}