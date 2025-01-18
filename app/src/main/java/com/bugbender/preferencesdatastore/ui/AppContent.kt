package com.bugbender.preferencesdatastore.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bugbender.preferencesdatastore.data.Task
import com.bugbender.preferencesdatastore.data.TaskPriority
import com.bugbender.preferencesdatastore.proto.UserPreferences.*
import com.bugbender.preferencesdatastore.ui.components.AppBottomBar
import com.bugbender.preferencesdatastore.ui.components.AppTopBar
import com.bugbender.preferencesdatastore.ui.components.TaskList
import com.bugbender.preferencesdatastore.ui.theme.PreferencesDataStoreTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AppContent(
    screenState: TasksViewModel.ScreenState,
    onShowCompletedTaskChange: (Boolean) -> Unit,
    onPriorityClicked: (Boolean) -> Unit,
    onDeadlineClicked: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar()
        },
        content = { paddingValues ->
            TaskList(
                tasks = screenState.tasks,
                modifier = Modifier.padding(paddingValues)
            )
        },
        bottomBar = {
            AppBottomBar(
                showCompletedTasks = screenState.showCompleted,
                sortOrder = screenState.sortOrder,
                onShowCompletedTaskChange = onShowCompletedTaskChange,
                onPriorityClicked = onPriorityClicked,
                onDeadlineClicked = onDeadlineClicked,
            )
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showSystemUi = true)
@Composable
private fun AppContentPreview() {
    PreferencesDataStoreTheme {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        AppContent(
            TasksViewModel.ScreenState(
                tasks = listOf(
                    Task(
                        name = "Open codelab",
                        deadline = simpleDateFormat.parse("2020-07-03")!!,
                        priority = TaskPriority.LOW,
                        completed = true
                    ),
                    Task(
                        name = "Import project",
                        deadline = simpleDateFormat.parse("2020-04-03")!!,
                        priority = TaskPriority.MEDIUM,
                        completed = true
                    ),
                    Task(
                        name = "Check out the code",
                        deadline = simpleDateFormat.parse("2020-05-03")!!,
                        priority = TaskPriority.LOW
                    ),
                    Task(
                        name = "Read about DataStore",
                        deadline = simpleDateFormat.parse("2020-06-03")!!,
                        priority = TaskPriority.HIGH
                    ),
                    Task(
                        name = "Implement each step",
                        deadline = Date(),
                        priority = TaskPriority.MEDIUM
                    ),
                    Task(
                        name = "Understand how to use DataStore",
                        deadline = simpleDateFormat.parse("2020-04-03")!!,
                        priority = TaskPriority.HIGH
                    ),
                    Task(
                        name = "Understand how to migrate to DataStore",
                        deadline = Date(),
                        priority = TaskPriority.HIGH
                    )
                ),
                showCompleted = true,
                sortOrder = SortOrder.BY_DEADLINE
            ),
            {}, {}, {}
        )
    }
}