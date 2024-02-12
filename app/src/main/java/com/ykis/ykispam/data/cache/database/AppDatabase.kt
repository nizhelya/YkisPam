package com.ykis.ykispam.data.cache.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.ykis.ykispam.di.ApplicationScope
import com.ykis.ykispam.data.cache.dao.ApartmentDao
import com.ykis.ykispam.data.cache.dao.FamilyDao
import com.ykis.ykispam.data.cache.dao.HeatMeterDao
import com.ykis.ykispam.data.cache.dao.HeatReadingDao
import com.ykis.ykispam.data.cache.dao.PaymentDao
import com.ykis.ykispam.data.cache.dao.ServiceDao
import com.ykis.ykispam.data.cache.dao.WaterMeterDao
import com.ykis.ykispam.data.cache.dao.WaterReadingDao
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.family.FamilyEntity
import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.heat.reading.HeatReadingEntity
import com.ykis.ykispam.domain.payment.PaymentEntity
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.water.meter.WaterMeterEntity
import com.ykis.ykispam.domain.water.reading.WaterReadingEntity
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider


@Database(
    entities = [
        ApartmentEntity::class,
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
    abstract fun apartmentDao(): ApartmentDao
    abstract fun familyDao(): FamilyDao
    abstract fun serviceDao(): ServiceDao
    abstract fun paymentDao(): PaymentDao
    abstract fun waterMeterDao(): WaterMeterDao
    abstract fun waterReadingDao(): WaterReadingDao
    abstract fun heatMeterDao(): HeatMeterDao
    abstract fun heatReadingDao(): HeatReadingDao

    companion object {
        const val DATABASE_NAME: String = "mykis_db"
    }
    class Callback @Inject constructor(
        private val database: Provider<AppDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}