package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
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
import com.binjesus.kfhr_mobile.models.Attendance
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AttendanceRecord(attendance: Attendance, modifier: Modifier = Modifier) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = dateFormat.format(attendance.checkInDateTime), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = dayOfWeekFormat.format(attendance.checkInDateTime), fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Check-in: ${timeFormat.format(attendance.checkInDateTime)}",
                fontSize = 18.sp
            )
            Text(
                text = "Check-out: ${attendance.checkOutDateTime?.let { timeFormat.format(it) } ?: "N/A"}",
                fontSize = 18.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AttendanceList(navController: NavHostController, attendances: List<Attendance>, employeeId: Int) {
    var selectedMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var selectedYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    var selectedDate: Int? by remember { mutableStateOf(null) }
    var monthExpanded by remember { mutableStateOf(false) }
    var yearExpanded by remember { mutableStateOf(false) }
    var dateExpanded by remember { mutableStateOf(false) }

    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    val years = (2020..2024).toList()

    val dates = remember(selectedYear, selectedMonth) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, selectedYear)
            set(Calendar.MONTH, selectedMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val maxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        (1..maxDate).toList()
    }

    val filteredAttendances by remember(selectedMonth, selectedYear, selectedDate) {
        derivedStateOf {
            attendances.filter { attendance ->
                val calAttendance = Calendar.getInstance().apply { time = attendance.checkInDateTime }
                calAttendance.get(Calendar.YEAR) == selectedYear &&
                        calAttendance.get(Calendar.MONTH) == selectedMonth &&
                        (selectedDate == null || calAttendance.get(Calendar.DAY_OF_MONTH) == selectedDate) &&
                        attendance.employeeId == employeeId
            }
        }
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Attendance Records",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { navController.navigate("Notifications") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.bell), // Replace with actual icon resource
                        contentDescription = "Notifications",
                        tint = Color.Black,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExposedDropdownMenuBox(
                    expanded = yearExpanded,
                    onExpandedChange = { yearExpanded = !yearExpanded },
                    modifier = Modifier.wrapContentSize()
                ) {
                    OutlinedTextField(
                        value = selectedYear.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Year") },
                        trailingIcon = {
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                        },
                        modifier = Modifier
                            .width(110.dp)
                            .clickable { yearExpanded = true }
                    )
                    ExposedDropdownMenu(
                        expanded = yearExpanded,
                        onDismissRequest = { yearExpanded = false }
                    ) {
                        years.forEach { year ->
                            DropdownMenuItem(onClick = {
                                selectedYear = year
                                yearExpanded = false
                            }) {
                                Text(text = year.toString())
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                ExposedDropdownMenuBox(
                    expanded = monthExpanded,
                    onExpandedChange = { monthExpanded = !monthExpanded }
                ) {
                    OutlinedTextField(
                        value = months[selectedMonth],
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Month") },
                        trailingIcon = {
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                        },
                        modifier = Modifier
                            .width(110.dp)
                            .clickable { monthExpanded = true }
                    )
                    ExposedDropdownMenu(
                        expanded = monthExpanded,
                        onDismissRequest = { monthExpanded = false }
                    ) {
                        months.forEachIndexed { index, month ->
                            DropdownMenuItem(onClick = {
                                selectedMonth = index
                                monthExpanded = false
                            }) {
                                Text(text = month)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                ExposedDropdownMenuBox(
                    expanded = dateExpanded,
                    onExpandedChange = { dateExpanded = !dateExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedDate?.toString() ?: "All Dates",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Date") },
                        trailingIcon = {
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                        },
                        modifier = Modifier
                            .width(110.dp)
                            .clickable { dateExpanded = true }
                    )
                    ExposedDropdownMenu(
                        expanded = dateExpanded,
                        onDismissRequest = { dateExpanded = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            selectedDate = null
                            dateExpanded = false
                        }) {
                            Text(text = "All Dates")
                        }
                        dates.forEach { date ->
                            DropdownMenuItem(onClick = {
                                selectedDate = date
                                dateExpanded = false
                            }) {
                                Text(text = date.toString())
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            if (filteredAttendances.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally // Center the list
                ) {
                    items(filteredAttendances) { attendance ->
                        AttendanceRecord(attendance = attendance)
                    }
                }
            } else {
                Text("No attendance records found", style = MaterialTheme.typography.h6)
            }
        }
}


@Preview(showBackground = true)
@Composable
fun PreviewAttendanceList() {
    val navController = rememberNavController()
    val sampleAttendances = listOf(
        Attendance(
            id = 1,
            employeeId = 101,
            checkInDateTime = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 1, 8, 30)
            }.time,
            checkOutDateTime = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 1, 17, 0)
            }.time
        ),
        Attendance(
            id = 2,
            employeeId = 101,
            checkInDateTime = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 2, 8, 45)
            }.time,
            checkOutDateTime = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 2, 17, 15)
            }.time
        ),
        Attendance(
            id = 3,
            employeeId = 101,
            checkInDateTime = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 3, 9, 0)
            }.time,
            checkOutDateTime = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 3, 18, 0)
            }.time
        )
    )

    AttendanceList(navController, attendances = sampleAttendances, employeeId = 101)
}
