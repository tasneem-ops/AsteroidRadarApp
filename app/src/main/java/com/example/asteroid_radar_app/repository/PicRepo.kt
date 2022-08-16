package com.example.asteroid_radar_app.repository

import android.util.Log
import com.example.asteroid_radar_app.PictureOfDay
import com.example.asteroid_radar_app.database.AsteroidDatabase
import com.example.asteroid_radar_app.network.PicApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PicRepo(private val database : AsteroidDatabase) {
lateinit var picture : PictureOfDay
    suspend fun refreshPic(){
        withContext(Dispatchers.IO){
            picture = database.pictureDao.getPic("image")
            try {
                val response = PicApi.retrofitService.getProperties()
                database.pictureDao.insertPic(response)
            }
            catch (e:Exception){
                Log.i("PicRepo", "No Internet"+ e)
            }
        }
    }
}
