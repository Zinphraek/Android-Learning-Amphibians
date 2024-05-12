package com.learningandroid.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.learningandroid.amphibians.R
import com.learningandroid.amphibians.network.AmphibianData
import com.learningandroid.amphibians.ui.theme.AmphibiansTheme

@Composable
fun HomeScreen(
  amphibianUiState: AmphibianUiState,
  retryAction: () -> Unit,
  modifier: Modifier,
  contentPadding: PaddingValues,
) {
  when (amphibianUiState) {
    is AmphibianUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    is AmphibianUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
    is AmphibianUiState.Success -> AmphibianScreen(
      amphibianUiState.amphibians,
      contentPadding = contentPadding, modifier = modifier
    )
  }
}

@Composable
fun AmphibianScreen(
  amphibians: List<AmphibianData>,
  modifier: Modifier,
  contentPadding: PaddingValues = PaddingValues(0.dp)
) {
  LazyVerticalGrid(
    columns = GridCells.Adaptive(300.dp),
    modifier = modifier.padding(horizontal = 4.dp),
    contentPadding = contentPadding
  ) {
    items(
      items = amphibians, key = { amphibianData -> amphibianData.name }) { amphibianData ->
      AmphibianCard(
        amphibianData = amphibianData,
        modifier = modifier
          .padding(4.dp)
          .fillMaxWidth()
          .aspectRatio(1.5f)
      )
    }
  }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
  Image(
    painter = painterResource(R.drawable.loading_img),
    contentDescription = stringResource(R.string.loading),
    modifier = modifier.size(200.dp)
  )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(R.drawable.ic_connection_error),
      contentDescription = stringResource(R.string.no_connection)
    )
    Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    Button(onClick = retryAction) {
      Text(stringResource(R.string.retry))
    }
  }
}

@Composable
fun AmphibianCard(amphibianData: AmphibianData, modifier: Modifier = Modifier) {
  Card(
    modifier = modifier.background(color = Color(red = 223, green = 229, blue = 216)),
    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
  ) {
    Column {
      Text(text = "${amphibianData.name} (${amphibianData.type})", fontWeight = FontWeight.Bold)
      AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
          .data(amphibianData.imgSrc)
          .crossfade(true)
          .build(),
        contentScale = ContentScale.Crop,
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.amphibian_image),
        modifier = Modifier.fillMaxWidth()
      )
      Text(text = amphibianData.description, fontWeight = FontWeight.Light)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun AmphibianScreenPreview() {
  AmphibiansTheme {
    val mockData = List(10) { AmphibianData("$it", "", "", "") }
    AmphibianScreen(amphibians = mockData, modifier = Modifier)
  }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
  AmphibiansTheme {
    ErrorScreen({})
  }
}