package com.binjesus.kfhr_mobile.composables

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
        if (viewModel.token?.token != null) {
            navController.navigate(Route.MainAppRoute)
        }

        composable(Route.WelcomeRoute) {
            WelcomeScreen(navController = navController)
        }
        composable(Route.SignInRoute) {
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