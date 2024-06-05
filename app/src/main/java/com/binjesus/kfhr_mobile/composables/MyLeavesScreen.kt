package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.binjesus.kfhr_mobile.models.Leave
import com.binjesus.kfhr_mobile.utils.Route
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
                IconButton(onClick = { navController.navigate(Route.ApplyLeavesRoute) }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
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
                LeaveApplicationsList(viewModel.leaves
                )
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
        Button(onClick = { expanded = true }, shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(76, 175, 80))) {
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
            Text(leave.startDate.toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(leave.leaveTypes.toString(), color = Color(16, 89, 179))
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(leave.status!!, color = when (leave.status) {
                    "Approved" -> Color.Green
                    "Declined" -> Color.Red
                    "Awaiting" -> Color.Gray
                    else -> Color.Black
                })
//                Icon(Icons.Filled.ArrowForward, contentDescription = "Details")
            }
        }
    }
}