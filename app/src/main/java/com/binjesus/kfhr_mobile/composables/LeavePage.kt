package com.example.leavesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binjesus.kfhr_mobile.R

data class LeaveApplication(
    val date: String,
    val type: String,
    val status: String
)

data class BottomNavItem(
    val label: String,
    val iconRes: Int,
    val selected: Boolean
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                LeaveApplicationsScreen()

        }
    }
}

@Composable
fun LeaveApplicationsScreen() {
    val leaveApplications = listOf(
        LeaveApplication("Wed, 16 Dec", "Casual", "Awaiting"),
        LeaveApplication("Mon, 16 Nov", "Sick", "Approved"),
        LeaveApplication("Mon, 22 Nov - Fri, 25 Nov", "Casual", "Declined"),
        LeaveApplication("Thu, 01 Nov", "Sick", "Approved")
    )

    var selectedFilter by remember { mutableStateOf("All") }
    val filterOptions = listOf("All", "Casual", "Sick", "Awaiting", "Approved", "Declined")
    val items = listOf(
        BottomNavItem("HOME", R.drawable.home, false),
        BottomNavItem("ATTENDANCE", R.drawable.security, false),
        BottomNavItem("LEAVES", R.drawable.document, true),
        BottomNavItem("CERTS", R.drawable.onlinecertificate, false),
        BottomNavItem("DIRECTORY", R.drawable.agenda, false)
    )

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color(0xFF4CAF50)
            ) {
                items.forEach { item ->
                    BottomNavigationItem(
                        selected = item.selected,
                        onClick = { /* Handle navigation click */ },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.label,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                fontSize = 8.sp,
                                fontWeight = if (item.selected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        modifier = Modifier.width(70.dp)
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
                Text(
                    text = "Leaves",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
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
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add",
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LeaveApplicationsList(
                    leaveApplications = leaveApplications.filter {
                        selectedFilter == "All" || it.type == selectedFilter || it.status == selectedFilter
                    }
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
fun LeaveApplicationsList(leaveApplications: List<LeaveApplication>) {
    LazyColumn {
        items(leaveApplications.size) { index ->
            LeaveApplicationItem(leaveApplications[index])
        }
    }
}

@Composable
fun LeaveApplicationItem(application: LeaveApplication) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(application.date, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(application.type, color = Color(16, 89, 179))
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(application.status, color = when (application.status) {
                    "Approved" -> Color.Green
                    "Declined" -> Color.Red
                    "Awaiting" -> Color.Gray
                    else -> Color.Black
                })
                Icon(Icons.Filled.ArrowForward, contentDescription = "Details")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CertificatesScreenPreview() {

        LeaveApplicationsScreen()

}
