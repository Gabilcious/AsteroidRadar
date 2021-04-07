package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(
    val database: AsteroidDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    var asteroids = database.getAsteroids()

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay
        get() = _pictureOfDay

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail
        get() = _navigateToAsteroidDetail

    init {
        init()
    }

    private fun init() {
        getPictureOfDay()
        getAsteroids()
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            try {
                val result = NasaApi.retrofitService.getAsteroids()
                val asteroids = parseAsteroidsJsonResult(JSONObject(result))
                database.insertAsteroids(asteroids)
            } catch (e: Exception) {
                Log.d("NasaApiService", "Failure: " + e.message)
                getAsteroids()
            }
        }
    }

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                val result = NasaApi.retrofitService.getPictureOfDay()
                if (result.mediaType == "image") {
                    _pictureOfDay.value = result
                }
            } catch (e: Exception) {
                Log.d("NasaApiService", "Failure: " + e.message)
                getPictureOfDay()
            }
        }
    }
}