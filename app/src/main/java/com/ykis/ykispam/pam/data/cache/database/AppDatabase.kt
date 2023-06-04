package com.ykis.ykispam.pam.data.cache.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.ykis.ykispam.pam.data.cache.dao.AppartmentDao
import com.ykis.ykispam.pam.data.cache.dao.FamilyDao
import com.ykis.ykispam.pam.data.cache.dao.HeatMeterDao
import com.ykis.ykispam.pam.data.cache.dao.HeatReadingDao
import com.ykis.ykispam.pam.data.cache.dao.PaymentDao
import com.ykis.ykispam.pam.data.cache.dao.ServiceDao
import com.ykis.ykispam.pam.data.cache.dao.WaterMeterDao
import com.ykis.ykispam.pam.data.cache.dao.WaterReadingDao
import com.ykis.ykispam.pam.domain.appartment.AppartmentEntity
import com.ykis.ykispam.pam.domain.family.FamilyEntity
import com.ykis.ykispam.pam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.pam.domain.heat.reading.HeatReadingEntity
import com.ykis.ykispam.pam.domain.payment.PaymentEntity
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import com.ykis.ykispam.pam.domain.water.meter.WaterMeterEntity
import com.ykis.ykispam.pam.domain.water.reading.WaterReadingEntity

@Database(
    entities = [
        AppartmentEntity::class,
        FamilyEntity::class,
        ServiceEntity::class,
        PaymentEntity::class,
        WaterMeterEntity::class,
        WaterReadingEntity::class,
        HeatMeterEntity::class,
        HeatReadingEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appartmentDao(): AppartmentDao
    abstract fun familyDao(): FamilyDao
    abstract fun serviceDao(): ServiceDao
    abstract fun paymentDao(): PaymentDao
    abstract fun waterMeterDao(): WaterMeterDao
    abstract fun waterReadingDao(): WaterReadingDao
    abstract fun heatMeterDao(): HeatMeterDao
    abstract fun heatReadingDao(): HeatReadingDao

    companion object {
        val DATABASE_NAME: String = "mykis_db"
    }
}