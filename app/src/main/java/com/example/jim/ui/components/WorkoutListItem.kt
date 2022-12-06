package com.example.jim.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jim.ui.models.Exercise
import com.example.jim.ui.models.Workout
import com.example.jim.ui.models.Set


@Composable
fun WorkoutListItem(
    workout: Workout,
    exercises: List<Exercise>,
    sets: List<Set>,
) {
    var showDetail by remember { mutableStateOf(false) }
    Surface(
        elevation = 2.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.clickable {
            showDetail = !showDetail
        },

    ) {
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "Date: ${workout.date ?: ""}",
                        style = MaterialTheme.typography.subtitle1
                    )

                    Text(
                        text = "Duration: ${workout.duration ?: ""} min",
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = showDetail,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {

                Column() {
                    Divider(color = Color.LightGray, thickness = 2.dp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "Exercise",
                                style = TextStyle(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    letterSpacing = 0.1.sp,
                                    textDecoration = TextDecoration.Underline
                                ),
                            )

                            Text(
                                text = "Sets",
                                style = TextStyle(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    letterSpacing = 0.1.sp,
                                    textDecoration = TextDecoration.Underline
                                ),
                            )
                        }
                    }
                    exercises.forEach { exercise ->
                        Row() {
                            ExerciseOverviewListItem(
                                exercise = exercise,
                                sets = sets.filter { set -> set.exerciseId == exercise.id },
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                /* TODO: Put Sets of workout here! */
            }
        }
    }
}