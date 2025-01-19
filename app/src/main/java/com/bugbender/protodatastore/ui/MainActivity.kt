package com.bugbender.protodatastore.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bugbender.protodatastore.data.TasksRepository
import com.bugbender.protodatastore.data.UserPreferencesRepository
import com.bugbender.protodatastore.data.UserPreferencesSerializer
import com.bugbender.protodatastore.proto.UserPreferences
import com.bugbender.protodatastore.ui.theme.DataStoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DataStoreTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen() {
    val context = LocalContext.current
    val viewModel: TasksViewModel = viewModel()

    val state by viewModel.screenStateFlow.collectAsStateWithLifecycle()

    AppContent(
        screenState = state,
        onShowCompletedTaskChange = viewModel::showCompletedTasks,
        onPriorityClicked = viewModel::enableSortByPriority,
        onDeadlineClicked = viewModel::enableSortByDeadline
    )
}

@Preview(showSystemUi = true)
@Composable
private fun AppScreenPreview() {
    DataStoreTheme {
        AppScreen()
    }
}