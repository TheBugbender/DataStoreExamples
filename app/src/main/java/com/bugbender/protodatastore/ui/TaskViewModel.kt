package com.bugbender.protodatastore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bugbender.protodatastore.data.Task
import com.bugbender.protodatastore.data.TasksRepository
import com.bugbender.protodatastore.data.UserPreferencesRepository
import com.bugbender.protodatastore.proto.UserPreferences
import com.bugbender.protodatastore.proto.UserPreferences.SortOrder
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TasksViewModel(
    repository: TasksRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {


    val screenStateFlow = combine(
        repository.tasks,
        userPreferencesRepository.userPreferences
    ) { tasks: List<Task>, userPreferences: UserPreferences ->
        return@combine ScreenState(
            tasks = filterSortTasks(
                tasks = tasks,
                showCompleted = userPreferences.showCompleted,
                sortOrder = userPreferences.sortOrder
            ),
            showCompleted = userPreferences.showCompleted,
            sortOrder = userPreferences.sortOrder
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScreenState()
    )

    fun showCompletedTasks(show: Boolean) = viewModelScope.launch {
        userPreferencesRepository.updateShowCompleted(show)
    }

    fun enableSortByDeadline(enable: Boolean) = viewModelScope.launch {
        userPreferencesRepository.enableSortByDeadline(enable)
    }

    fun enableSortByPriority(enable: Boolean) = viewModelScope.launch {
        userPreferencesRepository.enableSortByPriority(enable)
    }

    private fun filterSortTasks(
        tasks: List<Task>,
        showCompleted: Boolean,
        sortOrder: SortOrder
    ): List<Task> {
        // filter the tasks
        val filteredTasks = if (showCompleted) {
            tasks
        } else {
            tasks.filter { !it.completed }
        }
        // sort the tasks
        return when (sortOrder) {
            SortOrder.BY_DEADLINE -> filteredTasks.sortedByDescending { it.deadline }
            SortOrder.BY_PRIORITY -> filteredTasks.sortedBy { it.priority }
            SortOrder.BY_DEADLINE_AND_PRIORITY -> filteredTasks.sortedWith(
                compareByDescending<Task> { it.deadline }.thenBy { it.priority }
            )
            else -> filteredTasks

        }
    }

    data class ScreenState(
        val tasks: List<Task> = listOf(),
        val showCompleted: Boolean = false,
        val sortOrder: SortOrder = SortOrder.NONE
    )

    class Factory(
        private val taskRepository: TasksRepository,
        private val userPreferencesRepository: UserPreferencesRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TasksViewModel(taskRepository, userPreferencesRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

