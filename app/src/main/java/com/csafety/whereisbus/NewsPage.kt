package com.csafety.whereisbus

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.csafety.whereisbus.ui.theme.WhereIsBusTheme

@Composable
fun PostDate(
    post: BusNew
) {
    Text(text = "发布日期: " + post.date, style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun PostTitle(
    post: BusNew
) {
    Text(
        text = post.title,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun PostCard(
    post: BusNew
) {
    // 声明一个状态来跟踪对话框是否可见
    val showDialog = remember { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxWidth()
            .clickable {
                showDialog.value = true
            },
    ) {
        Column(
            Modifier.padding(16.dp, 8.dp)
        ) {
            PostTitle(post)
            PostDate(post)
        }
        // 根据对话框的可见状态来显示或隐藏对话框
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text("内容") },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(300.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = post.content,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog.value = false
                            // 执行确认操作
                        }
                    ) {
                        Text("确认")
                    }
                },
            )
        }
    }
}

@Composable
fun NewsPage(data: BusPagesData, title: String = "信息查询") {
    val busNewList = data.busNew
    val profile = remember {
        mutableStateOf(busNewList)
    }
    LaunchedEffect(key1 = Unit, block = {
        sendRequestToGetBusNews(
            profileState = profile
        )
        Log.d("Main Activity", profile.toString())
    })
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top
    ) {
        TitleBar(title)
        LazyColumn {
            itemsIndexed(items = profile.value) { _, item ->
                PostCard(post = item)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewsPagePreview() {
    WhereIsBusTheme {
        NewsPage(mockBusPagesData)
    }
}