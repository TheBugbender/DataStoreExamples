package com.bugbender.preferencesdatastore.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugbender.preferencesdatastore.R
import com.bugbender.preferencesdatastore.data.Task
import com.bugbender.preferencesdatastore.data.TaskPriority
import com.bugbender.preferencesdatastore.ui.theme.PreferencesDataStoreTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TaskCard(task: Task) {
    val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text(
            text = task.name,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Priority ${task.priority.name}",
            color = when (task.priority) {
                TaskPriority.HIGH -> Color.Red
                TaskPriority.MEDIUM -> Color(0xFFFF9800)
                TaskPriority.LOW -> Color.Green
            },
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.calendar_month_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondaryContainer
            )
            Text(
                text = dateFormat.format(task.deadline),
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCardPreview() {
    PreferencesDataStoreTheme {
        TaskCard(
            task = Task(
                name = "Understand how to live and find a job as soon as possible",
                deadline = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse("2020-07-03")!!,
                priority = TaskPriority.MEDIUM,
                completed = false
            )
        )
    }
}