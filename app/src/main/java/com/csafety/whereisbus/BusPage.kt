package com.csafety.whereisbus

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csafety.whereisbus.ui.theme.WhereIsBusTheme

@Composable
fun SearchBar () {
    Row(
        Modifier
            .padding(24.dp, 2.dp, 24.dp, 6.dp)
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically) {
        var searchText by remember { mutableStateOf("") }
        BasicTextField(
            value = searchText,
            onValueChange = { searchText = it },
            Modifier
                .padding(start = 24.dp)
                .weight(1f),
            textStyle = TextStyle(fontSize = 15.sp)
        ){
            if (searchText.isEmpty()) {
                Text(text = "搜搜看?", color = MaterialTheme.colorScheme.secondary, fontSize = 15.sp)
            }
            it()
        }
        Box(modifier = Modifier
            .padding(6.dp)
            .fillMaxHeight()
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)) {
            Icon(painter = painterResource(id = R.drawable.twotone_search_24), contentDescription = "搜索",
                Modifier
                    .size(24.dp)
                    .align(Alignment.Center), tint = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun HotBusButtons (hotBusLineList: List<HotBusLine>?) {
    val profile = remember {
        mutableStateOf(hotBusLineList ?: listOf())
    }

    LaunchedEffect(key1 = Unit, block = {
        sendRequestToGetHotBusLine(
            profileState = profile
        )

        Log.d("Main Activity", profile.toString())
    })

    Column(
        Modifier
            .padding(24.dp, 12.dp)
            .fillMaxWidth()) {
        Text(text = "热门公交: ")
        LazyRow (
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(0.dp, 8.dp)
            ){
            itemsIndexed(items = profile.value) {
                    _, item ->
                Button(onClick = {
                }, Modifier.padding(4.dp)) {
                    Text(text = item.name)
                }
            }

        }
    }
}

@Composable
fun BusPage (data: BusPagesData, title: String = "公交查询") {
    val hotBusLineList = data.hotBusLine
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top
    ) {
        TitleBar(title)
        SearchBar()
        HotBusButtons(hotBusLineList)
    }
}



@Preview(showBackground = true)
@Composable
fun BusPagePreview() {
    WhereIsBusTheme {
        BusPage(mockBusPagesData)
    }
}