package com.yondikavl.gerakgo.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(fusedLocationClient: FusedLocationProviderClient) {
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val context = LocalContext.current

    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var score by remember { mutableIntStateOf(0) }
    val visitedCheckpoints = remember { mutableStateListOf<LatLng>() }

    // Daftar checkpoint
    val checkpoints = listOf(
        LatLng(-6.175392, 106.827153), // Monas
        LatLng(-6.21462, 106.84513),   // Jakarta Pusat
        LatLng(-6.2666127, 106.6175838)  // Hampton Square
    )

    LaunchedEffect(locationPermission.status.isGranted) {
        if (locationPermission.status.isGranted) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val locationRequest = LocationRequest.Builder(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    2000
                ).build()

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    object : LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            val loc = result.lastLocation
                            loc?.let {
                                userLocation = LatLng(it.latitude, it.longitude)
                            }
                        }
                    },
                    Looper.getMainLooper()
                )
            }
        } else {
            locationPermission.launchPermissionRequest()
        }
    }

    Box(Modifier.fillMaxSize()) {
        if (userLocation != null) {
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(userLocation!!, 14f)
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = locationPermission.status.isGranted)
            ) {
                userLocation?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = "Lokasi Kamu",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                    )
                }

                checkpoints.forEach { point ->
                    val isVisited = visitedCheckpoints.contains(point)

                    Marker(
                        state = MarkerState(position = point),
                        title = if (isVisited) "Checkpoint (Sudah dikunjungi)" else "Checkpoint",
                        icon = BitmapDescriptorFactory.defaultMarker(
                            if (isVisited) BitmapDescriptorFactory.HUE_GREEN else BitmapDescriptorFactory.HUE_RED
                        )
                    )

                    Circle(
                        center = point,
                        radius = 1000.0,
                        strokeColor = if (isVisited) Color.Green else Color.Red,
                        fillColor = if (isVisited) Color(0x2200FF00) else Color(0x22FF0000)
                    )

                    userLocation?.let { uLoc ->
                        val results = FloatArray(1)
                        Location.distanceBetween(
                            uLoc.latitude, uLoc.longitude,
                            point.latitude, point.longitude,
                            results
                        )
                        val distance = results[0]

                        if (distance <= 1000 && !isVisited) {
                            visitedCheckpoints.add(point)
                            score += 10
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Text(
                text = "Score: $score",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
