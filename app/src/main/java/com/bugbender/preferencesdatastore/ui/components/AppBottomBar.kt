package com.bugbender.preferencesdatastore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugbender.preferencesdatastore.R
import com.bugbender.preferencesdatastore.data.SortOrder
import com.bugbender.preferencesdatastore.ui.theme.PreferencesDataStoreTheme

@Composable
fun AppBottomBar(
    showCompletedTasks: Boolean,
    sortOrder: SortOrder,
    onShowCompletedTaskChange: (Boolean) -> Unit,
    onPriorityClicked: (Boolean) -> Unit,
    onDeadlineClicked: (Boolean) -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .windowInsetsPadding(BottomAppBarDefaults.windowInsets)
    ) {
        ShowCompletedTasks(
            checked = showCompletedTasks,
            onShowCompletedTaskChange = onShowCompletedTaskChange
        )
        TaskFilters(
            sortOrder = sortOrder,
            onPriorityClicked = onPriorityClicked,
            onDeadlineClicked = onDeadlineClicked
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppBottomBarPreview() {
    PreferencesDataStoreTheme {
        AppBottomBar(true, SortOrder.BY_PRIORITY, {}, {}, {})
    }
}

@Composable
fun ShowCompletedTasks(
    checked: Boolean,
    onShowCompletedTaskChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)
    ) {
        Icon(painter = painterResource(R.drawable.filter_list_24), contentDescription = null)
        Text(
            text = stringResource(R.string.show_completed_tasks),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Switch(checked = checked, onCheckedChange = onShowCompletedTaskChange)
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowCompletedTasksPreview() {
    PreferencesDataStoreTheme {
        ShowCompletedTasks(true, onShowCompletedTaskChange = {})
    }
}

@Composable
fun TaskFilters(
    sortOrder: SortOrder,
    onPriorityClicked: (Boolean) -> Unit,
    onDeadlineClicked: (Boolean) -> Unit,
) {
    val (prioritySelected, deadlineSelected) = when (sortOrder) {
        SortOrder.BY_PRIORITY -> Pair(true, false)
        SortOrder.BY_DEADLINE -> Pair(false, true)
        SortOrder.BY_DEADLINE_AND_PRIORITY -> Pair(true, true)
        SortOrder.NONE -> Pair(false, false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
        TaskFilterChip(
            selected = prioritySelected,
            onClicked = onPriorityClicked,
            label = stringResource(R.string.pririoty),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        TaskFilterChip(
            selected = deadlineSelected,
            onClicked = onDeadlineClicked,
            label = stringResource(R.string.deadline)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskFiltersPreview() {
    PreferencesDataStoreTheme {
        TaskFilters(SortOrder.BY_PRIORITY, {}, {})
    }
}

@Composable
fun TaskFilterChip(
    selected: Boolean,
    onClicked: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    FilterChip(
        onClick = { onClicked(!selected) },
        selected = selected,
        label = { Text(label) },
        leadingIcon = if (selected) {
            {
                Icon(imageVector = Icons.Filled.Done, contentDescription = null)
            }
        } else {
            null
        },
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskFilterChipPreview() {
    PreferencesDataStoreTheme {
        TaskFilterChip(selected = true, onClicked = {}, label = "Filter")
    }
}