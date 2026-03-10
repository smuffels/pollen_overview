package com.example.pollen_overview.api

import androidx.compose.ui.text.TextGranularity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class PollenApi(private val client: HttpClient) {
    suspend fun getData(stationCode: Station, granularity: Granularity): String {
        return client.get("https://data.geo.admin.ch/ch.meteoschweiz.ogd-pollen/${stationCode.code}/ogd-pollen_${stationCode.code}_${granularity.code}_recent.csv").body<String>()
    }

}

enum class Station(val code: String){
    BERN("pbe"),
    BASEL("pbs"),
    BUCHS_SG("pbu"),
    CHAUX_DE_FONDS_NE("pcf"),
    DAVOS_GR("pds"),
    GENF("pge"),
    JUNGFRAUJOCH_VS("pju"),
    LOCARNO_TI("plo"),
    LAUSANNE_VD("pls"),
    LUGANO_TI("plu"),
    LUZERN("plz"),
    MUENSTERLINGEN_TG("pmu"),
    NEUCHATEL_NE("pne"),
    PAYERNE_VD("ppy"),
    SION_VS("psn"),
    ZUERICH("pzh")
}

enum class Granularity(val code: String){
    HOURLY("h"),
    DAILY("d")
}

enum class PlantType(val columnName: String, val displayName: String) {
    BIRKE("kabetuh0", "Birke"),
    GRAESER("khpoach0", "Gräser"),
    ERLE("kaalnuh0", "Erle"),
    HASEL("kacoryh0", "Hasel"),
    BUCHE("kafaguh0", "Buche"),
    ESCHE("kafraxh0", "Esche"),
    EICHE("kaquerh0", "Eiche")
}