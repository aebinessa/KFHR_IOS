package com.binjesus.kfhr_mobile.composables

import ApplyForLeaveScreen
import HomeScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.binjesus.kfhr_mobile.models.Attendance
import com.binjesus.kfhr_mobile.models.Employee
import com.binjesus.kfhr_mobile.models.LateMinutesLeft
import com.binjesus.kfhr_mobile.models.RecommendedCertificate
import java.util.Calendar
import java.util.Date

@Composable
fun ScreensNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "HomeScreen"
) {
    Scaffold(
        modifier = modifier,
        //if you want to add the bell notification for all pages add it here

        bottomBar = {
            BottomNavBar(navController)
        }
    ) {
        NavHost(
            modifier = modifier.padding(it),
            navController = navController,
            startDestination = startDestination
        ) {
            composable("WelcomeScreen") {
                LoginScreen(navController)
            }
            composable("NfcId"){
                val employee = Employee(
                    id = 8332246,
                    name = "Abdullah Essa Bin Essa",
                    role = "Group Human Resources",
                    email = "abdullah@example.com",
                    phone = "123456789",
                    dob = Date(),
                    gender = "Male",
                    profilePicURL = "https://example.com/profile.jpg",
                    nfcIdNumber = 123,
                    positionId = 1,
                    departmentId = 1,
                    pointsEarned = 100
                )

                UserProfileScreen(navController = navController, employee = employee)
            }
            composable(
                "EmployeeDetail/{employeeId}",
                arguments = listOf(navArgument("employeeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val employeeId = backStackEntry.arguments?.getInt("employeeId")
                // Assuming you have a list of employees somewhere in your scope
                val employees = listOf(
                    Employee(
                        1,
                        "Feras Alshadad",
                        "Role",
                        "email@example.com",
                        "123456789",
                        Date(),
                        "Male",
                        "https://example.com/profile1.jpg",
                        123,
                        1,
                        1,
                        100
                    ),
                    Employee(
                        2,
                        "Abdullah Bin Essa",
                        "Role",
                        "email@example.com",
                        "123456789",
                        Date(),
                        "Male",
                        "https://example.com/profile2.jpg",
                        124,
                        2,
                        2,
                        100
                    ),
                    Employee(
                        3,
                        "Othman Alkous",
                        "Role",
                        "email@example.com",
                        "123456789",
                        Date(),
                        "Male",
                        "https://example.com/profile3.jpg",
                        125,
                        3,
                        3,
                        100
                    )
                    // Add more employees as needed
                )
                val employee = employees.find { it.id == employeeId }
                employee?.let {
                    EmployeeDetailScreen(navController, it)
                }
            }

            composable("ContactIT") {
                ITContactScreen(navController)
            }
            composable("MyCertificates"){
                CertificatesApp(navController)
            }
            composable("SubmitCertificate"){
                CertificateSubmissionScreen(navController,onSubmit = { certificate ->
                    // Handle the certificate application submission
                })
            }
            composable("Notifications"){
                NotificationScreen(navController)

            }
            composable("SignIn") {
                SignInScreen(navController)
            }
            composable("HomeScreen") {
                // Pass dummy data for the preview
                val employee = Employee(
                    id = 1,
                    name = "Abdullah Bin Essa",
                    role = "Forssah Tech Trainee",
                    email = "abdullah@example.com",
                    phone = "123456789",
                    dob = Date(),
                    gender = "Male",
                    profilePicURL = "https://example.com/profile.jpg",
                    nfcIdNumber = 123,
                    positionId = 1,
                    departmentId = 1,
                    pointsEarned = 100
                )
                val attendance = Attendance(
                    id = 1,
                    employeeId = 1,
                    checkInDateTime = Date(),
                    checkOutDateTime = Date(Date().time + 3600000) // 1 hour later
                )
                val lateMinutesLeft = LateMinutesLeft(
                    id = 1,
                    employeeId = 1,
                    time = 27,
                    month = Date()
                )
                HomeScreen(navController, employee, attendance, lateMinutesLeft)
            }
            composable("AttendanceScreen") {
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
            composable("DirectoryScreen") {
                val employees = listOf(
                    Employee(
                        1,
                        "Feras Alshadad",
                        "Role",
                        "email@example.com",
                        "123456789",
                        Date(),
                        "Male",
                        "https://example.com/profile1.jpg",
                        123,
                        1,
                        1,
                        100
                    ),
                    Employee(
                        2,
                        "Abdullah Bin Essa",
                        "Role",
                        "email@example.com",
                        "123456789",
                        Date(),
                        "Male",
                        "https://example.com/profile2.jpg",
                        124,
                        2,
                        2,
                        100
                    ),
                    Employee(
                        3,
                        "Othman Alkous",
                        "Role",
                        "email@example.com",
                        "123456789",
                        Date(),
                        "Male",
                        "https://example.com/profile3.jpg",
                        125,
                        3,
                        3,
                        100
                    ),
                    // Add more employees as needed
                )
                EmployeeDirectoryScreen(navController, employees) { employee ->
                    navController.navigate("EmployeeDetail/${employee.id}")
                }
            }

            composable("LeavesScreen"){
                ApplyForLeaveScreen(navController, onSubmit = { leave ->
                    // Handle the leave application submission
                })

            }
            composable("CertsScreen"){
                val certificates = listOf(
                    RecommendedCertificate(1, "CCNA", "Cisco", 100, "https://www.cisco.com", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5JJdkCaqPHmjegj3ggaiHufw4SH5wYZoT3kDcDNDAdw&s"),
                    RecommendedCertificate(2, "CCNP", "Cisco", 200, "https://www.cisco.com", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5JJdkCaqPHmjegj3ggaiHufw4SH5wYZoT3kDcDNDAdw&s"),
                    RecommendedCertificate(3, "AWS Certified Solutions Architect", "Amazon", 150, "https://www.aws.training", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5JJdkCaqPHmjegj3ggaiHufw4SH5wYZoT3kDcDNDAdw&s"),
                    RecommendedCertificate(4, "Google Cloud Professional Data Engineer", "Google", 150, "https://cloud.google.com/certification", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5JJdkCaqPHmjegj3ggaiHufw4SH5wYZoT3kDcDNDAdw&s")
                )

                RecommendedCertificatesScreen(navController, certificates, onCertificateClick = { certificate ->
                    // Handle certificate click (navigate to detail screen)
                }, onViewAllClick = {
                    // Handle view all click (navigate to view all certificates screen)
                })
            }

        }
    }
}
