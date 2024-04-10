package palbp.laboratory.simplexludum.ui.common

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Resource identifiers
const val SEARCH_LABEL: String = "search_label"
const val CLEAR_SEARCH: String = "clear_search"

@Composable
fun SearchBox(onSearchRequested: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        var query by remember { mutableStateOf("") }
        OutlinedTextField(
            value = query,
            onValueChange = { query = it; onSearchRequested(it) },
            label = { Text(text = stringResource(SEARCH_LABEL)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "Clear search query",
                    modifier = Modifier.clickable { query = "" }
                )
            },
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(CLEAR_SEARCH),
            modifier = Modifier.clickable { query = "" }
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
}
