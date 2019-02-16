package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.data.repository.IRepository
import com.arctouch.codechallenge.data.repository.Repository
import com.arctouch.codechallenge.data.service.TmdbApi
import com.arctouch.codechallenge.ui.home.HomeViewModel
import com.arctouch.codechallenge.util.SERVER_URL
import com.arctouch.codechallenge.util.TIMEOUT
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

//# DATA MODULE

val dataModule: Module = module {
    single { Repository(get()) as IRepository }
}

//# NETWORK MODULE

val networkModule: Module = module {
    single { createOkHttpClient() }
    single { createWebService<TmdbApi>(get(), SERVER_URL) }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}

//# VIEW MODEL MODULE

val viewModelModule: Module = module {
    viewModel { HomeViewModel(get()) }
}