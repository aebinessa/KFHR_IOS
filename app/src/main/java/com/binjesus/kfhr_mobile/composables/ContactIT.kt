package com.binjesus.kfhr_mobile.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class ITEmployee(
    val name: String,
    val phoneNumber: String,
    val email: String
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ITContactScreen(navController: NavHostController) {
    val employees = listOf(
        ITEmployee("Mohammad Alshadad", "+96599990001", "mohammedalshadad@kfh.com"),
        ITEmployee("Khaled Alshatti", "+96590002345", "khaledalshatti@kfh.com"),
        ITEmployee("Omar Almutairi", "+96568765421", "omralmutairi@kfh.com")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Contact IT") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("WelcomeScreen") }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF078544),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Please contact one of the following officers to get your account setup:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                employees.forEach { employee ->
                    EmployeeCard(employee)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Usual response in 1-2 working days",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun EmployeeCard(employee: ITEmployee) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF078544)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Officer: ${employee.name}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
            Text(text = "Phone Number: ${employee.phoneNumber}", fontSize = 14.sp, color = Color.White)
            ClickableText(
                text = AnnotatedString(employee.email),
                onClick = { /* Handle email click */ },
                style = LocalTextStyle.current.copy(color = Color(0xFFBBDEFB), fontSize = 14.sp)
            )
        }
    }
}

/*@Composable
@Preview(showBackground = true)
fun PreviewITContactScreen() {
    ITContactScreen(navController)
}*/