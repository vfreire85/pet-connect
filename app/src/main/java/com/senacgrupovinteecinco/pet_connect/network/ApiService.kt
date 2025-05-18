import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // ... (outros endpoints existentes)

    // Novo endpoint para favoritos
    @POST("favorites")
    fun addFavorite(
        @Header("Authorization") token: String,
        @Body request: FavoriteRequest
    ): Call<ApiResponse>

    // Classe para o corpo da requisição
    data class FavoriteRequest(
        val user_id: Int,
        val location_id: Int
    )
}