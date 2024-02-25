### Internet Permission

> Text that is a quote

``` xml
<uses-permission android:name="android.permission.INTERNET"/>
```

### Gradle dependencies

> Text that is a quote

``` gradle
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
implementation("io.reactivex.rxjava3:rxjava:3.1.8")
implementation ("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
```

### Data classes

> Text that is a quote

``` kotlin
data class JsonKeeperResponse(val items: List<JsonKeeperItem>)
data class JsonKeeperItem(
    @SerializedName("title", alternate = ["Title"])
    val title: String
)

class TypicodeResponse : ArrayList<TypicodeResponseItem>()
```

### Retrofit API service call

> Text that is a quote

``` kotlin
interface IJsonKeeperAPIClient {
    @GET("/b/WN0G")
    fun getResponse(): Single<JsonKeeperResponse>
}

class JsonKeeperAPIImpl {
    private val BASE_URL = "https://jsonkeeper.com"

    fun getResponse() = provideRetrofit().create(IJsonKeeperAPIClient::class.java).getResponse()

    private fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getUnsafeOkHttpClient())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun getUnsafeOkHttpClient(): OkHttpClient? {
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
                .getSocketFactory()
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
```

### RxJava3

> Text that is a quote

``` kotlin
private lateinit var tvJsonResponse: TextView
private val disposable = CompositeDisposable()

tvJsonResponse = findViewById(R.id.tvJsonResponse)
        disposable.add(
            JsonKeeperAPIImpl().getResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JsonKeeperResponse>() {
                    override fun onSuccess(response: JsonKeeperResponse) {
                        tvJsonResponse.text = response.toString()
                    }

                    override fun onError(e: Throwable) {
                        println(e.printStackTrace())
                    }
                })
        )
```
