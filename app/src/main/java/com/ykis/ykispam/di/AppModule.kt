package com.ykis.ykispam.di


import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ykis.ykispam.HiltApp
import com.ykis.ykispam.data.ApartmentRepositoryImpl
import com.ykis.ykispam.data.FamilyRepositoryImpl
import com.ykis.ykispam.data.HeatMeterRepositoryImpl
import com.ykis.ykispam.data.HeatReadingRepositoryImpl
import com.ykis.ykispam.data.PaymentRepositoryImpl
import com.ykis.ykispam.data.ServiceRepositoryImpl
import com.ykis.ykispam.data.WaterMeterRepositoryImpl
import com.ykis.ykispam.data.WaterReadingRepositoryImpl
import com.ykis.ykispam.data.cache.dao.ApartmentDao
import com.ykis.ykispam.data.cache.dao.FamilyDao
import com.ykis.ykispam.data.cache.dao.HeatMeterDao
import com.ykis.ykispam.data.cache.dao.HeatReadingDao
import com.ykis.ykispam.data.cache.dao.PaymentDao
import com.ykis.ykispam.data.cache.dao.ServiceDao
import com.ykis.ykispam.data.cache.dao.WaterMeterDao
import com.ykis.ykispam.data.cache.dao.WaterReadingDao
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.data.cache.preferences.AppSettingsRepository
import com.ykis.ykispam.data.cache.preferences.AppSettingsRepositoryImpl
import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.data.remote.api.ApiService.Companion.BASE_URL
import com.ykis.ykispam.data.remote.appartment.ApartmentRemote
import com.ykis.ykispam.data.remote.family.FamilyRemote
import com.ykis.ykispam.data.remote.heat.meter.HeatMeterRemote
import com.ykis.ykispam.data.remote.heat.reading.HeatReadingRemote
import com.ykis.ykispam.data.remote.payment.PaymentRemote
import com.ykis.ykispam.data.remote.service.ServiceRemote
import com.ykis.ykispam.data.remote.water.meter.WaterMeterRemote
import com.ykis.ykispam.data.remote.water.reading.WaterReadingRemote
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.domain.family.FamilyRepository
import com.ykis.ykispam.domain.meter.heat.meter.HeatMeterRepository
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingRepository
import com.ykis.ykispam.domain.meter.water.meter.WaterMeterRepository
import com.ykis.ykispam.domain.meter.water.reading.WaterReadingRepository
import com.ykis.ykispam.domain.payment.PaymentRepository
import com.ykis.ykispam.domain.service.ServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY

        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi,okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }



    @Provides
    fun provideApartmentDao(db: AppDatabase): ApartmentDao {
        return db.apartmentDao()
    }

    @Provides
    fun provideFamilyDao(db: AppDatabase): FamilyDao {
        return db.familyDao()
    }

    @Provides
    fun provideServiceDao(db: AppDatabase): ServiceDao {
        return db.serviceDao()
    }
    @Provides
    fun providePaymentDao(db: AppDatabase): PaymentDao {
        return db.paymentDao()
    }
    @Provides
    fun provideWaterMeterDao(db: AppDatabase): WaterMeterDao {
        return  db.waterMeterDao()
    }
    @Provides
    fun provideWaterReadingDao(db: AppDatabase): WaterReadingDao {
        return  db.waterReadingDao()
    }
    @Provides
    fun provideHeatMeterDao(db: AppDatabase): HeatMeterDao {
        return  db.heatMeterDao()
    }

    @Provides
    fun provideHeatReadingDao(db: AppDatabase): HeatReadingDao {
        return  db.heatReadingDao()
    }
    @Singleton
    @Provides
    fun provideApartmentRepository(
        apartmentRemote: ApartmentRemote,
    ): ApartmentRepository {
        return ApartmentRepositoryImpl(apartmentRemote)
    }

    @Singleton
    @Provides
    fun provideFamilyRepository(
        familyRemote: FamilyRemote,
    ): FamilyRepository {
        return FamilyRepositoryImpl( familyRemote )
    }

    @Singleton
    @Provides
    fun provideServiceRepository(
        serviceRemote: ServiceRemote,
    ): ServiceRepository {
        return ServiceRepositoryImpl(serviceRemote = serviceRemote )
    }

    @Singleton
    @Provides
    fun providePaymentRepository(
        paymentRemote: PaymentRemote,
    ): PaymentRepository {
        return PaymentRepositoryImpl( paymentRemote)
    }
    @Singleton
    @Provides
    fun provideWaterMeterRepository(
        waterMeterRemote: WaterMeterRemote,
    ): WaterMeterRepository {
        return  WaterMeterRepositoryImpl(waterMeterRemote)
    }
    @Singleton
    @Provides
    fun provideWaterReadingRepository(
        waterReadingRemote: WaterReadingRemote,
    ): WaterReadingRepository {
        return  WaterReadingRepositoryImpl(waterReadingRemote)
    }
    @Singleton
    @Provides
    fun provideHeatMeterRepository(
        heatMeterRemote: HeatMeterRemote,
    ): HeatMeterRepository {
        return  HeatMeterRepositoryImpl(heatMeterRemote)
    }
    @Singleton
    @Provides
    fun provideHeatReadingRepository(
        heatReadingRemote: HeatReadingRemote,
    ): HeatReadingRepository {
        return  HeatReadingRepositoryImpl(heatReadingRemote)
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    @Singleton
    @Provides
    fun providesDatastoreRepo(
        @ApplicationContext context: Context
    ): AppSettingsRepository = AppSettingsRepositoryImpl(context)

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): HiltApp {
        return app as HiltApp
    }
//    @Singleton
//    @Provides
//    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
//        return PreferenceDataStoreFactory.create(
//            corruptionHandler = ReplaceFileCorruptionHandler(
//                produceNewData = { emptyPreferences() }
//            ),
//            migrations = listOf(SharedPreferencesMigration(appContext,"user_preferences")),
//            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
//            produceFile = { appContext.preferencesDataStoreFile("user_preferences") }
//        )
//    }
}
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope