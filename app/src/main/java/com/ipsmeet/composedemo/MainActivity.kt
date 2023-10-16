package com.ipsmeet.composedemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ipsmeet.composedemo.ui.theme.ComposeDemoTheme
import com.ipsmeet.composedemo.ui.theme.Pink80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
                if (shouldShowOnboarding) {
                    OnboardingScreen(onContinue = { shouldShowOnboarding = false })
                } else {
                    MyApp()
                }
            }   // ComposeDemoTheme
        }
    }
}

@Composable
fun OnboardingScreen(onContinue: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Welcome to the\nBasics compose demo app!",
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinue
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    ComposeDemoTheme {
        OnboardingScreen {}
    }
}

@Composable
fun MyApp() {
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Spacer(
                modifier = Modifier
                    .width(10.dp)
                    .padding(15.dp)
            )
            Greeting("Android")
        }
        FirstLayout()
        IncrementCounter()
        CreateList()
        IntentButton(LocalContext.current)
    }   // column
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun FirstLayout() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black),
        color = Pink80
    ) {
        Row(
            modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Android launcher icon",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(0.5.dp, Color.White, CircleShape)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(
                    text = "Title",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(3.dp)
                )
                Text(
                    text = "Descriptor",
                    color = Color.White,
                    fontSize = 8.sp,
                    modifier = Modifier.padding(3.dp)
                )
            }   // column
        }   // row
    }   // surface
}

@Preview(showBackground = true)
@Composable
fun IncrementCounter() {
    var count by remember {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column {
            Text(
                text = "Counter:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        count++
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Image(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        Modifier.size(18.dp)
                    )
                    Text(
                        text = "Increment",
                        color = Color.Black,
                        modifier = Modifier.padding(3.dp, 0.dp, 0.dp, 0.dp)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = count.toString(),
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Light,
                )
                Spacer(modifier = Modifier.width(15.dp))
                Button(
                    onClick = {
                        if (count > 0) {
                            count--
                        }
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(text = "Decrement", color = Color.Black)
                }
            }   // row
        }   // column
    }   // surface
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CreateList() {
    var data by remember { mutableStateOf("") }
    var dataList by remember { mutableStateOf(listOf<String>()) }
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Surface(
//        color = Pink80,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
    ) {
        Column {
            Text(
                text = "Add data in list:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(15.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp, 15.dp, 15.dp, 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = data,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Green,
                        containerColor = Color.White
                    ),
                    label = {
                        Text(
                            text = "Enter something",
                            modifier = Modifier.background(Color.Transparent)
                        )
                    },
                    modifier = Modifier
                        .size(150.dp, Dp.Infinity)
                        .weight(2.6f),
                    onValueChange = { text ->
                        data = text
                    }
                )   // outlined-text-field
                Spacer(modifier = Modifier.width(5.dp))
                Button(
                    modifier = Modifier.weight(1.5f),
                    onClick = {
                        if (data != "") {
                            dataList = dataList + data
                            data = ""
                        }
                    }) {
                    Text(text = "Add data", modifier = Modifier.clip(RoundedCornerShape(1.dp)))
                }   // button
            }   // row
            if (dataList.isNotEmpty()) {
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "List:",
                        fontSize = 18.sp,
                        textDecoration = TextDecoration.Underline
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth()
                    ) {
                        items(dataList) {
                            Text(text = it)
                        }
                    }   // lazy-column
                }   // column
            }   // if
        }   // column
    }   // surface
}

@Composable
fun IntentButton(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.size(200.dp, Dp.Infinity),
            onClick = {
                context.startActivity(
                    Intent(context.applicationContext, MainActivity2::class.java)
                )
            }) {
            Text(
                text = "Click here to see EXTENDABLE LAZY-LIST",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeDemoTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun IntentButtonPreview() {
    IntentButton(context = LocalContext.current)
}