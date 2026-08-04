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
import com.example.stockmap.BuildConfig

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
                                BuildConfig.SUPABASE_ANON_KEY
                            )
                                .addHeader("Authorization", "Bearer ${BuildConfig.SUPABASE_ANON_KEY}").build()
                        )
                    }
                    .build()
            )
            .baseUrl(BuildConfig.SUPABASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): StockMapApi {
        return retrofit.create(StockMapApi::class.java)
    }
}