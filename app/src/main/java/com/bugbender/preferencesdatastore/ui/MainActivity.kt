package com.bugbender.preferencesdatastore.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bugbender.preferencesdatastore.R
import com.bugbender.preferencesdatastore.data.Task
import com.bugbender.preferencesdatastore.data.TasksRepository
import com.bugbender.preferencesdatastore.data.UserPreferencesRepository
import com.bugbender.preferencesdatastore.ui.components.AppBottomBar
import com.bugbender.preferencesdatastore.ui.components.AppTopBar
import com.bugbender.preferencesdatastore.ui.components.TaskList
import com.bugbender.preferencesdatastore.ui.theme.PreferencesDataStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PreferencesDataStoreTheme {
                AppScreen()
            }
        }
    }
}


@Composable
fun AppScreen() {
    val context = LocalContext.current

    val viewModel = viewModel<TasksViewModel>(
        factory = TasksViewModel.Factory(
            taskRepository = TasksRepository,
            userPreferencesRepository = UserPreferencesRepository(dataStore = context.dataStore)
        )
    )

    val state by viewModel.screenStateFlow.collectAsStateWithLifecycle()

    AppContent(
        screenState = state,
        onShowCompletedTaskChange = viewModel::showCompletedTasks,
        onPriorityClicked = viewModel::enableSortByPriority,
        onDeadlineClicked = viewModel::enableSortByDeadline
    )
}