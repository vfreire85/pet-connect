import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

object LocationUtils {

    // Método para obter a localização atual
    fun getCurrentLocation(
        activity: Activity,
        onSuccess: (lat: Double, lng: Double) -> Unit,
        onFailure: () -> Unit = {}
    ) {
        val locationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activity)

        // Verifica permissão
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        onSuccess(location.latitude, location.longitude)
                    } else {
                        Toast.makeText(activity, "Localização não disponível", Toast.LENGTH_SHORT).show()
                        onFailure()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(activity, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
                    onFailure()
                }
        } else {
            // Solicita permissão se não tiver
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            onFailure()
        }
    }

    const val REQUEST_LOCATION_PERMISSION = 1001
}