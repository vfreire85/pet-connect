class ReviewActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val locationId = intent.getIntExtra("location_id", 0)
        apiService = RetrofitClient.create()

        // Enviar avaliação
        findViewById<Button>(R.id.submitReviewBtn).setOnClickListener {
            val rating = findViewById<RatingBar>(R.id.ratingBar).rating
            val comment = findViewById<EditText>(R.id.commentEditText).text.toString()

            apiService.submitReview(
                token = "Bearer ${getAuthToken()}",
                request = ReviewRequest(locationId, rating.toInt(), comment)
            ).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ReviewActivity, "Avaliação enviada!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(this@ReviewActivity, "Falha: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}