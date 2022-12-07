package com.example.jim.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.jim.ui.models.Exercise
import com.example.jim.ui.models.Set
import com.example.jim.ui.viewmodels.WorkoutSessionScreenState
import com.example.jim.ui.viewmodels.WorkoutSessionViewModel
import kotlinx.coroutines.launch


@Composable
fun ExerciseListItem(
    exercise: Exercise,
    sets: List<Set>? = null,
    viewModel: WorkoutSessionViewModel
) {
    val scope = rememberCoroutineScope()
    var showDetail by remember { mutableStateOf(false) }
    var weight by remember {mutableStateOf(0)}
    var reps by remember {mutableStateOf(0)}

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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = exercise.name ?: "", style = MaterialTheme.typography.subtitle2)
                }
                if(!sets.isNullOrEmpty()){
                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        sets.forEach { set ->
                            Text(text = "${set.weight} lbs x ${set.reps} reps")
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = showDetail,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
                /* TODO: Put Set entry here! */
                Column {
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Weight")
                        Spacer(modifier = Modifier.width(4.dp))
                        FormField(
                            value = "$weight",
                            onValueChange = {
                                if (it.toIntOrNull() != null) weight = it.toInt()
                            },
                            placeholder = { Text(text = "Set Weight") }
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Repetitions")
                        Spacer(modifier = Modifier.width(2.dp))
                        FormField(
                            value = "$reps",
                            onValueChange = {
                                if (it.toIntOrNull() != null) reps = it.toInt()
                            },
                            placeholder = { Text(text = "Set Repetitions") }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            println("Reps: $reps, Weight $weight")
                            val rep = reps
                            val weigh = weight
                            scope.launch { viewModel.addSet(rep, weigh, exercise.id.toString()) }
                            showDetail = false
                            reps = 0
                            weight = 0

                        }) {
                            Text(text = "Add Set")
                        }
                    }
                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun ExerciseListItem(
//    exercise: Exercise,
//    toggle: (checked: Boolean) -> Unit = {},
//    onEditPressed: () -> Unit = {}
//) {
//    var showDetail by remember { mutableStateOf(false) }
//    val swipeableState = rememberSwipeableState(initialValue = SwipeState.CLOSED)
//    val anchors = mapOf(
//        0f to SwipeState.CLOSED,
//        -200f to SwipeState.OPEN
//    )
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(IntrinsicSize.Min)
//            .swipeable(
//                state = swipeableState,
//                anchors = anchors,
//                orientation = Orientation.Horizontal,
//            )
//    ) {
//        Row(modifier = Modifier
//            .fillMaxHeight()
//            .fillMaxWidth(),
//            horizontalArrangement = Arrangement.End
//        ) {
//            Button(
//                onClick = {},
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .fillMaxWidth(.5f),
//                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
//                    horizontalArrangement = Arrangement.End,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
//                }
//            }
//        }
//        Surface(
//            modifier = Modifier
//                .offset { IntOffset(swipeableState.offset.value.toInt(), 0) }
//                .clickable {
//                    showDetail = !showDetail
//                },
//            elevation = 2.dp,
//            shape = RoundedCornerShape(4.dp),
//        ) {
//            Column (){
//                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Checkbox(checked = todo.completed == true, onCheckedChange = toggle)
//                        Text(text = todo.title ?: "", style = MaterialTheme.typography.subtitle2)
//                    }
//                    IconButton(onClick = onEditPressed) {
//                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit button")
//                    }
//                }
//                AnimatedVisibility(
//                    visible = showDetail,
//                    enter = expandVertically(),
//                    exit = shrinkVertically(),
//                ) {
//                    Column {
//                        Divider()
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Row(modifier = Modifier.padding(16.dp, 0.dp)) {
//                            Text(text = todo.description ?: "")
//                        }
//
//                        Text(
//                            text = "${todo.estimatedCompletionTime ?: ""}hrs",
//                            modifier = Modifier.padding(16.dp, 0.dp)
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                    }
//                }
//            }
//        }
//    }
//}
//
////@Preview
////@Composable
////fun ExerciseListItemPreview() {
////    FirebaseExercisesApplicationTheme {
////        ExerciseListItem(todo = Exercise(
////            title = "Go pick up grandma",
////            description = "Grandma needs to go to the store at 5:15",
////            estimatedCompletionTime = 5,
////            id = "fcs3k3lkm2309mgfs",
////            priority = Exercise.PRIORITY_LOW,
////            completed = true,
////            userId = "23oiknmf09sj0982jlkjsdf"
////        ))
////    }
////}