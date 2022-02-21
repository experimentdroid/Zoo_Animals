package com.randomanimals.data.model

import com.google.gson.annotations.SerializedName

data class Animal(@SerializedName("id") val id: Int,

    @SerializedName("name") val name: String,

    @SerializedName("latin_name") val latinName: String,

    @SerializedName("animal_type") val animalType: String,

    @SerializedName("active_time") val activeTime: String,

    @SerializedName("length_min") val lengthMin: Double,

    @SerializedName("length_max") val lengthMax: Double,

    @SerializedName("weight_min") val weightMin: Double,

    @SerializedName("weight_max") val weightMax: Double,

    @SerializedName("lifespan") val lifespan: Int,

    @SerializedName("habitat") val habitat: String,

    @SerializedName("diet") val diet: String,

    @SerializedName("geo_range") val geoRange: String,

    @SerializedName("image_link") val imageLink: String)