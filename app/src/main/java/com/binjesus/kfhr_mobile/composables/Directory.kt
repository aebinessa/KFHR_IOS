package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.models.Employee
import com.binjesus.kfhr_mobile.utils.Route
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel
import java.util.Date

@Composable
fun EmployeeDirectoryScreen(navController: NavHostController,
                            viewModel: KFHRViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Surface(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Top Bar with profile, search, and notifications
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = rememberImagePainter("https://example.com/profile.jpg"),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "Directory",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                IconButton(onClick = { navController.navigate(Route.NotificationsRoute) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.bell), // Replace with actual icon resource
                        contentDescription = "Notifications",
                        tint = Color.Black,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp)
            )

            // Employee List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(viewModel.employees.filter {
                    it.name.contains(searchQuery.text, ignoreCase = true)
                }) { employee ->
                    viewModel.selectedDirectoryEmployee = employee
                    EmployeeListItem(employee, {navController.navigate(Route.EmployeeDetailRoute)})
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun EmployeeListItem(employee: Employee, onEmployeeClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onEmployeeClick() }
    ) {
        Image(
            painter = rememberImagePainter(employee.profilePicURL),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = employee.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = employee.role,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmployeeDirectoryScreen() {
    val navController = rememberNavController() // Create a dummy NavHostController for preview
    EmployeeDirectoryScreen(navController)
}

