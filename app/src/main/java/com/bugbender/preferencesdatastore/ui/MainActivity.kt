package com.bugbender.preferencesdatastore.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.bugbender.preferencesdatastore.R
import com.bugbender.preferencesdatastore.ui.components.AppBottomBar
import com.bugbender.preferencesdatastore.ui.components.AppTopBar
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
    Scaffold(
        topBar = {
            AppTopBar()
        },
        content = { paddingValues ->

        },
        bottomBar = {
            AppBottomBar(
                onShowCompletedTaskChange = {},
                onPriorityClicked = {},
                onDeadlineClicked = {},
            )
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showSystemUi = true)
@Composable
private fun AppScreenPreview() {
    PreferencesDataStoreTheme {
        AppScreen()
    }
}