package com.csafety.whereisbus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csafety.whereisbus.ui.theme.WhereIsBusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WhereIsBusTheme {
                val navController = rememberNavController()
                Column {
//                val currentRoute = navController.currentDestination?.route ?: "bus"
                    NavHost(navController = navController, startDestination = "bus", Modifier.weight(1f)) {
                        tabBarList.forEach {
                            composable(it.path, content = it.comp)
                        }
                    }
                    NavBar(navController)
                }
            }
        }
    }
}

