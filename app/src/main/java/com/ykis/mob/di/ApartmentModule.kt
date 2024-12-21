package com.ykis.mob.di

import com.ykis.mob.data.cache.apartment.ApartmentCache
import com.ykis.mob.data.cache.apartment.ApartmentCacheImpl
import com.ykis.mob.data.cache.family.FamilyCache
import com.ykis.mob.data.cache.family.FamilyCacheImpl
import com.ykis.mob.data.cache.heat.meter.HeatMeterCache
import com.ykis.mob.data.cache.heat.meter.HeatMeterCacheImpl
import com.ykis.mob.data.cache.heat.reading.HeatReadingCache
import com.ykis.mob.data.cache.heat.reading.HeatReadingCacheImpl
import com.ykis.mob.data.cache.payment.PaymentCache
import com.ykis.mob.data.cache.payment.PaymentCacheImpl
import com.ykis.mob.data.cache.service.ServiceCache
import com.ykis.mob.data.cache.service.ServiceCacheImpl
import com.ykis.mob.data.cache.water.meter.WaterMeterCache
import com.ykis.mob.data.cache.water.meter.WaterMeterCacheImpl
import com.ykis.mob.data.cache.water.reading.WaterReadingCache
import com.ykis.mob.data.cache.water.reading.WaterReadingCacheImpl
import com.ykis.mob.data.remote.appartment.ApartmentRemote
import com.ykis.mob.data.remote.appartment.ApartmentRemoteImpl
import com.ykis.mob.data.remote.family.FamilyRemote
import com.ykis.mob.data.remote.family.FamilyRemoteImpl
import com.ykis.mob.data.remote.heat.meter.HeatMeterRemote
import com.ykis.mob.data.remote.heat.meter.HeatMeterRemoteImpl
import com.ykis.mob.data.remote.heat.reading.HeatReadingRemote
import com.ykis.mob.data.remote.heat.reading.HeatReadingRemoteImpl
import com.ykis.mob.data.remote.payment.PaymentRemote
import com.ykis.mob.data.remote.payment.PaymentRemoteImpl
import com.ykis.mob.data.remote.service.ServiceRemote
import com.ykis.mob.data.remote.service.ServiceRemoteImpl
import com.ykis.mob.data.remote.water.meter.WaterMeterRemote
import com.ykis.mob.data.remote.water.meter.WaterMeterRemoteImpl
import com.ykis.mob.data.remote.water.reading.WaterReadingRemote
import com.ykis.mob.data.remote.water.reading.WaterReadingRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ApartmentModule {

    @Singleton
    @Binds
    abstract fun bindApartmentCache(impl: ApartmentCacheImpl): ApartmentCache

    @Singleton
    @Binds
    abstract fun bindApartmentRemote(impl: ApartmentRemoteImpl): ApartmentRemote

    @Singleton
    @Binds
    abstract fun bindFamilyCache(impl: FamilyCacheImpl): FamilyCache

    @Singleton
    @Binds
    abstract fun bindFamilyRemote(impl: FamilyRemoteImpl): FamilyRemote


    @Singleton
    @Binds
    abstract fun bindServiceCache(impl: ServiceCacheImpl): ServiceCache

    @Singleton
    @Binds
    abstract fun bindServiceRemote(impl: ServiceRemoteImpl): ServiceRemote

    @Singleton
    @Binds
    abstract fun bindPaymentCache(impl: PaymentCacheImpl): PaymentCache

    @Singleton
    @Binds
    abstract fun bindPaymentRemote(impl: PaymentRemoteImpl): PaymentRemote

    @Singleton
    @Binds
    abstract fun bindWaterMeterCache(impl: WaterMeterCacheImpl): WaterMeterCache

    @Singleton
    @Binds
    abstract fun bindWaterMeterRemote(impl: WaterMeterRemoteImpl): WaterMeterRemote

    @Singleton
    @Binds
    abstract fun bindWaterReadingCache(impl: WaterReadingCacheImpl): WaterReadingCache

    @Singleton
    @Binds
    abstract fun bindWaterReadingRemote(impl: WaterReadingRemoteImpl): WaterReadingRemote

    @Singleton
    @Binds
    abstract fun bindHeatMeterCache(impl: HeatMeterCacheImpl): HeatMeterCache

    @Singleton
    @Binds
    abstract fun bindHeatMeterRemote(impl: HeatMeterRemoteImpl): HeatMeterRemote

    @Singleton
    @Binds
    abstract fun bindHeatReadingCache(impl: HeatReadingCacheImpl): HeatReadingCache

    @Singleton
    @Binds
    abstract fun bindHeatReadingRemote(impl: HeatReadingRemoteImpl): HeatReadingRemote
  }