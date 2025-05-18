class LocationRepository(private val apiService: ApiService, private val db: AppDatabase) {
    suspend fun getLocations(lat: Double, lng: Double): List<Location> {
        return try {
            val apiLocations = apiService.getLocations(lat, lng, 5000).await()
            db.locationDao().insertAll(*apiLocations.toTypedArray())
            apiLocations
        } catch (e: Exception) {
            db.locationDao().getAll() // Fallback offline
        }
    }
}