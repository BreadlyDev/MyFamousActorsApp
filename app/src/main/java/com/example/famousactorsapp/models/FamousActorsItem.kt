package com.example.famousactorsapp.models

data class FamousActorsItem(
    val birthday: String,
    val gender: String,
    val height: Double,
    val name: String,
    val nationality: String,
    val netWorth: Long,
    val occupation: List<String>,
    val age: Int,
    val isAlive: Boolean
)
