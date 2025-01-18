package com.bugbender.preferencesdatastore.ui

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
import com.bugbender.preferencesdatastore.data.TasksRepository
import com.bugbender.preferencesdatastore.data.UserPreferencesRepository
import com.bugbender.preferencesdatastore.data.UserPreferencesSerializer
import com.bugbender.preferencesdatastore.proto.UserPreferences
import com.bugbender.preferencesdatastore.ui.theme.PreferencesDataStoreTheme


private const val DATA_STORE_FILE_NAME = "user_preferences.pb"

private val Context.userPreferencesStore: DataStore<UserPreferences> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = UserPreferencesSerializer
)

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
    val viewModel: TasksViewModel = viewModel(
        factory = TasksViewModel.Factory(
            taskRepository = TasksRepository,
            userPreferencesRepository = UserPreferencesRepository(
                dataStore = context.userPreferencesStore
            )
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

@Preview(showSystemUi = true)
@Composable
private fun AppScreenPreview() {
    PreferencesDataStoreTheme {
        AppScreen()
    }
}