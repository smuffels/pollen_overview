package com.example.pollen_overview.api

import com.example.pollen_overview.model.Level
import com.example.pollen_overview.model.PollenData
import com.example.pollen_overview.model.Region
import kotlinx.serialization.Serializable
import kotlin.time.Instant

data class PollenDto(
    val plantName: String,
    val region: String,
    val level: String,
    val time: String
)

fun PollenDto.toModel(): PollenData{
    return PollenData(
        plantName = plantName,
        region = Region.valueOf(region),
        level = Level.valueOf(level),
        time = Instant.parse(time)
    )
}