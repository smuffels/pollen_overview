package com.example.pollen_overview.model

import com.example.pollen_overview.api.Station


    fun Region.toStation(): Station = when (this) {
        // Deutschschweiz Nordwest
        Region.BS -> Station.BASEL
        Region.BL -> Station.BASEL
        Region.AG -> Station.BASEL
        Region.SO -> Station.BERN

        // Bern / Mittelland
        Region.BE -> Station.BERN
        Region.FR -> Station.PAYERNE_VD
        Region.JU -> Station.CHAUX_DE_FONDS_NE

        // Zürich / Ostschweiz
        Region.ZH -> Station.ZUERICH
        Region.SH -> Station.ZUERICH
        Region.ZG -> Station.ZUERICH
        Region.SZ -> Station.ZUERICH
        Region.UR -> Station.LUZERN
        Region.NW -> Station.LUZERN
        Region.OW -> Station.LUZERN
        Region.LU -> Station.LUZERN
        Region.GL -> Station.ZUERICH

        // Ostschweiz
        Region.SG -> Station.BUCHS_SG
        Region.AR -> Station.BUCHS_SG
        Region.AI -> Station.BUCHS_SG
        Region.TG -> Station.MUENSTERLINGEN_TG
        Region.GR -> Station.DAVOS_GR

        // Westschweiz / Romandie
        Region.GE -> Station.GENF
        Region.VD -> Station.LAUSANNE_VD
        Region.NE -> Station.NEUCHATEL_NE

        // Süd
        Region.TI -> Station.LUGANO_TI
        Region.VS -> Station.SION_VS
    }