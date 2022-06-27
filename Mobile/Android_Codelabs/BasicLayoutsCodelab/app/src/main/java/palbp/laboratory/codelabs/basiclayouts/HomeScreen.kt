package palbp.laboratory.codelabs.basiclayouts

import AlignYourBodyRow
import alignYourBodyPreviewData
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import palbp.laboratory.codelabs.basiclayouts.ui.theme.BasicLayoutsCodelabTheme
import java.util.*

@Composable
fun HomeScreen(
    alignYourBodyData: List<DrawableStringPair>,
    favoriteCollectionsData: List<DrawableStringPair>,
    modifier: Modifier = Modifier) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(modifier = Modifier.padding(horizontal = 16.dp))
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow(alignYourBodyData)
        }
        HomeSection(title = R.string.align_your_body) {
            FavoriteCollectionsGrid(favoriteCollectionsData)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        onValueChange = { },
        leadingIcon = {
              Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        placeholder = {
            Text(text = stringResource(R.string.placeholder_search))
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        modifier = modifier
            .heightIn(min = 56.dp)
            .fillMaxWidth()
    )
}

@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = title).uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.h2,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 8.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

@Composable
fun BasicLayoutsCodeLabBottomNavigation(modifier: Modifier = Modifier) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        BottomNavigationItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Default.Spa, contentDescription = null) },
            label = { Text(stringResource(id = R.string.bottom_navigation_home)) }
        )
        BottomNavigationItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
            label = { Text(stringResource(id = R.string.bottom_navigation_profile)) }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2, heightDp = 200)
@Composable
fun HomeScreenPreview() {
    BasicLayoutsCodelabTheme {
        HomeScreen(
            alignYourBodyData = alignYourBodyPreviewData,
            favoriteCollectionsData = favoriteCollectionsPreviewData
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun SearchBarPreview() {
    BasicLayoutsCodelabTheme {
        SearchBar(Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun HomeSectionPreview() {
    BasicLayoutsCodelabTheme {
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow(data = alignYourBodyPreviewData)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun BasicLayoutsCodeLabBottomNavigationPreview() {
    BasicLayoutsCodelabTheme {
        BasicLayoutsCodeLabBottomNavigation()
    }
}
