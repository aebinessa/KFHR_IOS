package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.binjesus.kfhr_mobile.models.Employee
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel

@Composable
fun EmployeeDirectoryScreen(navController: NavController, viewModel: KFHRViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val employees by viewModel.employees
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Employee Directory",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            errorMessage.isNotEmpty() -> {
                Text(text = errorMessage, color = Color.Red)
            }
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(employees.filter {
                        it.name.contains(searchQuery.text, ignoreCase = true)
                    }) { employee ->
                        EmployeeListItem(employee) {
                            viewModel.selectedDirectoryEmployee = employee
                            navController.navigate("EmployeeDetailRoute")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun EmployeeListItem(employee: Employee, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Text(text = "Name: ${employee.name}", fontWeight = FontWeight.Bold)
        Text(text = "Email: ${employee.email}")
        Text(text = "Position ID: ${employee.positionId ?: "N/A"}")
        Text(text = "Department ID: ${employee.departmentId ?: "N/A"}")
    }
}
