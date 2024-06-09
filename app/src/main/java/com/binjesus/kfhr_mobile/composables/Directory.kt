package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.binjesus.kfhr_mobile.models.Employee
import com.binjesus.kfhr_mobile.ui.theme.DarkGreen
import com.binjesus.kfhr_mobile.ui.theme.LightGreen
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel

@Composable
fun EmployeeDirectoryScreen(navController: NavController, viewModel: KFHRViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            viewModel.errorMessage.value.isNotEmpty() -> {
                Text(text = viewModel.errorMessage.value, color = Color.Red)
            }

            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(viewModel.employees.value.filter {
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
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        backgroundColor = DarkGreen
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(employee.profilePicURL),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = employee.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = employee.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = LightGreen
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Position :",
                            style = MaterialTheme.typography.bodySmall,
                            color = LightGreen
                        )
                        var positionName = ""
                        if (employee.positionName != null) {
                            positionName = employee.positionName
                        }
                        Text(
                            text = positionName,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Department:",
                            style = MaterialTheme.typography.bodySmall,
                            color = LightGreen
                        )
                        var departmentName = ""
                        if (employee.departmentName != null) {
                            departmentName = employee.departmentName.departmentName
                        }

                        Text(
                            text = departmentName,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
