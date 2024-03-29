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
package com.brandyodhiambo.home.presentation.homeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.brandyodhiambo.common.R
import com.brandyodhiambo.common.domain.model.Days
import com.brandyodhiambo.common.domain.model.GoalWaterIntake
import com.brandyodhiambo.common.domain.model.IdealWaterIntake
import com.brandyodhiambo.common.domain.model.Level
import com.brandyodhiambo.common.domain.model.ReminderTime
import com.brandyodhiambo.common.domain.model.SelectedDrink
import com.brandyodhiambo.common.presentation.component.WaterIntakeDialog
import com.brandyodhiambo.designsystem.components.CircularButton
import com.brandyodhiambo.home.presentation.component.CircularRating
import com.brandyodhiambo.home.presentation.component.CongratulationsDialog
import com.brandyodhiambo.home.presentation.component.DeleteDialog
import com.brandyodhiambo.home.presentation.component.EmptyDialog
import com.brandyodhiambo.home.presentation.component.IdealIntakeGoalDialog
import com.brandyodhiambo.home.presentation.component.SelectDrinkComposable
import com.brandyodhiambo.home.presentation.component.TimeSetterDialog
import com.ramcosta.composedestinations.annotation.Destination

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val openTimeDialog = remember { mutableStateOf(false) }
    val openDeleteDialog = remember { mutableStateOf(false) }
    val openCongratulationsDialog = remember { mutableStateOf(false) }
    val openEmptyStateDialog = remember { mutableStateOf(false) }
    val openGoalDialog = remember { mutableStateOf(false) }
    val idealWaterIntakeDialog = remember { mutableStateOf(false) }
    val selectedDrinkDialog = remember { mutableStateOf(false) }

    val selectedDrinksFromDB = viewModel.selectedDrinkFromDB.observeAsState(initial = emptyList())
    val idealWaterIntake = viewModel.idealWaterIntakeFromDb.observeAsState()
    val waterIntake = idealWaterIntake.value?.waterIntake ?: 0
    val waterIntakeForm = idealWaterIntake.value?.form ?: "ml"
    val goalWaterIntakeFromDb = viewModel.goalWaterIntakeFromDb.observeAsState()
    val goalWaterIntake = goalWaterIntakeFromDb.value?.waterIntake ?: 0
    val goalForm = goalWaterIntakeFromDb.value?.form ?: "ml"
    val levelFromDb = viewModel.levelFromDB.observeAsState()

    val amountTaken = levelFromDb.value?.amountTaken ?: 0f
    val waterTaken = levelFromDb.value?.waterTaken ?: 0
    var selectedId = 0

    val reminderTimeFromDb = viewModel.reminderTime.observeAsState()
    val hour = reminderTimeFromDb.value?.hour
    val minute = reminderTimeFromDb.value?.minute

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn {
                item {
                    WaterIntake(
                        openGoalDialog = openGoalDialog,
                        idealWaterIntakeDialog = idealWaterIntakeDialog,
                        waterIntake = waterIntake,
                        form = waterIntakeForm,
                        goalForm = goalForm,
                        goalWaterIntake = goalWaterIntake
                    )
                }
                item {
                    WaterRecord(
                        openDialog = openTimeDialog,
                        selectedDrinkDialog = selectedDrinkDialog,
                        amountTaken = amountTaken,
                        time = if (hour != null && minute != null) "$hour:$minute" else "Add Time",
                        waterTaken = waterTaken,
                        goalWaterIntake = goalWaterIntake,
                        onAddLevelClick = {
                            if (selectedDrinksFromDB.value.isEmpty()) {
                                openEmptyStateDialog.value = true
                                return@WaterRecord
                            }
                            val (amount, taken) = incrementProgressCircle(
                                selectedDrinksFromDB = selectedDrinksFromDB,
                                goalWaterIntake = goalWaterIntake,
                                viewModel = viewModel
                            )
                            viewModel.deleteAllLevels()
                            viewModel.insertLevel(
                                Level(
                                    amountTaken = amount,
                                    waterTaken = taken
                                )
                            )
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(selectedDrinksFromDB.value) {
                    WaterIntakeTimeAndLevel(intake = it, onDeleteIconClick = { selectedDrink ->
                        selectedDrink.id?.let { id ->
                            selectedId = id
                            openDeleteDialog.value = true
                        }
                    })
                }
            }

            if (openEmptyStateDialog.value) {
                Dialog(onDismissRequest = { openEmptyStateDialog.value = false }) {
                    EmptyDialog(
                        onConfirmClick = {
                            openEmptyStateDialog.value = false
                            selectedDrinkDialog.value = true
                        },
                        onDismiss = { openEmptyStateDialog.value = false }
                    )
                }
            }

            if (openDeleteDialog.value) {
                Dialog(onDismissRequest = { openDeleteDialog.value = false }) {
                    DeleteDialog(
                        id = selectedId,
                        onDismiss = { openDeleteDialog.value = false },
                        onConfirmClick = { id ->
                            viewModel.deleteOneSelectedDrink(id)
                            openDeleteDialog.value = false
                        }
                    )
                }
            }
            if ((waterTaken >= goalWaterIntake) && (goalWaterIntake != 0)) {
                openCongratulationsDialog.value = true
            }

            if (openCongratulationsDialog.value) {
                Dialog(onDismissRequest = { openCongratulationsDialog.value }) {
                    CongratulationsDialog(
                        onCancelClicked = {
                            openCongratulationsDialog.value = false
                        },
                        onOkayClicked = {
                            openCongratulationsDialog.value = false
                        }
                    )
                }
            }

            if (openTimeDialog.value) {
                Dialog(onDismissRequest = { openTimeDialog.value }) {
                    TimeSetterDialog(
                        currentPickerValueText = viewModel.reminderTimePickerValue.value,
                        reminderDays = viewModel.reminderDays.value,
                        onDismiss = { openTimeDialog.value = false },
                        onCurrentPickerValueChanged = {
                            viewModel.reminderTimePickerValue.value = it
                        },
                        onAllDayClicked = {
                            viewModel.onAllDaySelected(
                                isAllDay = true
                            )
                            viewModel.reminderDays.value = listOf(
                                Days("M", viewModel.isAllDaySelected.value),
                                Days("T", viewModel.isAllDaySelected.value),
                                Days("W", viewModel.isAllDaySelected.value),
                                Days("T", viewModel.isAllDaySelected.value),
                                Days("F", viewModel.isAllDaySelected.value),
                                Days("S", viewModel.isAllDaySelected.value),
                                Days("S", viewModel.isAllDaySelected.value)
                            )
                        },
                        onConfirmClick = {
                            openTimeDialog.value = false
                            var ampm = ""
                            ampm = if (viewModel.reminderTimePickerValue.value.hours in 0..12) {
                                "AM"
                            } else {
                                "PM"
                            }
                            viewModel.onReminderTimeSelected(
                                hours = viewModel.reminderTimePickerValue.value.hours,
                                minutes = viewModel.reminderTimePickerValue.value.minutes,
                                amPm = ampm,
                                isReapeated = false,
                                isAllDay = viewModel.isAllDaySelected.value,
                                days = viewModel.reminderDays.value
                            )
                            if (viewModel.reminderTime.value != null) {
                                viewModel.deleteAllRemindTime()
                            }
                            viewModel.insertRemindTime(
                                ReminderTime(
                                    hour = viewModel.reminderSelectedTime.value.hour,
                                    minute = viewModel.reminderSelectedTime.value.minute,
                                    ampm = viewModel.reminderSelectedTime.value.ampm,
                                    isRepeated = false,
                                    isAllDay = viewModel.isAllDaySelected.value,
                                    days = viewModel.reminderDays.value
                                )
                            )
                        }
                    )
                }
            }

            if (openGoalDialog.value) {
                Dialog(onDismissRequest = { openGoalDialog.value }) {
                    WaterIntakeDialog(
                        openCustomDialog = openGoalDialog,
                        currentWaterIntakeText = viewModel.goalWaterIntakeValue.value,
                        currentWaterIntakeFormText = viewModel.goalWaterForm.value,
                        onCurrentWaterIntakeTextChange = {
                            viewModel.setGoalWaterIntakeValue(it)
                        },
                        onCurrentWaterIntakeFormTextChange = {
                            viewModel.setGoalWaterForm(it)
                        },
                        onOkayClick = {
                            val goalWaterIntakeToInsert = GoalWaterIntake(
                                waterIntake = viewModel.goalWaterIntakeValue.value.toInt(),
                                form = viewModel.goalWaterForm.value
                            )
                            viewModel.insertGoalWaterIntake(goalWaterIntakeToInsert)
                        }
                    )
                }
            }

            if (idealWaterIntakeDialog.value) {
                Dialog(onDismissRequest = { idealWaterIntakeDialog.value }) {
                    IdealIntakeGoalDialog(
                        idealCustomDialog = idealWaterIntakeDialog,
                        currentIdealIntakeText = viewModel.idealWaterIntakeValue.value,
                        currentIdealIntakeFormText = viewModel.idealWaterForm.value,
                        onCurrentIdealIntakeTextChange = {
                            viewModel.setIdealWaterIntakeValue(it)
                        },
                        onCurrentIdealIntakeFormTextChange = {
                            viewModel.setIdealWaterForm(it)
                        },
                        onOkayClick = {
                            val idealWaterIntakeToInsert = IdealWaterIntake(
                                waterIntake = viewModel.idealWaterIntakeValue.value.toInt(),
                                form = viewModel.idealWaterForm.value
                            )
                            viewModel.insertIdealWaterIntake(idealWaterIntakeToInsert)
                        }
                    )
                }
            }

            if (selectedDrinkDialog.value) {
                Dialog(onDismissRequest = { selectedDrinkDialog.value }) {
                    SelectDrinkComposable(
                        openDialog = selectedDrinkDialog,
                        onCurrentSelectedDrinkTime = {
                            viewModel.setSelectedTime(it)
                        },
                        onCurrentSelectedDrinkIcon = {
                            viewModel.setSelectedIcon(it)
                        },
                        onCurrentSelectedDrinkSize = {
                            viewModel.setSize(it)
                        },
                        onClick = {
                            viewModel.insertSelectedDrink(
                                SelectedDrink(
                                    drinkValue = viewModel.size.value,
                                    icon = viewModel.selectedIcon.value,
                                    time = viewModel.selectedTime.value
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

private fun incrementProgressCircle(
    selectedDrinksFromDB: State<List<SelectedDrink>>,
    goalWaterIntake: Int,
    viewModel: HomeViewModel
): Pair<Float, Int> {
    // getting the last selected drink
    var amountTaken = viewModel.levelFromDB.value?.amountTaken ?: 0f
    var waterTakenFromDb = viewModel.levelFromDB.value?.waterTaken ?: 0

    // if there is no selected drink, return
    if (selectedDrinksFromDB.value.isEmpty()) {
        return Pair(0f, 0)
    }

    // getting the last selected drink from the list of selected drinks from db
    val lastSelectedDrink = selectedDrinksFromDB.value.first()
    val waterTakenId = lastSelectedDrink.id
    val waterTaken = lastSelectedDrink.drinkValue.removeSuffix("ml").toInt()

    // if the water taken is 0 or the goal water intake is 0, return
    if (waterTaken == 0 || goalWaterIntake == 0) {
        return Pair(0f, 0)
    }

    // if the amount taken is 0, then calculate the amount taken
    if (amountTaken == 0f) {
        amountTaken = (waterTaken.toFloat() / goalWaterIntake.toFloat()) * 100
    } else {
        amountTaken += (waterTaken.toFloat() / goalWaterIntake.toFloat()) * 100
    }

    // if the water taken from db is 0, then calculate the water taken from db
    if (waterTakenFromDb == 0) {
        waterTakenFromDb = waterTaken
    } else {
        waterTakenFromDb += waterTaken
    }

    if (waterTakenId != null) {
        viewModel.deleteOneSelectedDrink(waterTakenId)
    }
    return Pair(amountTaken, waterTakenFromDb)
}

@Composable
fun WaterIntake(
    openGoalDialog: MutableState<Boolean>,
    idealWaterIntakeDialog: MutableState<Boolean>,
    waterIntake: Int,
    form: String,
    goalForm: String,
    goalWaterIntake: Int
) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_glass),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        idealWaterIntakeDialog.value = true
                    }
                ) {
                    Text(
                        text = "Ideal water intake",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "$waterIntake $form",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.labelMedium

                    )
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.ic_cup), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        openGoalDialog.value = true
                    }
                ) {
                    Text(
                        text = "Water intake goal",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "$goalWaterIntake $goalForm",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Composable
fun WaterRecord(
    openDialog: MutableState<Boolean>,
    amountTaken: Float,
    waterTaken: Int,
    time: String,
    goalWaterIntake: Int,
    selectedDrinkDialog: MutableState<Boolean>,
    onAddLevelClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(start = 16.dp, end = 16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            CircularRating(
                percentage = amountTaken,
                drunk = waterTaken,
                goal = goalWaterIntake
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CircularButton(
                    backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.67f),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    icon = R.drawable.ic_clock,
                    title = time,
                    onClick = {
                        openDialog.value = true
                    }
                )
                CircularButton(
                    backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.67f),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    icon = R.drawable.ic_add,
                    title = "Add Level",
                    onClick = {
                        onAddLevelClick()
                    }
                )
                CircularButton(
                    backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.67f),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    icon = R.drawable.ic_glass,
                    title = "Add Drink",
                    onClick = {
                        selectedDrinkDialog.value = true
                    }
                )
            }
        }
    }
}

@Composable
fun WaterIntakeTimeAndLevel(
    intake: SelectedDrink,
    onDeleteIconClick: (SelectedDrink) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium)
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_glass),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
                Text(
                    text = intake.drinkValue,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = intake.time,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.labelMedium
                )
                IconButton(onClick = { onDeleteIconClick(intake) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = Color.Gray,
                        contentDescription = null
                    )
                }
            }
        }
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
        )
    }
}
