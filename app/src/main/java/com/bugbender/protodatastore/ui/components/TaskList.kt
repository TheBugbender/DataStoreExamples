package com.bugbender.protodatastore.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bugbender.protodatastore.data.Task
import com.bugbender.protodatastore.data.TaskPriority
import com.bugbender.protodatastore.ui.theme.DataStoreTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TaskList(tasks: List<Task>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier.fillMaxSize() then modifier) {
        itemsIndexed(tasks) { index, task ->
            TaskCard(task)
            if (index < tasks.lastIndex) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskListPreview() {
    DataStoreTheme {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        TaskList(
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
                    name = "Check out the code", deadline = simpleDateFormat.parse("2020-05-03")!!,
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
            )
        )
    }
}