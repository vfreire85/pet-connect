@Database(entities = [Location::class, Favorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun favoriteDao(): FavoriteDao
}

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double
)

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations")
    fun getAll(): List<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg locations: Location)
}