package com.example.asteroid_radar_app

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "picture_of_day")
data class PictureOfDay(
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    @PrimaryKey
    var url: String)