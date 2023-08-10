package com.csafety.whereisbus

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.csafety.whereisbus.ui.theme.WhereIsBusTheme


@Composable
fun NavItem(
    iconRes: ImageVector,
    description: String,
    tint: Color,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        Modifier
            .fillMaxHeight(),
        shape = RectangleShape,
        colors = ButtonDefaults.outlinedButtonColors()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = iconRes,
                description,
                Modifier
                    .size(36.dp)
                    .weight(1f), tint
            )
            Text(text = description, fontSize = 12.sp, color = tint)
        }
    }
}

@Composable
fun NavBar(navController: NavController) {
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    LazyRow(
        Modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(items = tabBarList) { index, item ->
            NavItem(
                item.icon,
                item.name,
                if (selectedTabIndex.intValue == index)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.secondary,
                onClick = {
                    navController.navigate(item.path)
                    selectedTabIndex.intValue = tabBarList.indexOfFirst {
                        it.path == (navController.currentDestination?.route ?: "bus")
                    }
                }

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavBarPreview() {
    val navController = rememberNavController()
    WhereIsBusTheme {
        NavBar(navController)
    }
}