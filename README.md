
### Android Manifest

> Text that is a quote

``` xml
<application
        android:name=".MyApplication"
```

### Gradle App

> Text that is a quote

``` gradle
plugins {
    id ("kotlin-kapt")
}

implementation("com.google.dagger:dagger:2.51")
    kapt("com.google.dagger:dagger-compiler:2.51")
```

### MyApplication

> Text that is a quote

``` kotlin
class MyApplication : Application() {
    lateinit var appComponent: ApplicationComponent

    private fun initDagger(application: MyApplication): ApplicationComponent =
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(application))
            .build()

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }
}
```

### @Component

> Text that is a quote

``` kotlin
@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, JsonKeeperAPIModule::class, RepositoryModule::class])
interface ApplicationComponent {
    fun inject(fragment: ListFragment)
}
```

### API Module

> Text that is a quote

``` kotlin
@Module
class JsonKeeperAPIModule {
    private val BASE_URL = "https://jsonkeeper.com"

    @Provides
    fun provideIJsonKeeperAPIClient(retrofit: Retrofit): IJsonKeeperAPIClient {
        return retrofit.create(IJsonKeeperAPIClient::class.java)
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
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
### Repository

> Text that is a quote

``` kotlin
interface IRepository {
    suspend fun getJsonKeeperList(): List<JsonKeeperItem>
}

class RepositoryImpl @Inject constructor(private val JsonKeeperAPI: IJsonKeeperAPIClient) :
    IRepository {
    override suspend fun getJsonKeeperList(): List<JsonKeeperItem> {
        return JsonKeeperAPI.getResponse().items
    }
}
```
### Modules

> Text that is a quote

``` kotlin
@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application
}

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(jsonKeeperAPIClient: IJsonKeeperAPIClient): IRepository =
        RepositoryImpl(jsonKeeperAPIClient)
}
```



### ViewModelFactory

> Text that is a quote

``` kotlin

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)



@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val viewModelsMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = viewModelsMap[modelClass] ?: viewModelsMap.asIterable().firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}



@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}



@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(JsonKeeperViewModel::class)
    abstract fun bindViewModel(viewModel: JsonKeeperViewModel): ViewModel
}

```


### Fragment

> Text that is a quote

``` kotlin
@Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewmodel by lazy {
        ViewModelProvider(requireActivity(), this.viewModelFactory)[JsonKeeperViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context

        (context.applicationContext as MyApplication).appComponent.inject(this)

    }
```
