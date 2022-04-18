package com.argz.issue3503.model

data class Performance(
    val completedAt: Long? = 0,
    val exerciseId: String? = "",
    val id: String? = "",
    val movementId: String? = "",
    val planId: String? = "",
    val repetitions: Int? = 0,
    val weight: Weight? = null
)