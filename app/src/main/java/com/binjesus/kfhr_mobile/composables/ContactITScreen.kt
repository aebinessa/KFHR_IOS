package com.binjesus.kfhr_mobile.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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
import androidx.navigation.compose.rememberNavController

data class ITEmployee(
    val name: String,
    val phoneNumber: String,
    val email: String
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactItScreen(navController: NavHostController) {
    val employees = listOf(
        ITEmployee("Jacqueline Salford", "+9665435678", "jacquelinesalford@abcd.com"),
        ITEmployee("Elon Tusk", "+9655643567", "elontusk@abcd.com"),
        ITEmployee("Abigail", "+9656345768765", "abigail@abcd.com")
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF078544)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Officer: ${employee.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
            Text(
                text = "Phone Number: ${employee.phoneNumber}",
                fontSize = 16.sp,
                color = Color.White
            )
            ClickableText(
                text = AnnotatedString(employee.email),
                onClick = { /* Handle email click */ },
                style = LocalTextStyle.current.copy(
                    color = Color(0xFFBBDEFB),
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewITContactScreen() {
    ContactItScreen(navController = rememberNavController())
}
