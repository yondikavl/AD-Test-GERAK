package com.yondikavl.question6.ui.employee

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yondikavl.question6.viewmodel.EmployeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeScreen(viewModel: EmployeeViewModel = viewModel()) {
    val employees by viewModel.employees.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (employees.isEmpty()) {
            viewModel.fetchEmployeesSafe()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Employee List",
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color(color = 0xFF85BAB5)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF348C83),
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFEEF4F4))
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(employees) { employee ->
                            EmployeeItem(employee)
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Tentang Question 6 Akai si Engineer 3") },
                text = {
                    Text(
                        buildAnnotatedString {
                            append("Wah! Akai sekarang sudah bisa membuat banyak project berkatmu. ")
                            append("Saking banyaknya, codenya jadi berantakan.\n\n")
                            append("Tapi tenang, ada cara biar project lebih rapi yaitu dengan mempelajari MVVM pattern.\n\n")
                            append("Nah di aplikasi ini, data Employee dari API ")

                            withStyle(
                                style = SpanStyle(
                                    color = Color(color = 0xFF348C83),
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("http://dummy.restapiexample.com/api/v1/employees")
                            }

                            append(" sudah ditampilkan dalam bentuk List dengan menerapkan MVVM pattern.")
                        }
                    )
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Tutup", color = Color(0xFF348C83))
                    }
                },
                containerColor = Color.White
            )
        }
    }
}
