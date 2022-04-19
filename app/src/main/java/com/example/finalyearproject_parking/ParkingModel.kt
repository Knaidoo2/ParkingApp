package com.example.finalyearproject_parking

import java.util.*
import kotlin.random.Random as Rando
import kotlin.random.Random as RandomRandom

data class ParkingModel(
    var id: Int = getAutoId(),
    var street: String = "",
    var city: String = "",
    var postcode: String = ""

) {
    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}