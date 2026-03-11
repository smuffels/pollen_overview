package com.example.pollen_overview.repository

import androidx.compose.ui.geometry.Rect
import com.example.pollen_overview.api.Granularity
import com.example.pollen_overview.api.PlantType
import com.example.pollen_overview.api.PollenApi
import com.example.pollen_overview.api.Station
import com.example.pollen_overview.model.Level
import com.example.pollen_overview.model.PollenData
import com.example.pollen_overview.model.Region
import kotlinx.datetime.Instant
import com.example.pollen_overview.model.toStation
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.format.byUnicodePattern

class Repository(private val api: PollenApi, private val useFake: Boolean = false) {

    suspend fun getLatestPollen(region: Region): List<PollenData>{
        if(useFake) return fakeData(region)
        val station = region.toStation()
        val csv = api.getData(station, Granularity.HOURLY)
        return parseCsv(csv,region)
    }

    private fun fakeData(region: Region): List<PollenData> {
        val timestamp = Instant.parse("2026-03-09T23:00:00Z")
        return when (region){
            Region.ZH -> listOf(
                PollenData("Birke", region, Level.STRONG, timestamp),
                PollenData("Hasel", region, Level.MEDIUM, timestamp),
                PollenData("Erle", region, Level.LOW, timestamp),
                PollenData("Gräser", region, Level.NONE, timestamp),
                PollenData("Esche", region, Level.VERY_STRONG, timestamp),
                PollenData("Buche", region, Level.LOW, timestamp),
                PollenData("Eiche", region, Level.NONE, timestamp),
            )
            Region.TG -> listOf(
                PollenData("Birke", region, Level.STRONG, timestamp),
                PollenData("Hasel", region, Level.MEDIUM, timestamp),
                PollenData("Erle", region, Level.LOW, timestamp),
            )
            else -> listOf(
                PollenData("Birke", region, Level.LOW, timestamp),
                PollenData("Hasel", region, Level.LOW, timestamp),
                PollenData("Erle", region, Level.LOW, timestamp),
                PollenData("Gräser", region, Level.LOW, timestamp),
                PollenData("Esche", region, Level.LOW, timestamp),
                PollenData("Buche", region, Level.LOW, timestamp),
                PollenData("Eiche", region, Level.LOW, timestamp),
            )
        }

    }

    private fun parseCsv(csv: String, region: Region): List<PollenData>{
        val lines = csv.trim().lines()
        if (lines.size<2) return emptyList()

        val header = lines[0].split(";")
        val lastLine = lines.last().split(";")

        val timeIndex = header.indexOf("reference_timestamp")
        val format = LocalDateTime.Format { byUnicodePattern("dd.MM.yyyy HH:mm") }
        val timestamp = LocalDateTime.parse(lastLine[timeIndex], format).toInstant(TimeZone.of("Europe/Zurich"))


        val result = mutableListOf<PollenData>()

        for (plant in PlantType.entries) {
            val index = header.indexOf(plant.columnName)
            if (index == -1) continue

            val rawValue = lastLine[index].toIntOrNull() ?: continue

            result.add(PollenData(
                plantName = plant.displayName,
                region = region,
                level = rawValue.toLevel(),
                time = timestamp
            ))
        }

        return result
    }


    // cutoff values are a guestimate based on the meteo swiss map
    private fun Int.toLevel(): Level = when {
        this == 0    -> Level.NONE
        this < 30    -> Level.LOW
        this < 70    -> Level.MEDIUM
        this < 600   -> Level.STRONG
        else         -> Level.VERY_STRONG
    }

}