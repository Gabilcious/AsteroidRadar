package com.udacity.asteroidradar.api

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDatabaseDao {
    @Query("SELECT * FROM asteroid_table WHERE close_approach_date = :date")
    fun getAsteroidsForDate(date: String): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroids(asteroids: ArrayList<Asteroid>)
}