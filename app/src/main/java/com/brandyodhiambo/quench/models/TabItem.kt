package com.brandyodhiambo.quench.models

import com.brandyodhiambo.quench.R

import androidx.compose.runtime.Composable
import com.brandyodhiambo.quench.views.screens.home.HomeScreen
import com.brandyodhiambo.quench.views.screens.settings.SettingScreen
import com.brandyodhiambo.quench.views.screens.statistics.StatisticsScreen

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var icon: Int, var title: String, var screen: ComposableFun) {
    object Home : TabItem(R.drawable.ic_home, "Home", { HomeScreen() })
    object Statistic : TabItem(R.drawable.ic_statistics, "Statistic", { StatisticsScreen() })
    object Settings : TabItem(R.drawable.ic_settings, "Settings", { SettingScreen() })
}