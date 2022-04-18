package com.argz.issue3503.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Workout (
    val id: String? = "",
    val startTime: Long? = 0,
    val endTime: Long? = 0,
    val version: Long? = 0,
    val workoutId: String? = "",
    val performances: List<Performance>? = null
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "startTime" to startTime,
            "endTime" to endTime,
            "version" to version,
            "workoutId" to workoutId,
            "performances" to performances
        )
    }
}
