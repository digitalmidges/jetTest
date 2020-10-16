package com.digitalmidges.jettest.di

import android.app.Application
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.managers.PreferencesHelper
import com.digitalmidges.jettest.network.api.ServiceApi
import com.digitalmidges.jettest.network.api.MyCallAdapterFactory
import com.digitalmidges.jettest.network.NetworkConsts
import com.google.gson.*
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class]) class AppModule {

    @Singleton
    @Provides
    fun provideServiceApi(application: Application, client: OkHttpClient?): ServiceApi {


        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date?> {
            var df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)

            @Throws(JsonParseException::class)
            override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
                return try {
                    df.parse(json.asString)
                } catch (e: ParseException) {
                    null
                }
            }
        })
        val gson = gsonBuilder.create() // fix the bug when date is empty or null

        return Retrofit.Builder()
            .baseUrl(NetworkConsts.BASE_URL)
            .client(client!!)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(MyCallAdapterFactory()) // the custom live data with loading, success, error...
            .build()
            .create(ServiceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor? {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideClient(interceptor: HttpLoggingInterceptor?): OkHttpClient? {
        return interceptor?.let {
            OkHttpClient.Builder()
                .addNetworkInterceptor { chain: Interceptor.Chain ->
                    // Add Auth Header
//                    val brearToken = PreferencesHelper.getBearerToken() ?: ""
                    val brearToken = "test"
                    val auth = "Bearer $brearToken"
                    val request: Request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", auth)
                        .build()
                    chain.proceed(request)
                }
                .addInterceptor(it)
                .build()
        }
    }

    @Singleton
    @Provides
    fun isTablet(application: Application): Boolean {
        return application.resources.getBoolean(R.bool.isTablet)
    }


}
