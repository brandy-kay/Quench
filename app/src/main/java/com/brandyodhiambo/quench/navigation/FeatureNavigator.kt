/*
 * Copyright (C)2023 Brandy Odhiambo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.brandyodhiambo.quench.navigation

import androidx.navigation.NavController
import com.brandyodhiambo.home.presentation.destinations.SleepAndWakeTimeScreenDestination
import com.brandyodhiambo.home.presentation.sleepWakeScreen.SleepAndWakeUpScreenScreenNavigator
import com.brandyodhiambo.quench.ui.splash.SplashScreenNavigator
import com.brandyodhiambo.settings.presentation.AddReminderNavigator
import com.brandyodhiambo.settings.presentation.NotificationNavigator
import com.brandyodhiambo.settings.presentation.SettingsNavigator
import com.brandyodhiambo.settings.presentation.destinations.AddReminderScreenDestination
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.NavGraphSpec

class FeatureNavigator(
    private val navController: NavController,
    private val navGraph: NavGraphSpec
) : SplashScreenNavigator,
    SleepAndWakeUpScreenScreenNavigator,
    SettingsNavigator,
    NotificationNavigator,
    AddReminderNavigator {
    override fun navigateToNotificationScreen() {
        navController.navigate(
            NavGraphs.settings.route
        )
    }

    override fun navigateToReminderScreen() {
        navController.navigate(AddReminderScreenDestination within navGraph)
    }

    override fun navigateToSleepWakeTimeScreen() {
        navController.navigate(SleepAndWakeTimeScreenDestination within navGraph)
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun navigateToMainScreen() {
        navController.navigate(
            NavGraphs.home.route
        ) {
            popUpTo(NavGraphs.splash.route) {
                inclusive = true
            }
        }
    }

    override fun navigateUp() {
        navController.navigateUp()
    }
}
