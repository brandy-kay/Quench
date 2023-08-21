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
package com.brandyodhiambo.statistics.presentation

import androidx.lifecycle.ViewModel
import com.brandyodhiambo.common.domain.repository.DailyStatisticsRepository
import com.brandyodhiambo.common.domain.repository.MonthlyStatisticsRepository
import com.brandyodhiambo.common.domain.repository.WeeklyStatisticRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val dailyStatisticsRepository: DailyStatisticsRepository,
    private val weeklyStatisticsRepository: WeeklyStatisticRepository,
    private val monthlyStatisticsRepository: MonthlyStatisticsRepository
) : ViewModel() {

    val dailyStatisticsFromDB = dailyStatisticsRepository.getDailyStatistics()
    val weeklyStatisticsFromDB = weeklyStatisticsRepository.getWeeklyStatistic()
    val monthlyStatisticsFromDB = monthlyStatisticsRepository.getMonthlyStatistics()
}