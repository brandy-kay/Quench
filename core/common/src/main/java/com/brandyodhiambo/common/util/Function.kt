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
package com.brandyodhiambo.common.util

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.*

fun String.toInitials(): String {
    return this
        .split(' ')
        .mapNotNull { it.firstOrNull()?.toString() }
        .reduce { acc, s -> acc + s }
}

fun getCurrentDay(): String {
    val now = LocalDateTime.now()
    val currentDayOfWeek = now.dayOfWeek
    return currentDayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

fun getCurrentMonth(): String {
    val now = LocalDateTime.now()
    val currentMonth = now.month
    return currentMonth.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

fun getCurrentWeekNumber(): Int {
    val now = LocalDate.now()
    val weekFields = WeekFields.of(Locale.getDefault())
    return now.get(weekFields.weekOfWeekBasedYear())
}

suspend fun <T> LiveData<T>.awaitValue(): T = withContext(Dispatchers.Default) {
    val flow = MutableSharedFlow<T>(replay = 1)
    val observer = androidx.lifecycle.Observer<T> {
        it?.let { value -> flow.tryEmit(value) }
    }

    withContext(Dispatchers.Main) {
        observeForever(observer)
    }
    try {
        flow.first {
            withContext(Dispatchers.Main) {
                removeObserver(observer)
            }
            true
        }
    } finally {
        withContext(Dispatchers.Main) {
            removeObserver(observer)
        }
    }
}
