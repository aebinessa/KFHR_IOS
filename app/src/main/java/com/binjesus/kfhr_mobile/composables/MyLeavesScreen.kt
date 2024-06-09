package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.models.Leave
import com.binjesus.kfhr_mobile.utils.Route
import com.binjesus.kfhr_mobile.utils.convertDateToBasicDateStringFormat
import com.binjesus.kfhr_mobile.utils.convertDateToDayOfWeek
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel

@Composable
fun MyLeavesScreen(
    navController: NavHostController,
    viewModel: KFHRViewModel
) {

    var selectedFilter by remember { mutableStateOf("All") }
    val filterOptions = listOf("All", "Casual", "Sick", "Awaiting", "Approved", "Declined")

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Leaves",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Route.ApplyLeavesRoute) },
                backgroundColor = Color(0xFF4CAF50),
                modifier = Modifier
                    .padding(
                        bottom = 24.dp,
                        end = 16.dp
                    )
                    .size(75.dp)
                    .shadow(elevation = 20.dp, shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Add",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilterDropdown(
                        filterOptions = filterOptions,
                        selectedFilter = selectedFilter,
                        onFilterSelected = { selectedFilter = it }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LeaveApplicationsList(viewModel.leaves)
            }
        }
    )
}

@Composable
fun FilterDropdown(
    filterOptions: List<String>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(
            onClick = { expanded = true },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(76, 175, 80))
        ) {
            Text(selectedFilter)
            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            filterOptions.forEach { filter ->
                DropdownMenuItem(onClick = {
                    onFilterSelected(filter)
                    expanded = false
                }) {
                    Text(text = filter)
                }
            }
        }
    }
}

@Composable
fun LeaveApplicationsList(leaveApplications: List<Leave>) {
    LazyColumn {
        items(leaveApplications) {
            LeaveApplicationItem(it)
        }
    }
}

@Composable
fun LeaveApplicationItem(leave: Leave) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = convertDateToBasicDateStringFormat(leave.startDate),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = convertDateToDayOfWeek(leave.startDate), fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = leave.leaveType, color = Color(16, 89, 179))
            Spacer(modifier = Modifier.height(4.dp))
            if (leave.status != null)
                Text(
                    text = leave.status, color = when (leave.status) {
                        "Approved" -> Color.Green
                        "Declined" -> Color.Red
                        "Awaiting" -> Color.Gray
                        else -> Color.Black
                    }
                )
        }
    }
}