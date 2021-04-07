package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import org.json.JSONObject
import retrofit2.HttpException

class FetchAsteroidsWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext).asteroidDatabaseDao
        return try {
            database.clearPast()
            val result = NasaApi.retrofitService.getAsteroids()
            val asteroids = parseAsteroidsJsonResult(JSONObject(result))
            database.insertAsteroids(asteroids)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "FetchAsteroidsWorker"
    }
}