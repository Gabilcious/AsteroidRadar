package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainViewModel(
    val database: AsteroidDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    var asteroids = database.getAsteroids()

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail
        get() = _navigateToAsteroidDetail

    private fun today() = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(Calendar.getInstance().time)

    init {
        init()
    }

    private fun init() {
        viewModelScope.launch {
            database.clear()
            getAsteroids()
        }
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
                val result = NasaApi.retrofitService.getAsteroids(today())
                val asteroids = parseAsteroidsJsonResult(JSONObject(result))
                database.insertAsteroids(asteroids)
            } catch (e: Exception) {
                Log.d("NasaApiService", "Failure: " + e.message)
                getAsteroids()
            }
        }
    }
}