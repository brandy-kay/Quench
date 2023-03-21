package com.brandyodhiambo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brandyodhiambo.dao.GoalWaterIntakeDao
import com.brandyodhiambo.dao.IdealWaterIntakeDao
import com.brandyodhiambo.dao.SelectedDrinkDao
import com.brandyodhiambo.dao.WakeTimeDao
import com.brandyodhiambo.entity.GoalWaterIntakeEntity
import com.brandyodhiambo.entity.IdealWaterIntakeEntity
import com.brandyodhiambo.entity.SelectedDrinkEntity
import com.brandyodhiambo.entity.WakeTimeEntity

@Database(
    entities = [SelectedDrinkEntity::class, WakeTimeEntity::class, IdealWaterIntakeEntity::class, GoalWaterIntakeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class QuenchDatabase : RoomDatabase() {
    abstract fun wakeTimeDao(): WakeTimeDao
    abstract fun selectedDrinkDao(): SelectedDrinkDao
    abstract fun idealWaterIntakeDao(): IdealWaterIntakeDao
    abstract fun goalWaterIntakeDao(): GoalWaterIntakeDao
}