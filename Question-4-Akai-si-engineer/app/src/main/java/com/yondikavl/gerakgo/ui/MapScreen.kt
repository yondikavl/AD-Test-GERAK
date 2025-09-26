package com.yondikavl.gerakgo.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@OptIn(com.google.accompanist.permissions.ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(fusedLocationClient: FusedLocationProviderClient) {
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val context = LocalContext.current

    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var selectedPoint by remember { mutableStateOf<LatLng?>(null) }
    var radius by remember { mutableDoubleStateOf(0.0) }

    LaunchedEffect(locationPermission.status.isGranted) {
        if (locationPermission.status.isGranted) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        userLocation = LatLng(it.latitude, it.longitude)
                    }
                }
            }
        } else {
            locationPermission.launchPermissionRequest()
        }
    }

    Box(Modifier.fillMaxSize()) {
        val uLoc = userLocation
        if (uLoc != null) {
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(uLoc, 15f)
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = locationPermission.status.isGranted),
                onMapClick = { latLng ->
                    selectedPoint = latLng
                    val results = FloatArray(1)
                    Location.distanceBetween(
                        uLoc.latitude, uLoc.longitude,
                        latLng.latitude, latLng.longitude,
                        results
                    )
                    radius = (results[0] + 0.0).coerceAtLeast(10.0)
                }
            ) {
                Marker(
                    state = MarkerState(position = uLoc),
                    title = "Lokasi Anda"
                )

                selectedPoint?.let { sp ->
                    Marker(
                        state = MarkerState(position = sp),
                        title = "Titik Pilihan"
                    )
                }

                Circle(
                    center = uLoc,
                    radius = radius,
                    strokeColor = Color.Red,
                    fillColor = Color.Red.copy(alpha = 0.12f),
                    strokeWidth = 4f
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(48.dp))

            Card(
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = {
                            Toast
                                .makeText(
                                    context,
                                    "Atur dengan klik pada peta",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color(0xFFEC6C28)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Radius: ${radius.toInt()} m",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }

        }
    }
}
