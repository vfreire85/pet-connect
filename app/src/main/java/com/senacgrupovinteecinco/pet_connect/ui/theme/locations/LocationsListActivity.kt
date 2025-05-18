class LocationsListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_list)

        recyclerView = findViewById(R.id.locationsRecyclerView)
        progressBar = findViewById(R.id.progressBar)

        // Configura o RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        // Busca locais próximos
        loadNearbyLocations()
    }

    private fun loadNearbyLocations() {
        getCurrentLocation { lat, lng ->
            val call = apiService.getLocations(lat, lng, 5000)

            progressBar.visibility = View.VISIBLE

            val retrofit = Retrofit.Builder()
                .baseUrl("https://seuservidor.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(ApiService::class.java)
            val call = service.getLocations(
                lat = -23.5505,  // Substituir por localização real
                lng = -46.6333,
                radius = 5000
            )

            call.enqueue(object : Callback<List<Location>> {
                override fun onResponse(call: Call<List<Location>>, response: Response<List<Location>>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        val adapter = LocationsAdapter(response.body()!!) { location ->
                            // Abre detalhes ao clicar
                            val intent = Intent(this@LocationsListActivity, LocationDetailActivity::class.java)
                            intent.putExtra("location", location)
                            startActivity(intent)
                        }
                        recyclerView.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@LocationsListActivity, "Falha ao carregar locais", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}