package com.bugbender.protodatastore.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bugbender.protodatastore.R
import com.bugbender.protodatastore.ui.theme.DataStoreTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.proto_datastore)) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Preview
@Composable
private fun AppTopBarPreview() {
    DataStoreTheme {
        AppTopBar()
    }
}

