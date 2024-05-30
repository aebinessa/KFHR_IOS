package com.binjesus.kfhr_mobile.composables

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.binjesus.kfhr_mobile.utils.Route
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel

@Composable
fun AuthNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.WelcomeRoute,
    viewModel: KFHRViewModel = viewModel()// <-- Added ViewModel parameter here

) {
    NavHost(navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Route.WelcomeRoute) {
            if (viewModel.token != null) {
                navController.navigate(Route.MainAppRoute)
            }
            WelcomeScreen(navController = navController)
        }
        composable(Route.SignInRoute) {
            if (viewModel.token != null) {
                navController.navigate(Route.MainAppRoute)
            }
            SignInScreen(navController = navController, viewModel = viewModel)
        }
        composable(Route.MainAppRoute) {
            ScreensNavHost(viewModel=viewModel)
        }
        composable(Route.ContactItRoute) {
            ContactItScreen(navController)
        }
    }
}