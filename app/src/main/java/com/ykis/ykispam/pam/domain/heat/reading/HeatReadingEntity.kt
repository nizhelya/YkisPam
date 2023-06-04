package com.ykis.ykispam.pam.domain.heat.reading

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "heat_reading")
data class HeatReadingEntity(
    @Json(name = "address_id")
    @ColumnInfo(name = "address_id")
    val addressId: Int = 0,
    @PrimaryKey(autoGenerate = false)
    @Json(name = "pok_id")
    @ColumnInfo(name = "pok_id")
    val pokId: Int = 0,
    @Json(name = "teplomer_id")
    @ColumnInfo(name = "teplomer_id")
    val teplomerId: Int = 0,
    @Json(name = "date_readings")
    @ColumnInfo(name = "date_reading")
    val dateReading: String = "Unknown",
    @Json(name = "date_ot")
    @ColumnInfo(name = "date_ot")
    val dateOt: String = "Unknown",
    @Json(name = "date_do")
    @ColumnInfo(name = "date_do")
    val dateDo: String = "Unknown",
    val edizm: String = "Unknown",
    val koef: String = "Unknown",
    val days: Short = 0,
    val last: Double = 0.0,
    val currant: Double = 0.0,
    val gkal: Double = 0.0,
    val avg: Byte = 0,
    val tarif: Double = 0.0,
    val qty: Double = 0.0,
//    val gkm2         : String= "Unknown",
//    val otoplenie    : Double= 0.0,
    @Json(name = "pok_ot")
    @ColumnInfo(name = "pok_ot")
    val pokOt: String = "Unknown",
    @Json(name = "pok_do")
    @ColumnInfo(name = "pok_do")
    val pokDo: String = "Unknown",
    @Json(name = "gkal_rasch")
    @ColumnInfo(name = "gkal_rasch")
    val gkalRasch: String = "Unknown",
    @Json(name = "gkal_day")
    @ColumnInfo(name = "gkal_day")
    val gkalDay: String = "Unknown",
    @Json(name = "qty_day")
    @ColumnInfo(name = "qty_day")
    val qtyDay: String = "Unknown",
    @Json(name = "day_avg")
    @ColumnInfo(name = "day_avg")
    val dayAvg: String = "Unknown",
    @Json(name = "data_in")
    @ColumnInfo(name = "date_in")
    val dateIn: String = "Unknown",
    val operator: String = "Unknown",

    )