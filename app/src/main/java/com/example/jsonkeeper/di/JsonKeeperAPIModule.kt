package com.example.jsonkeeper.di

import com.example.jsonkeeper.api.IJsonKeeperAPIClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException
import org.apache.http.conn.ssl.SSLSocketFactory as SSLSocketFactoryApache
import javax.net.ssl.SSLSocketFactory as SSLSocketFactoryJavax

@Module
@InstallIn(ActivityComponent::class)
class JsonKeeperAPIModule {
    private val baseUrl = "https://jsonkeeper.com"

    @Provides
    fun provideIJsonKeeperAPIClient(retrofit: Retrofit): IJsonKeeperAPIClient {
        return retrofit.create(IJsonKeeperAPIClient::class.java)
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideClient(): OkHttpClient {
        return try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            val sslContext: SSLContext = SSLContext.getInstance("TLS")
            sslContext.init(
                null, trustAllCerts,
                SecureRandom()
            )
            val sslSocketFactory: SSLSocketFactoryJavax = sslContext
                .socketFactory
            var okHttpClient = OkHttpClient()
            okHttpClient = okHttpClient.newBuilder()
                .sslSocketFactory(sslSocketFactory)
                .hostnameVerifier(SSLSocketFactoryApache.ALLOW_ALL_HOSTNAME_VERIFIER).build()
            okHttpClient
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}