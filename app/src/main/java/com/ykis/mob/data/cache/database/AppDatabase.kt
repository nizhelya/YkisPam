package com.ykis.mob.data.cache.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.ykis.mob.data.cache.dao.ApartmentDao
import com.ykis.mob.data.cache.dao.FamilyDao
import com.ykis.mob.data.cache.dao.HeatMeterDao
import com.ykis.mob.data.cache.dao.HeatReadingDao
import com.ykis.mob.data.cache.dao.PaymentDao
import com.ykis.mob.data.cache.dao.ServiceDao
import com.ykis.mob.data.cache.dao.WaterMeterDao
import com.ykis.mob.data.cache.dao.WaterReadingDao
import com.ykis.mob.di.ApplicationScope
import com.ykis.mob.domain.apartment.ApartmentEntity
import com.ykis.mob.domain.family.FamilyEntity
import com.ykis.mob.domain.meter.heat.meter.HeatMeterEntity
import com.ykis.mob.domain.meter.heat.reading.HeatReadingEntity
import com.ykis.mob.domain.meter.water.meter.WaterMeterEntity
import com.ykis.mob.domain.meter.water.reading.WaterReadingEntity
import com.ykis.mob.domain.payment.PaymentEntity
import com.ykis.mob.domain.service.ServiceEntity
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