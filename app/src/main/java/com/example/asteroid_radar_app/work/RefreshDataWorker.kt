package com.example.asteroid_radar_app.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroid_radar_app.database.AsteroidDatabase.Companion.getInstance
import com.example.asteroid_radar_app.repository.AsteroidRepo
import com.example.asteroid_radar_app.repository.PicRepo
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val asteroidRepository = AsteroidRepo(database)
        val picRepository = PicRepo(database)
        return try {
            asteroidRepository.refreshAsteroids()
            picRepository.refreshPic()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

}