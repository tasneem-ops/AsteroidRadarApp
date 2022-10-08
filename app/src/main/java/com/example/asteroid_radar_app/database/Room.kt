package com.example.asteroid_radar_app.database

import android.content.Context
import android.provider.SyncStateContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.asteroid_radar_app.Asteroid
import com.example.asteroid_radar_app.Constants
import com.example.asteroid_radar_app.PictureOfDay
import com.example.asteroid_radar_app.network.NetworkUtils


@Dao
interface AsteroidDao {
    @Query("select * from asteroid_table")
    fun getAllAsteroids(): List<Asteroid>

    @Query("select * from asteroid_table where closeApproachDate = :today")
    fun getTodayAsteroids(today : String): List<Asteroid>

    @Query("select * from asteroid_table where closeApproachDate in (:week)")
    fun getWeekAsteroids(week : List<String>): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(vararg asteroids: Asteroid)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAsteroid(vararg asteroids: Asteroid)

}

@Dao
interface PictureDao{
    @Query ("select * from picture_of_day where mediaType = :type")
    fun getPic(type : String): PictureOfDay

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPic(pictureOfDay: PictureOfDay)

    @Query("delete from picture_of_day")
    fun clear()

}



@Database(entities = [Asteroid::class, PictureOfDay::class], version = 6 )
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    abstract val pictureDao : PictureDao
    companion object {

        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroids_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}