package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.binjesus.kfhr_mobile.R
import java.util.*




@Composable
fun CertificatesApp(navController: NavHostController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add certificate action */ },
                backgroundColor = Color.Green,
                contentColor = Color.White
            ) {
                Text("+", fontSize = 24.sp)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false,
    ) { innerPadding ->
        // Add padding provided by scaffold to avoid overlapping with FAB
        Box(modifier = Modifier.padding(innerPadding)) {
            CertificatesScreen(navController = navController)
        }
    }
}

@Composable
fun CertificatesScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        CertificateList()
    }
}

@Composable
fun Header(navController: NavHostController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "My Certificates:",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = { /* Recommended action */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Text("Recommended", color = Color.White)
        }
        IconButton(onClick = { /* Notification action */ }) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.bell),
                contentDescription = "Notifications"
            )
        }
    }
}

@Composable
fun CertificateList() {
    val certificates = List(5) { "Certificate ${it + 1}" }
    LazyColumn {
        items(certificates) { certificate ->
            CertificateCard(certificate)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CertificateCard(certificate: String) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = Color(0xFFF5F5F5) // light gray background color
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp)
        ) {
            Text(
                text = certificate,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = "Supporting line text lorem ipsum dolor sit amet...",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CertificatesScreenPreview() {
    val navController = rememberNavController()
    CertificatesApp(navController = navController)
}
