package com.binjesus.kfhr_mobile.composables


import HomeScreen
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.binjesus.kfhr_mobile.ui.ApplyForLeaveScreen
import com.binjesus.kfhr_mobile.utils.Route
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel

@Composable
fun ScreensNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.HomeRoute,
    viewModel: KFHRViewModel = viewModel()// <-- Added ViewModel parameter here

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
            composable(Route.NFCRoute) {
                NFCIdScreen(navController, viewModel)
            }

            composable(Route.EmployeeDetailRoute) {
                EmployeeDetailScreen(navController, viewModel.selectedDirectoryEmployee!!)
            }

            composable(Route.MyCertificatesRoute) {
                viewModel.getMyCertificates()
                MyCertificates(navController, viewModel)
            }

            composable(Route.SubmitCertificateRoute) {
                CertificateSubmissionScreen(navController, viewModel)
            }

            composable(Route.RecommendedCertificatesRoute) {
                viewModel.getRecommendedCertificates()
                val context = LocalContext.current
                RecommendedCertificatesScreen(
                    navController, viewModel
                ) { certificate ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(certificate.organizationWebsite))
                    context.startActivity(intent)
                }
            }

            composable(Route.NotificationsRoute) {
                NotificationScreen(viewModel)
            }

            composable(Route.HomeRoute) {
                viewModel.fetchLateMinutesLeft()
                viewModel.getTodayAttendance()
                HomeScreen(navController, viewModel)
            }

            composable(Route.AttendanceRoute) {
                viewModel.getAttendances()
                AttendanceList(navController, viewModel)
            }
            composable(Route.DirectoryRoute) {
                viewModel.getEmployees()
                EmployeeDirectoryScreen(navController, viewModel)
            }

            composable(Route.ApplyLeavesRoute) {
                ApplyForLeaveScreen(navController, viewModel)
            }
            composable(Route.MyLeavesRoute) {
                viewModel.fetchLeave()
                MyLeavesScreen(navController, viewModel)
            }

        }
    }
}
