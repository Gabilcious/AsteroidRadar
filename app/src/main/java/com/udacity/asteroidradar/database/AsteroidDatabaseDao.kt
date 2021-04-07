package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Dao
interface AsteroidDatabaseDao {
    private fun today() = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(Calendar.getInstance().time)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(asteroids: ArrayList<Asteroid>)

    @Query("SELECT * FROM asteroid_table WHERE close_approach_date = :date")
    fun getAsteroids(date: String = today()): LiveData<List<Asteroid>>

    @Query("DELETE FROM asteroid_table")
    suspend fun clear()
}