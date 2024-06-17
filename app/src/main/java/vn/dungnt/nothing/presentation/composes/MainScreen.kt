package vn.dungnt.nothing.presentation.composes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import vn.dungnt.nothing.presentation.composes.bottomNav.BottomNavBar
import vn.dungnt.nothing.presentation.composes.bottomNav.NavigationGraph
import vn.dungnt.nothing.presentation.theme.NothingTheme

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    NothingTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val navController: NavHostController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavBar(navController = navController)
                }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    NavigationGraph(navController = navController)
                }
            }
        }
    }
}