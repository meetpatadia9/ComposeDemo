package com.ipsmeet.composedemo

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ipsmeet.composedemo.ui.theme.ComposeDemoTheme

class MainActivity3 : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val configuration = LocalConfiguration.current
            val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

            if (isPortrait) {
                Scaffold(
                    bottomBar = { NavigationBarView() }
                ) {
                    RealWorldDemo(Modifier.padding(it))
                }
            } else {
                Row {
                    NavigationRailView()
                    HomeMainView()
                }
            }
        }
    }
}

@Composable
fun RealWorldDemo(modifier: Modifier) {
    ComposeDemoTheme {
        Surface(modifier = modifier) {
            HomeMainView()
        }
    }
}

@Composable
fun HomeMainView() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Searchbar()
        HomeScreen(title = "Align your body") {
            AlignYourBodyRow()
        }
        HomeScreen(title = "Favorite collections") {
            FavoriteCollectionCardGridRow()
        }
    }
}

@Composable
fun HomeScreen(title: String, content: @Composable () -> Unit) {
    Text(
        text = title,
        modifier = Modifier.padding(start = 10.dp, top = 26.dp),
        style = MaterialTheme.typography.titleMedium
    )
    content()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Searchbar() {
    var searchQuery by remember { mutableStateOf("") }

    TextField(
        value = searchQuery,
        textStyle = TextStyle.Default.copy(fontSize = 16.sp),
        onValueChange = { searchQuery = it },
        placeholder = { Text(text = "Search") },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.round_search_24),
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        maxLines = 1,
    )
}

@Composable
fun AlignYourBody(image: Int, title: String) {
    Surface {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 10.dp),
            )
        }   // column
    }   // surface
}

@Composable
fun FavoriteCollectionCard(image: Int, title: String) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(255.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Image(
                painter = painterResource(id = image),
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }   // row
    }   // surface
}

@Composable
fun AlignYourBodyRow() {
    val itemList = listOf(
        ListViewDataClass(R.drawable.img_inversion_yoga, "Inversions"),
        ListViewDataClass(R.drawable.img_quick_yoga, "Quick yoga"),
        ListViewDataClass(R.drawable.img_streatching_yoga, "Stretching"),
        ListViewDataClass(R.drawable.img_tabata, "Tabata"),
        ListViewDataClass(R.drawable.img_hiit, "HIIT"),
        ListViewDataClass(R.drawable.img_pre_natal_yoga, "Pre-natal yoga")
    )

    LazyRow {
        items(itemList) {
            AlignYourBody(it.image, it.title)
        }
    }
}

@Composable
fun FavoriteCollectionCardGridRow() {
    val itemList = listOf(
        ListViewDataClass(R.drawable.img_short_mantras, "Short mantras"),
        ListViewDataClass(R.drawable.img_nature_meditation, "Nature meditation"),
        ListViewDataClass(R.drawable.img_stress_n_anxiety, "Stress and anxiety"),
        ListViewDataClass(R.drawable.img_self_msg, "Self message"),
        ListViewDataClass(R.drawable.img_overwhelmed, "Overwhelmed"),
        ListViewDataClass(R.drawable.img_nightly, "Nightly wind down")
    )

    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier.height(204.dp)
    ) {
        items(itemList) {
            FavoriteCollectionCard(it.image, it.title)
        }
    }
}

@Composable
fun NavigationBarView() {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.round_home_24),
                    contentDescription = null
                )
            },
            label = {
                Text(text = "Home")
            }
        )   // home - navigation item
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.round_account_circle_24),
                    contentDescription = null
                )
            },
            label = {
                Text(text = "Profile")
            }
        )   // profile - navigation item
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationRailView() {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationRailItem(
                selected = true,
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.round_home_24),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = "Home")
                }
            )   // home - navigation item
            NavigationRailItem(
                selected = true,
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.round_account_circle_24),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = "Profile")
                }
            )   // profile - navigation item
        }
    }
}