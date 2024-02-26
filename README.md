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
implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
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
    suspend fun getResponse(): JsonKeeperResponse
}

class JsonKeeperAPIImpl {
    private val BASE_URL = "https://jsonkeeper.com"

    suspend fun getResponse() = provideRetrofit().create(IJsonKeeperAPIClient::class.java).getResponse()

    private fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getUnsafeOkHttpClient())
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

### ViewModel

> Text that is a quote

``` kotlin
class JsonKeeperViewModel : ViewModel() {

    private var _livedataResponse = MutableLiveData<List<JsonKeeperItem>>()
    var livedataResponse: LiveData<List<JsonKeeperItem>> = _livedataResponse

    fun getJsonKeeper() {
        viewModelScope.launch(Dispatchers.IO) {
            _livedataResponse.postValue(JsonKeeperAPIImpl().getResponse().items)
        }
    }
}
```
### Adapter

> Text that is a quote

``` kotlin
class JsonKeeperAdapter(
    private val jsonKeeperList: ArrayList<JsonKeeperItem>
) : RecyclerView.Adapter<MyViewModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel =
        MyViewModel(
            LayoutInflater.from(parent.context).inflate(R.layout.jsonkeeper_item, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        holder.bindView(jsonKeeperList[position])
    }

    override fun getItemCount() = jsonKeeperList.size

    fun setNewList(newList: List<JsonKeeperItem>) {
        jsonKeeperList.clear()
        jsonKeeperList.addAll(newList)
        notifyDataSetChanged()
    }
}

class MyViewModel(itemView: View) : RecyclerView.ViewHolder(itemView.rootView) {
    val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
    val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
    val image = itemView.findViewById<ImageView>(R.id.imgJsonKeeper)

    fun bindView(data: JsonKeeperItem) {
        tvTitle.text = data.title
        tvDescription.text = data.description
        tvDate.text = data.date

        Glide.with(itemView.context).load(data.img).into(image)
    }
}
```

### Coroutines

> Text that is a quote

``` kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: JsonKeeperViewModel

    private var adapter = JsonKeeperAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.rvJsonKeeper)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter
        }

        viewModel = ViewModelProvider(this)[JsonKeeperViewModel::class.java]
        viewModel.getJsonKeeper()

        viewModel.livedataResponse.observe(this, Observer { jsonKeeperList ->
            //adapter= JsonKeeperAdapter(arrayListOf(jsonKeeperList.))
            adapter.setNewList(jsonKeeperList)
            recyclerView.adapter = adapter
        })
    }
}
```
