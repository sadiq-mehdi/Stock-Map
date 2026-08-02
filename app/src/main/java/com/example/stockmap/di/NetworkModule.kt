package com.example.stockmap.di

import com.example.stockmap.data.remote.api.StockMapApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.proceed(
                            chain.request().newBuilder().addHeader(
                                "apikey",
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InltdXVyYmpsbmN1bnpleWZ4c2JyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODU2ODM0NTMsImV4cCI6MjEwMTI1OTQ1M30.QqriXATCVEmCDiTy7uH_6HXGQCO69COIgBc9JL3rT-M"
                            )
                                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InltdXVyYmpsbmN1bnpleWZ4c2JyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODU2ODM0NTMsImV4cCI6MjEwMTI1OTQ1M30.QqriXATCVEmCDiTy7uH_6HXGQCO69COIgBc9JL3rT-M").build()
                        )
                    }
                    .build()
            )
            .baseUrl("https://ymuurbjlncunzeyfxsbr.supabase.co/rest/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): StockMapApi {
        return retrofit.create(StockMapApi::class.java)
    }
}