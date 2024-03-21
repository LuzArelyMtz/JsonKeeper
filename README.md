### Internet Permission

> Text that is a quote

``` xml
<uses-permission android:name="android.permission.INTERNET"/>
```

### Gradle dependencies

> Text that is a quote

``` gradle

viewBinding{
        enable =true
}

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
) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            JsonkeeperItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(jsonKeeperList[position])
    }

    override fun getItemCount() = jsonKeeperList.size

    fun setNewList(newList: List<JsonKeeperItem>) {
        jsonKeeperList.clear()
        jsonKeeperList.addAll(newList)
        notifyDataSetChanged()
    }
}

class MyViewHolder(private val binding: JsonkeeperItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: JsonKeeperItem) {
        binding.tvTitle.text = data.title
        binding.tvDescription.text = data.description
        binding.tvDate.text = data.date

        Glide.with(binding.root.context).load(data.img).into(binding.imgJsonKeeper)
    }
}
```

### Coroutines

> Text that is a quote

``` kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: JsonKeeperViewModel

    private var adapter = JsonKeeperAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvJsonKeeper.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter
        }

        viewModel = ViewModelProvider(this)[JsonKeeperViewModel::class.java]
        viewModel.getJsonKeeper()

        viewModel.livedataResponse.observe(this, Observer { jsonKeeperList ->
            //adapter= JsonKeeperAdapter(arrayListOf(jsonKeeperList.))
            adapter.setNewList(jsonKeeperList)
            binding.rvJsonKeeper.adapter = adapter
        })
    }
}
```

### Fragment

> Text that is a quote

``` kotlin
if (savedInstanceState == null) {
            var transition = supportFragmentManager.beginTransaction()
            transition.add(R.id.maincontainer, GridViewFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
 }


 val fragment = DetailGiftFragment.newInstance()
                requireActivity().supportFragmentManager
                    /*.commit {
                    replace<DetailGiftFragment>(R.id.maincontainer)
                    addToBackStack("GridViewFragment")
                }*/
                    .beginTransaction()
                    .replace(R.id.maincontainer, fragment)
                    .addToBackStack("GridViewFragment")
                    .commit()

class DetailGiftFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = DetailGiftFragment().apply {

val gridfragmentBiding = GridviewFragmentBinding.inflate(inflater, container, false)
        binding = gridfragmentBiding
        return gridfragmentBiding.root


<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/maincontainer"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.MainActivity" />

```


### Nav Graph

> Text that is a quote

``` kotlin

popStackBack()

<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/navigation_main"
    app:startDestination="@id/listFragment">

    <fragment
        android:id="@+id/listFragment"
        android:name="com.luz.codingchallenge.ui.ListFragment"
        android:label="list_fragment"
        tools:layout="@layout/list_fragment" >
        <action
            android:id="@+id/action_listFragment_to_detailsFragment"
            app:destination="@id/detailsFragment" />
    </fragment>
    <fragment
        android:id="@+id/detailsFragment"
        android:name="com.luz.codingchallenge.ui.DetailsFragment"
        android:label="detail_fragment"
        tools:layout="@layout/detail_fragment" >
        <argument
            android:name="itemAPI"
            app:argType="com.luz.codingchallenge.api.model.ItemAPI" />
    </fragment>

private val listAdapter = AdapterExample(arrayListOf()) { itemSelected ->
        val action = ListFragmentDirections.actionListFragmentToDetailsFragment(itemSelected)
        findNavController().navigate(action)
    }


class AdapterExample(
    private val list: ArrayList<ItemAPI>,
    private val itemSelectedListener: (ItemAPI) -> Unit
)

private val args: DetailsFragmentArgs by navArgs()
```
