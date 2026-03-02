package com.example.pollen_overview

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform