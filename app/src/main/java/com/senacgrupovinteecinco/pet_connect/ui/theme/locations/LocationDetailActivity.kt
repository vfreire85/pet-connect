class LocationDetailActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_detail)

        // Inicializa o Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://seuservidor.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)

        // Botão de favorito
        findViewById<Button>(R.id.favoriteButton).setOnClickListener {
            val locationId = intent.getIntExtra("location_id", 0) // Assume que o ID foi passado via Intent
            val userId = getUserId() // Implemente este método (ex: SharedPreferences)
            val token = getAuthToken() // Recupere o token JWT salvo no login

            toggleFavorite(userId, locationId, token)
        }
    }

    private fun toggleFavorite(userId: Int, locationId: Int, token: String) {
        val call = apiService.addFavorite("Bearer $token", ApiService.FavoriteRequest(userId, locationId))

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                // --- TRATAMENTO DE CÓDIGOS HTTP AQUI ---
                when (response.code()) {
                    200 -> {
                        Toast.makeText(this@LocationDetailActivity, "Favoritado com sucesso!", Toast.LENGTH_SHORT).show()
                        updateFavoriteButtonUI(true) // Atualiza a UI
                    }
                    401 -> {
                        Toast.makeText(this@LocationDetailActivity, "Faça login novamente", Toast.LENGTH_SHORT).show()
                        redirectToLogin() // Redireciona para a tela de login
                    }
                    500 -> {
                        Toast.makeText(this@LocationDetailActivity, "Erro no servidor", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this@LocationDetailActivity, "Erro desconhecido: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@LocationDetailActivity, "Falha na rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }      
        })
    }

    // Métodos auxiliares (adicione em um Utils.kt se reutilizá-los)
    private fun getUserId(): Int {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPref.getInt("user_id", 0) // 0 é valor padrão se não existir
    }

    private fun getAuthToken(): String {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPref.getString("auth_token", "") ?: ""
    }

    private fun updateFavoriteButtonUI(isFavorite: Boolean) {
        val button = findViewById<Button>(R.id.favoriteButton)
        if (isFavorite) {
            button.text = "❤️ Remover Favorito"
            button.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_favorite_filled,  // Ícone de coração preenchido
                0, 
                0, 
                0
            )
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent)) // Cor de destaque
        } else {
            button.text = "❤️ Favoritar"
            button.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_favorite_border,  // Ícone de coração vazio
                0, 
                0, 
                0
            )
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)) // Cor padrão
        }
    }

    private fun redirectToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    // --- MÉTODO updateFavoriteButtonUI ADICIONADO AQUI ---
    private fun updateFavoriteButtonUI(isFavorite: Boolean) {
        val button = findViewById<Button>(R.id.favoriteButton)
        if (isFavorite) {
            button.text = "❤️ Remover Favorito"
            button.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_favorite_filled, // Ícone de coração preenchido
                0, 
                0, 
                0
            )
        } else {
            button.text = "❤️ Favoritar"
            button.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_favorite_border, // Ícone de coração vazio
                0, 
                0, 
                0
            )
        }
    }
}