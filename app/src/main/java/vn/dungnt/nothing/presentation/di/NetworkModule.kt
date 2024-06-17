package vn.dungnt.nothing.presentation.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.dungnt.nothing.data.sources.remote.APIService
import vn.dungnt.nothing.domain.entities.LanguageEntity
import vn.dungnt.nothing.domain.entities.LanguageType
import vn.dungnt.nothing.utils.Constants
import vn.dungnt.nothing.utils.SharedPrefs
import vn.dungnt.nothing.utils.convertFromJson
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAPIClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(Interceptor { chain ->
                val ongoing: Request.Builder = chain.request().newBuilder().apply {
                    header("Accept", "application/json")
                    header("Content-Type", "application/json")
                }
                val pref = SharedPrefs.getString(Constants.PREFS_LANGUAGE_MODEL, "")
                val language = if (pref.isNotEmpty()) pref.convertFromJson<LanguageEntity>()
                    .getLanguageCode() else LanguageType.ENGLISH.code
                ongoing.header("language", language)
                val accessToken = SharedPrefs.getString(Constants.PREFS_ACCESS_TOKEN, "")
                accessToken.takeIf { it.isNotEmpty() }?.apply {
                    ongoing.header("Authorization", "Bearer $accessToken")
                }
                chain.proceed(ongoing.build())
            })
            .addNetworkInterceptor {
                val request: Request =
                    it.request().newBuilder().addHeader("Connection", "Close").build()
                it.proceed(request)
            }
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitAp(): APIService {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl("https://api.mockfly.dev/")
            .client(
                provideAPIClient()
            ).build()
        return retrofit.create(APIService::class.java)
    }
}