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
import com.binjesus.kfhr_mobile.ui.theme.DarkGreen
import com.binjesus.kfhr_mobile.ui.theme.LightGreen
import com.binjesus.kfhr_mobile.utils.Route

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
        ITEmployee("Ahmad", "+96599640840", "ahmad@kfh.com"),
        ITEmployee("Ahmad", "+96599640840", "ahmad@kfh.com"),
        ITEmployee("Ahmad", "+96599640840", "ahmad@kfh.com")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Contact IT") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Route.WelcomeRoute) }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = LightGreen,
                    titleContentColor = DarkGreen,
                    navigationIconContentColor = DarkGreen
                )
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(it).padding(8.dp)
        ) {
            employees.forEach { employee ->
                EmployeeCard(employee)
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
        colors = CardDefaults.cardColors(containerColor = DarkGreen),
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
