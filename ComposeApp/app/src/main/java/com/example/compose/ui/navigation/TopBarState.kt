package com.example.compose.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class TopBarState(
    var arrangement: Arrangement.Horizontal = Arrangement.Center,
    var actions : @Composable RowScope.() -> Unit = {}
    )