package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.today

@Dao
interface AsteroidDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(asteroids: ArrayList<Asteroid>)

    @Query("SELECT * FROM asteroid_table WHERE close_approach_date >= :date ORDER BY close_approach_date")
    fun getAsteroids(date: String = today()): LiveData<List<Asteroid>>

    @Query("DELETE FROM asteroid_table WHERE close_approach_date < :date")
    suspend fun clearPast(date: String = today())
}