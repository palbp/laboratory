package pdm.demos.mazepathfinder.config

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pdm.demos.mazepathfinder.R
import pdm.demos.mazepathfinder.domain.Search
import pdm.demos.mazepathfinder.maze.views.mapResource
import pdm.demos.mazepathfinder.ui.theme.MazePathFinderTheme

const val AlgorithmSelectionScreenTag = "AlgorithmSelectionScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlgorithmSelectionScreen(
    onSelected: (Search) -> Unit,
    onCancelled: () -> Unit
) {
    MazePathFinderTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag(AlgorithmSelectionScreenTag),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.title_activity_algorithm_selection)) },
                    navigationIcon = {
                        IconButton(onClick = onCancelled) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
        ) { innerPadding ->
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(top = 16.dp),
            ) {
                items(Search.entries.size) { index ->
                    val algorithm = Search.entries[index]
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelected(algorithm) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(algorithm.mapResource()),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlgorithmSelectionScreenPreview() {
    AlgorithmSelectionScreen(onSelected = { }, onCancelled = { })
}
