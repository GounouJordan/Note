package com.jordy.note.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.jordy.note.ui.screen.home.HomeScreen
import com.jordy.note.ui.screen.home.HomeViewModel
import com.jordy.note.ui.screen.splash.SplashScreenScreen

@Composable
fun Nav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.startRoute
    ){
        composable(route = Routes.startRoute) {
            SplashScreenScreen {
                navController.navigate(Routes.MainRoutes.baseRoute)
            }
        }
        mainGraph(navController = navController)
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        route = Routes.MainRoutes.baseRoute,
        startDestination = Routes.MainRoutes.homeRoute
    ){
        composable(route = Routes.MainRoutes.homeRoute){
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(viewModel = viewModel)

        }
        composable(route = Routes.MainRoutes.editNoteRoute){

        }
    }
}