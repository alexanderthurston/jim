package com.example.jim.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jim.ui.models.Exercise
import com.example.jim.ui.models.Set
import com.example.jim.ui.viewmodels.WorkoutSessionViewModel

@Composable
fun ExerciseOverviewListItem(
    exercise: Exercise,
    sets: List<Set>? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = exercise.name ?: "", style = MaterialTheme.typography.subtitle2)
        }

        if (!sets.isNullOrEmpty()) {
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                sets.forEach { set ->
                    Text(text = "${set.weight} lbs x ${set.reps} reps")
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}