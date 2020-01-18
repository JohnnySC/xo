package com.github.johnnysc.xoandroid.presentation

import android.app.Application
import com.github.johnnysc.xoandroid.BuildConfig
import com.github.johnnysc.xoandroid.data.CloudDataStore
import com.github.johnnysc.xoandroid.data.GameService
import com.github.johnnysc.xoandroid.data.PrefManager
import com.github.johnnysc.xoandroid.data.Repository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Asatryan on 18.01.20
 */
class App : Application() {

    companion object {
        const val DRAW = "DRAW"
        const val WIN = "WIN"
    }

    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        val prefManager = PrefManager(this)
        val host = prefManager.readString(PrefManager.HOST)
        val url = if (host.isNullOrEmpty()) "https://www.google.com" else host
        val retrofit = getRetrofit(getOkHttpClient(getInterceptor()), url)
        repository = Repository(prefManager, CloudDataStore(retrofit.create(GameService::class.java)))
        repository.generateUserId()
    }

    private fun getRetrofit(client: OkHttpClient, baseUrl: String) =
        Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    private fun getOkHttpClient(interceptor: Interceptor) =
        OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()


    private fun getInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }
}