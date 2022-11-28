package com.example.jim.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jim.ui.components.ExerciseListItem
import com.example.jim.ui.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel: HomeScreenViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

//    val sortedList by remember(state.showHigherPriorityItemsFirst) {
//        derivedStateOf {
//            if (state.showHigherPriorityItemsFirst) {
//                state.exercises.sortedBy { it.priority }.reversed()
//            } else {
//                state.exercises
//            }
//        }
//    }

    LaunchedEffect(true) {
        val loadingTodos = async {viewModel.getTodos()}
        delay(2000)
        loadingTodos.await()
        state.loading = false
    }

    Column {
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))

        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = state.showHigherPriorityItemsFirst, onCheckedChange = { state.showHigherPriorityItemsFirst = it })
                Text(text = "Show high priority items first")
            }
//            LazyColumn(modifier = Modifier
//                .fillMaxHeight()
//                .padding(16.dp)) {
//                items(sortedList, key = {it.id!!}) { exercise ->
//                    ExerciseListItem(
//                        exercise = exercise,
//                        toggle = {
//                            scope.launch {
//                                viewModel.toggleTodoCompletion(exercise)
//                            }
//                        },
//                        onEditPressed = {
//                            navHostController.navigate("editexercise?id=${exercise.id}")
//                        }
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//            }
        }
    }
}