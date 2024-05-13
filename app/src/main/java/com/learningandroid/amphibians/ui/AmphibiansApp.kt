@file:OptIn(ExperimentalMaterial3Api::class)

package com.learningandroid.amphibians.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learningandroid.amphibians.R
import com.learningandroid.amphibians.ui.screens.AmphibianViewModel
import com.learningandroid.amphibians.ui.screens.HomeScreen


@Composable
fun AmphibiansApp() {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = { AmphibianTopAppBar(scrollBehavior = scrollBehavior) }
  ) {
    Surface(
      modifier = Modifier.fillMaxSize()
    ) {
      val amphibianViewModel: AmphibianViewModel = viewModel(factory = AmphibianViewModel.Factory)
      HomeScreen(
        amphibianUiState = amphibianViewModel.amphibianUiState,
        retryAction = amphibianViewModel::getAmphibiansData,
        contentPadding = it,
        modifier = Modifier
      )
    }
  }
}

@Composable
fun AmphibianTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
  TopAppBar(
    scrollBehavior = scrollBehavior,
    title = {
      Text(
        text = stringResource(R.string.app_name),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.headlineMedium,
      )
    },
    modifier = modifier
  )
}
