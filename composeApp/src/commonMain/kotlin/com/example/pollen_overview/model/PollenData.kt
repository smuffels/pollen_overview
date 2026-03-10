package com.example.pollen_overview.model

import kotlin.time.Instant


data class PollenData (
    val plantName: String,
    val region: Region,
    val level: Level,
    val time: Instant

)

enum class Region{
    AG,
    AR,
    AI,
    BL,
    BS,
    BE,
    FR,
    GE,
    GL,
    GR,
    JU,
    LU,
    NE,
    NW,
    OW,
    SH,
    SZ,
    SO,
    SG,
    TI,
    TG,
    UR,
    VS,
    VD,
    ZG,
    ZH
}

enum class Level(val displayName: String){
    NONE("Keine"),
    LOW("Schwach"),
    MEDIUM("Mittel"),
    STRONG("Stark"),
    VERY_STRONG("Sehr stark")
}