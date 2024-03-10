package com.ykis.ykispam.domain.family

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "family")
data class FamilyEntity(
    @PrimaryKey(autoGenerate = false)
    @Json(name = "rec_id")
    @ColumnInfo(name = "rec_id")
    val recId: Int = 0,
    @Json(name = "address_id")
    @ColumnInfo(name = "address_id")
    val addressId: Int = 0,
    val rodstvo: String = "Unknown",
    @Json(name = "firstname")
    @ColumnInfo(name = "firstname")
    val fistname: String = "Unknown",
    @Json(name = "lastname")
    @ColumnInfo(name = "lastname")
    val lastname: String = "Unknown",
    @Json(name = "surname")
    @ColumnInfo(name = "surname")
    val surname: String = "Unknown",

    val born: String = "Unknown",

    val sex: String = "Unknown",

    var phone: String = "Unknown",

    val subsidia: Byte = 0,

    val vkl: Byte = 0,

    val inn: String = "Unknown",

    val document: String = "Unknown",

    val seria: String = "Unknown",

    val nomer: String = "Unknown",

    val datav: String? = "Unknown",

    val organ: String = "Unknown"

)
