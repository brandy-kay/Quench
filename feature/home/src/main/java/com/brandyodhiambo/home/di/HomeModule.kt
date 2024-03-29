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
package com.brandyodhiambo.home.di

import com.brandyodhiambo.common.domain.repository.AchievementRepository
import com.brandyodhiambo.common.domain.repository.GoalWaterIntakeRepository
import com.brandyodhiambo.common.domain.repository.IdealWaterIntakeRepository
import com.brandyodhiambo.common.domain.repository.LevelRepository
import com.brandyodhiambo.common.domain.repository.ReminderTimeRepository
import com.brandyodhiambo.common.domain.repository.SelectedDrinkRepository
import com.brandyodhiambo.common.domain.repository.SleepTimeRepository
import com.brandyodhiambo.common.domain.repository.WakeTimeRepository
import com.brandyodhiambo.dao.AchievementDao
import com.brandyodhiambo.dao.GoalWaterIntakeDao
import com.brandyodhiambo.dao.IdealWaterIntakeDao
import com.brandyodhiambo.dao.LevelDao
import com.brandyodhiambo.dao.ReminderTimeDao
import com.brandyodhiambo.dao.SelectedDrinkDao
import com.brandyodhiambo.dao.SleepTimeDao
import com.brandyodhiambo.dao.WakeTimeDao
import com.brandyodhiambo.home.data.repository.AchievementRepositoryImpl
import com.brandyodhiambo.home.data.repository.GoalWaterIntakeRepositoryImpl
import com.brandyodhiambo.home.data.repository.IdealWaterInkateRepositoryImpl
import com.brandyodhiambo.home.data.repository.LevelRepositoryImpl
import com.brandyodhiambo.home.data.repository.ReminderTimeRepositoryImpl
import com.brandyodhiambo.home.data.repository.SelectedDrinkRepositoryImpl
import com.brandyodhiambo.home.data.repository.SleepTimeRepositoryImpl
import com.brandyodhiambo.home.data.repository.WakeTimeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideSleepRepository(sleepTimeDao: SleepTimeDao): SleepTimeRepository {
        return SleepTimeRepositoryImpl(sleepTimeDao = sleepTimeDao)
    }

    @Provides
    @Singleton
    fun provideWakeTimeRepository(wakeTimeDao: WakeTimeDao): WakeTimeRepository {
        return WakeTimeRepositoryImpl(wakeTimeDao = wakeTimeDao)
    }

    @Provides
    @Singleton
    fun provideIdealWaterIntakeRepository(idealWaterIntakeDao: IdealWaterIntakeDao): IdealWaterIntakeRepository {
        return IdealWaterInkateRepositoryImpl(idealWaterIntakeDao = idealWaterIntakeDao)
    }

    @Provides
    @Singleton
    fun provideGoalWaterIntakeRepository(goalWaterIntakeDao: GoalWaterIntakeDao): GoalWaterIntakeRepository {
        return GoalWaterIntakeRepositoryImpl(goalWaterIntakeDao = goalWaterIntakeDao)
    }

    @Provides
    @Singleton
    fun provideSelectedDrinkRepository(selectedDrinkDao: SelectedDrinkDao): SelectedDrinkRepository {
        return SelectedDrinkRepositoryImpl(selectedDrinkDao = selectedDrinkDao)
    }

    @Provides
    @Singleton
    fun provideLevelRepository(levelDao: LevelDao): LevelRepository {
        return LevelRepositoryImpl(levelDao = levelDao)
    }

    @Provides
    @Singleton
    fun provideReminderTimeRepository(reminderTimeDao: ReminderTimeDao): ReminderTimeRepository {
        return ReminderTimeRepositoryImpl(reminderTimeDao = reminderTimeDao)
    }

    @Provides
    @Singleton
    fun provideAchievementRepository(achievementDao: AchievementDao): AchievementRepository {
        return AchievementRepositoryImpl(achievementDao = achievementDao)
    }
}
