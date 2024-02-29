package com.ykis.ykispam.domain.water.reading

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
    @ColumnInfo(name = "date_ot")
    @Json(name = "date_ot")
    val dateOt: String = "2024-01-01",
    @ColumnInfo(name = "date_do")
    @Json(name = "date_do")
    val dateDo: String = "2024-01-01",
    val days: Short = 0,
    val last: Int = 0,
    // TODO: current 
    val currant: Int = 0,
    val kub: Short = 0,
    val avg: Byte = 0,
    @Json(name = "pok_ot")
    val pokOt: Int = 0,
    @Json(name = "pok_do")
    val pokDo: Int = 0,
    val rday: Short = 0,
    @Json(name = "kub_day")
    val kubDay: Double = 0.0,
    @Json(name = "qty_kub")
    val qtyKub: Int = 0,
    @Json(name = "data_in")
    val operator: String = "Unknown"
)