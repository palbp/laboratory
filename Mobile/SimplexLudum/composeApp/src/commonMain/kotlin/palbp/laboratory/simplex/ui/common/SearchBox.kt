package palbp.laboratory.simplex.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import simplexludum.composeapp.generated.resources.Res
import simplexludum.composeapp.generated.resources.clear_search
import simplexludum.composeapp.generated.resources.do_search

// Tags used for testing. These tags identify the semantically relevant elements in the UI.
const val SEARCH_INPUT_TAG = "search_input"
const val CANCEL_ICON_TAG = "cancel_icon"
const val CANCEL_BUTTON_TAG = "cancel_button"

@Composable
fun SearchBox(onSearchRequested: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        var query by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            singleLine = true,
            value = query,
            onValueChange = { query = it; onSearchRequested(it) },
            label = { Text(text = stringResource(Res.string.do_search)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "Clear search query",
                    modifier = Modifier
                        .testTag(CANCEL_ICON_TAG)
                        .clickable { query = "" }
                )
            },
            modifier = Modifier.weight(1f).testTag(SEARCH_INPUT_TAG),
            shape = MaterialTheme.shapes.medium,
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(Res.string.clear_search),
            modifier = Modifier
                .testTag(CANCEL_BUTTON_TAG)
                .clickable { query = ""; focusManager.clearFocus() }
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
}
