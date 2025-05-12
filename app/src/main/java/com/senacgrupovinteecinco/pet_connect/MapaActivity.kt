package com.senacgrupovinteecinco.pet_connect

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Obter localização atual
        val locationClient = LocationServices.getFusedLocationProviderClient(this)
        try {
            locationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        val latLng = LatLng(it.latitude, it.longitude)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))

                        // Carregar locais próximos
                        loadNearbyLocations(it.latitude, it.longitude)
                    }
                }
        } catch (e: SecurityException) {
            // Tratar permissão negada
        }
    }

    private fun loadNearbyLocations(lat: Double, lng: Double) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://seuservidor.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call = service.getLocations(lat, lng, 5000) // 5km de raio

        call.enqueue(object : Callback<List<Location>> {
            override fun onResponse(call: Call<List<Location>>, response: Response<List<Location>>) {
                if (response.isSuccessful) {
                    response.body()?.forEach { location ->
                        val marker = map.addMarker(
                            MarkerOptions()
                                .position(LatLng(location.latitude, location.longitude))
                                .title(location.name)
                                .snippet(location.address)
                        )
                        marker?.tag = location.id
                    }
                }
            }

            override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                Toast.makeText(this@MapaActivity, "Falha ao carregar locais", Toast.LENGTH_SHORT).show()
            }
        })
    }
}