package com.ykis.ykispam.domain.heat.meter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "heat_meter")
data class HeatMeterEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "teplomer_id")
    @Json(name = "teplomer_id")
    val teplomerId: Int = 0,
    @Json(name = "nomer")
    val number: String = "Unknown",
    val model: String = "Unknown",
    @ColumnInfo(name = "model_id")
    @Json(name = "model_id")
    val modelId: Int = 0,
    @Json(name = "address_id")
    @ColumnInfo(name = "address_id")
    val addressId: Int = 0,
    val edizm: String = "Unknown",
    val koef: String = "Unknown",
    val area: Double = 0.0,
    val sdate: String = "Unknown",
    val fpdate: String = "Unknown",
    val pdate: String = "Unknown",
    val out: Byte = 0,
    val spisan: Byte = 0,
    @ColumnInfo(name = "data_spis")
    @Json(name = "data_spis")
    val dataSpis: String = "Unknown",
    val work: Byte = 0
)