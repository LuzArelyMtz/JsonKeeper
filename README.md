### Repository

> Text that is a quote

``` kotlin
interface IRepository {
    suspend fun getJsonKeeperList(): Flow<Result<List<JsonKeeperItem>>>
}



class RepositoryImpl @Inject constructor(private val JsonKeeperAPI: IJsonKeeperAPIClient) :
    IRepository {
    override suspend fun getJsonKeeperList(): Flow<Result<List<JsonKeeperItem>>> {
        val list = JsonKeeperAPI.getResponse().items
        return flow {
            emit(Result.success(list))
        }
    }
}

```

### ViewModel

> Text that is a quote

``` kotlin
class JsonKeeperViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    private var _livedataResponse = MutableLiveData<List<JsonKeeperItem>>()
    var livedataResponse: LiveData<List<JsonKeeperItem>> = _livedataResponse


    fun getJsonKeeper() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getJsonKeeperList().collect { result ->
                result.onSuccess { list ->
                    _livedataResponse.postValue(list)
                }
            }
        }
    }
}
```



### Android Manifest

> Text that is a quote

``` xml
<application
        android:name=".MyApplication"
```
### Gradle Top Level

> Text that is a quote

``` gradle
id("com.google.dagger.hilt.android") version "2.51" apply false
```

### Gradle App

> Text that is a quote

``` gradle
plugins {
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

implementation("com.google.dagger:hilt-android:2.51")
kapt("com.google.dagger:hilt-android-compiler:2.51")
```

### MyApplication

> Text that is a quote

``` kotlin
@HiltAndroidApp
class MyApplication : Application()
```


### API Module

> Text that is a quote

``` kotlin

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


```
### Repository

> Text that is a quote

``` kotlin
interface IRepository {
    suspend fun getJsonKeeperList(): List<JsonKeeperItem>
}

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun  bindRepositoryImpl(impl : RepositoryImpl): IRepository
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
@InstallIn(ActivityComponent::class)
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}



@Module
@InstallIn(ActivityComponent::class)
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
@@AndroidEntryPoint
class ListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewmodel by lazy {
        ViewModelProvider(requireActivity(), this.viewModelFactory)[JsonKeeperViewModel::class.java]
    }
```


### MainActivity

> Text that is a quote

``` kotlin
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
```
  
