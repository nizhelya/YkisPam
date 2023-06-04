package com.ykis.ykispam.pam.domain.water.reading

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "water_reading")
data class WaterReadingEntity(
    @Json(name = "address_id")
    @ColumnInfo(name = "address_id")
    val addressId: Int = 0,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pok_id")
    @Json(name = "pok_id")
    val pokId: Int = 0,
    @Json(name = "vodomer_id")
    @ColumnInfo(name = "vodomer_id")
    val vodomerId: Int = 0,
//    @ColumnInfo(name = "date_readings")
//    @Json(name = "date_readings")
//    val dateReadings : String = "Unknown",
    @ColumnInfo(name = "date_ot")
    @Json(name = "date_ot")
    val dateOt: String = "Unknown",
    @ColumnInfo(name = "date_do")
    @Json(name = "date_do")
    val dateDo: String = "Unknown",
    val days: Short = 0,
    val last: Int = 0,
    val currant: Int = 0,
    val kub: Short = 0,
    val avg: Byte = 0,
//    @Json(name = "tarif_xv")
//    val tarifXv      : Double = 0.0,
//    val xvoda        : Double = 0.0,
//    @Json(name = "tarif_st")
//    val tarifSt      : Double = 0.0,
//    val stoki        : Double = 0.0,
//    @Json(name = "date_st")
//    val dateSt       : String = "Unknown",
//    @Json(name = "date_fin")
//    val dateFin      : String = "Unknown",
    @Json(name = "pok_ot")
    val pokOt: Int = 0,
    @Json(name = "pok_do")
    val pokDo: Int = 0,
    val rday: Short = 0,
//    val mday         : Short = 0,
    @Json(name = "kub_day")
    val kubDay: Double = 0.0,
    @Json(name = "qty_kub")
    val qtyKub: Int = 0,
    @Json(name = "data_in")
//    val dataIn       : String = "Unknown",
    val operator: String = "Unknown"
)