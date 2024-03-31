
### Internet Permission

> Text that is a quote

``` xml
<uses-permission android:name="android.permission.INTERNET"/>
```

### Gradle Project top level

> Text that is a quote

``` gradle
id("androidx.navigation.safeargs.kotlin") version "2.7.1" apply false
```

### Gradle App

> Text that is a quote

``` gradle
plugins {
    id ("androidx.navigation.safeargs.kotlin")
    id ("kotlin-parcelize")
}

viewBinding{
        enable =true
}

implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
implementation ("androidx.fragment:fragment-ktx:1.5.4")
implementation ("com.github.bumptech.glide:glide:4.16.0")
implementation("androidx.navigation:navigation-dynamic-features-fragment:2.7.7")
implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
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
    private var _livedataJsonKeeperItem = MutableLiveData<JsonKeeperItem>()
    var livedataJsonKeeperItem: LiveData<JsonKeeperItem> = _livedataJsonKeeperItem

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
    private val jsonKeeperList: ArrayList<JsonKeeperItem>,
    private val onItemClickListener: OnItemClickListener
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
        holder.bind(jsonKeeperList[position], onItemClickListener)
    }

    override fun getItemCount() = jsonKeeperList.size

    fun setNewList(newList: List<JsonKeeperItem>) {
        jsonKeeperList.clear()
        jsonKeeperList.addAll(newList)
        notifyDataSetChanged()
    }
}

class MyViewHolder(private val binding: JsonkeeperItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: JsonKeeperItem, listener: OnItemClickListener) {
        binding.tvTitle.text = data.title
        binding.tvDescription.text = data.description
        binding.tvDate.text = data.date

        Glide.with(binding.root.context).load(data.img).into(binding.imgJsonKeeper)
        itemView.setOnClickListener({
            listener.onClick(itemView, data)
        })
    }
}

interface OnItemClickListener {
    fun onClick(v: View?, jsonKeeperItem: JsonKeeperItem)
}

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:card_view="http://schemas.android.com/apk/res-auto"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/jsonkeeper_item"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <com.google.android.material.card.MaterialCardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        card_view:cardCornerRadius="0dp"
        card_view:cardElevation="2dp"
        tools:ignore="MissingConstraints">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="10dp">

            <TextView
                android:id="@+id/tvTitle"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Title"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintBottom_toTopOf="@+id/tvDescription"/>

            <TextView
                android:id="@+id/tvDescription"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Description"
                app:layout_constraintTop_toBottomOf="@+id/tvTitle"
                app:layout_constraintBottom_toTopOf="@+id/tvDate" />

            <TextView
                android:id="@+id/tvDate"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Date"
                app:layout_constraintTop_toBottomOf="@+id/tvDescription"
                app:layout_constraintBottom_toTopOf="@+id/imgJsonKeeper"/>

            <ImageView
                android:id="@+id/imgJsonKeeper"
                android:layout_width="200dp"
                android:layout_height="200dp"
                app:layout_constraintLeft_toLeftOf="parent"
                app:layout_constraintRight_toRightOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/tvDate"
                app:layout_constraintBottom_toBottomOf="parent" />

        </androidx.constraintlayout.widget.ConstraintLayout>

    </com.google.android.material.card.MaterialCardView>

</androidx.constraintlayout.widget.ConstraintLayout>
```



### MainActivity

> Text that is a quote

``` kotlin

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="ui.MainActivity">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/main_fragment_container"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:navGraph="@navigation/nav_main"
        app:defaultNavHost="true"/>
</androidx.constraintlayout.widget.ConstraintLayout>


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

```

### Nav Graph

> Create the nav graph xml clicking in Android Resource Directory and then choose in value navigation. Then go Resource Manager and click on +

``` kotlin

<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_main"
    app:startDestination="@id/listFragment">

    <fragment
        android:id="@+id/listFragment"
        android:name="com.example.jsonkeeper.ui.ListFragment"
        android:label="ListFragment"
        tools:layout="@layout/list_fragment">
        <action
            android:id="@+id/action_listFragment_to_detailsFragment"
            app:destination="@id/detailsFragment" />
    </fragment>
    <fragment
        android:id="@+id/detailsFragment"
        android:name="com.example.jsonkeeper.ui.DetailsFragment"
        android:label="DetailsFragment"
        tools:layout="@layout/details_fragment">
        <argument
            android:name="jsonKeeperItem"
            app:argType="com.example.jsonkeeper.api.model.JsonKeeperItem" />
    </fragment>
</navigation>

```


### Fragment

> Text that is a quote

``` kotlin
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rvJsonKeeper"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="18dp">
        <TextView
            android:id="@+id/tvTitle"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Title"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/tvDescription"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Description"
            app:layout_constraintBottom_toTopOf="@+id/tvDate"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvTitle" />

        <TextView
            android:id="@+id/tvDate"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Date"
            app:layout_constraintBottom_toTopOf="@+id/image"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvDescription" />

        <ImageView
            android:id="@+id/image"
            android:layout_width="200dp"
            android:layout_height="200dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvDate" />
</androidx.constraintlayout.widget.ConstraintLayout>



class ListFragment : Fragment() {
    private lateinit var binding: ListFragmentBinding
    private lateinit var adapter: JsonKeeperAdapter
    //private val sharedViewModel: JsonKeeperViewModel by activityViewModels()
    private lateinit var viewmodel: JsonKeeperViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(this)
            .get(JsonKeeperViewModel::class.java)

        binding.rvJsonKeeper.layoutManager = LinearLayoutManager(context)
        adapter = JsonKeeperAdapter(arrayListOf(), object : OnItemClickListener {
            override fun onClick(v: View?, data: JsonKeeperItem) {
                viewmodel.setJsonKeeperItem(data)

                val action = ListFragmentDirections.actionListFragmentToDetailsFragment(data)
                findNavController().navigate(action)

            }
        })
        binding.rvJsonKeeper.adapter = adapter

        viewmodel.getJsonKeeper()

        viewmodel.livedataResponse.observe(requireActivity(), Observer { jsonKeeperList ->
            adapter.setNewList(jsonKeeperList)
        })
    }
}




class DetailsFragment : Fragment() {

    //private val sharedViewModel: JsonKeeperViewModel by activityViewModels()
    private lateinit var binding: DetailsFragmentBinding
    private lateinit var context: Context
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var jsonKeeperItem:JsonKeeperItem

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jsonKeeperItem = args.jsonKeeperItem
        //sharedViewModel.livedataJsonKeeperItem.observe(requireActivity(), Observer {
            binding.tvDescription.text = jsonKeeperItem.title
            binding.tvDescription.text = jsonKeeperItem.description
            binding.tvDate.text = jsonKeeperItem.date
            Glide.with(context).load(jsonKeeperItem.img).into(binding.image)
       // })
    }
}
```
