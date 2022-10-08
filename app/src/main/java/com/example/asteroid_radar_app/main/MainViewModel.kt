package com.example.asteroid_radar_app.main

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.asteroid_radar_app.Asteroid
import com.example.asteroid_radar_app.PictureOfDay
import com.example.asteroid_radar_app.database.AsteroidDatabase
import com.example.asteroid_radar_app.network.PicApi
import com.example.asteroid_radar_app.repository.AsteroidRepo
import com.example.asteroid_radar_app.repository.PicRepo
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val asteroidDatabase = AsteroidDatabase.getInstance(application)
    private val _pic = MutableLiveData<PictureOfDay>()
    val pic : LiveData<PictureOfDay>
    get() = _pic
    val picRepo = PicRepo(asteroidDatabase)

    private var _asteroid = MutableLiveData<List<Asteroid>>()
    val asteroid : LiveData<List<Asteroid>>
    get() = _asteroid
    val asteroidRepo = AsteroidRepo(asteroidDatabase)

   private val _selectedAsteroidNavigation = MutableLiveData<Asteroid>()
    val selectedAsteroidNavigation : LiveData<Asteroid>
    get() = _selectedAsteroidNavigation
    init {
        getData()
        getAst()

    }

    fun getData(){
        viewModelScope.launch {
            picRepo.refreshPic()
            _pic.value = picRepo.picture


        }
    }
    fun getAst(){
        viewModelScope.launch {
             asteroidRepo.refreshAsteroids()
           _asteroid.value = asteroidRepo.todayAsteroids
        }

    }

    fun onNavigateDetail(asteroid: Asteroid){
        _selectedAsteroidNavigation.value = asteroid
    }
    fun doneNavigation(){
        _selectedAsteroidNavigation .value = null
    }

    fun showToday(){
        viewModelScope.launch {
            _asteroid.value = asteroidRepo.todayAsteroids
        }
    }
    fun showWeek(){
        viewModelScope.launch {
            _asteroid.value = asteroidRepo.weekAsteroids
        }
    }
    fun showSaved(){
        viewModelScope.launch {
            _asteroid.value = asteroidRepo.allAsteroids
        }
    }
}