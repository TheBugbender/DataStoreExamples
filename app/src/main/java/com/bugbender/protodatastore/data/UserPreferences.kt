package com.bugbender.protodatastore.data

import kotlinx.serialization.Serializable


@Serializable
enum class SortOrder {
    NONE,
    BY_DEADLINE,
    BY_PRIORITY,
    BY_DEADLINE_AND_PRIORITY
}

@Serializable
data class UserPreferences (
    val showCompleted: Boolean,
    val sortOrder: SortOrder
)