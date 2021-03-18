/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fidloo.flux.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.fidloo.flux.presentation.ui.home.HomeScreen
import com.fidloo.flux.presentation.ui.home.HomeViewModel
import com.fidloo.flux.presentation.ui.welcome.Welcome

object MainDestinations {
    const val WELCOME_ROUTE = "welcome"
    const val HOME_ROUTE = "home"
}

@Composable
fun NavGraph(startDestination: String = MainDestinations.HOME_ROUTE) {
    val navController = rememberNavController()

    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.WELCOME_ROUTE) {
            Welcome(onGetStartedClicked = actions.welcomeComplete)
        }
        composable(MainDestinations.HOME_ROUTE) { backStackEntry ->
            val viewModel = hiltNavGraphViewModel<HomeViewModel>(backStackEntry = backStackEntry)
            HomeScreen(viewModel)
        }
    }
}

class MainActions(navController: NavHostController) {
    val welcomeComplete: () -> Unit = {
        navController.navigate(MainDestinations.HOME_ROUTE)
    }
}