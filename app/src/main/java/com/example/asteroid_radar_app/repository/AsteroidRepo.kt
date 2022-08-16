package com.example.asteroid_radar_app.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.asteroid_radar_app.Asteroid
import com.example.asteroid_radar_app.Constants
import com.example.asteroid_radar_app.database.AsteroidDatabase
import com.example.asteroid_radar_app.network.AsteroidApi
import com.example.asteroid_radar_app.network.NetworkUtils
import com.example.asteroid_radar_app.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
val networkUtils = NetworkUtils()
class AsteroidRepo(private val database : AsteroidDatabase) {
    lateinit var allAsteroids : List<Asteroid>
    lateinit var todayAsteroids : List<Asteroid>
    lateinit var weekAsteroids : List<Asteroid>
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO){

            try {
                val response = AsteroidApi.retrofitService.getProperties()
                val asteroidslist = parseAsteroidsJsonResult(jsonResult = JSONObject(response))
                for (asteroid in asteroidslist){
                    database.asteroidDao.insertAsteroid(asteroid)
                }
            }
            catch (e:Exception){
                Log.i("AsteroidRepo", "No Internet"+ e)
            }
            allAsteroids = database.asteroidDao.getAllAsteroids()
            todayAsteroids = database.asteroidDao.getTodayAsteroids(networkUtils.today)
            weekAsteroids = database.asteroidDao.getWeekAsteroids(networkUtils.nextSevenDays)


        }
    }

}