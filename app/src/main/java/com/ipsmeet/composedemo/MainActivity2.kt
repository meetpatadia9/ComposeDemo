package com.ipsmeet.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity2 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExtendableLazyList()
        }
    }
}

@Composable
fun ExtendableLazyList(
    dataList: List<String> = List(20) { "${it + 1}" }
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(dataList) {
            SingleListView(i = it)
        }
    }
}

@Composable
fun SingleListView(i: String) {
    var expanded by remember { mutableStateOf(false) }
    val bottomSpace by animateDpAsState(
        targetValue = if (expanded) 100.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "tween"
    )

    Row(
        modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
    ) {
        Column(
            Modifier
                .background(
                    MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(5.dp)
                )
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp)
        ) {
            Row(
                modifier = Modifier.padding(bottom = bottomSpace.coerceAtLeast(0.dp)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = i, fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )   // text
                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(Color.White),
                    onClick = {
                        expanded = !expanded
                    }) {
                    Text(
                        text =
                        if (expanded)
                            "Show less"
                        else
                            "Show more",
                        color = Color.Black
                    )
                }   // outlined-button
            }   // row with text and button
        }   // column, the one background color
    }   // row
}

@Preview(showBackground = true)
@Composable
fun PreviewExtendableLazyList() {
    ExtendableLazyList()
}