package com.ykis.ykispam.pam.domain.payment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "payment")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = false)
    @Json(name = "rec_id")
    @ColumnInfo(name = "rec_id")
    val recID: Int = 0,

    @Json(name = "address_id")
    @ColumnInfo(name = "address_id")
    val addressID: Int = 0,

    val address: String = "Unknown",
    val year: Short = 0,
    val data: String = "Unknown",
    val kvartplata: Double = 0.00,
    val remont: Double = 0.00,
    val otoplenie: Double = 0.00,
    val voda: Double = 0.00,
    val tbo: Double = 0.00,
    val summa: Double = 0.00,
    val prixod: String = "Unknown",
    val kassa: String = "Unknown",
    val nomer: String = "Unknown",

    @Json(name = "data_in")
    @ColumnInfo(name = "data_in")
    val dataIn: String = "Unknown",
)