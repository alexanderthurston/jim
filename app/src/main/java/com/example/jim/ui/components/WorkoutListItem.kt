package com.example.jim.ui.components


import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.jim.ui.models.Workout


@Composable
fun WorkoutListItem(
    workout: Workout
) {
    var showDetail by remember { mutableStateOf(false) }
    Surface(
        elevation = 2.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.clickable {
            showDetail = !showDetail
        },
    ) {
        Column (){
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = workout.date ?: "", style = MaterialTheme.typography.subtitle1)
                    Text(text = "${workout.duration ?: ""}" , style = MaterialTheme.typography.subtitle2)
                }
            }
            AnimatedVisibility(
                visible = showDetail,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
                /* TODO: Put Sets of workout here! */
//                    Column {
//                        Divider()
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Row(modifier = Modifier.padding(16.dp, 0.dp)) {
//                            Text(text =  ?: "")
//                        }
//
//                        Text(
//                            text = "${todo.estimatedCompletionTime ?: ""}hrs",
//                            modifier = Modifier.padding(16.dp, 0.dp)
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                    }
            }
        }
    }
}