package vn.dungnt.nothing.presentation.composes.bottomNav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import vn.dungnt.nothing.presentation.composes.bottomNav.book.bookGraph
import vn.dungnt.nothing.presentation.viewmodels.BookViewModel
import vn.dungnt.nothing.utils.Constants

@Composable
fun NavigationGraph(navController: NavHostController, defaultLanguage: String) {
    val bookViewModel = hiltViewModel<BookViewModel>()
    NavHost(
        navController = navController,
        startDestination = Constants.BookScreen.Start.name
    ) {
        bookGraph(navController)
        composable(Destinations.AccountScreen.route) {
            AccountScreen(defaultLanguage = defaultLanguage)
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    val screens = listOf(
        Destinations.BookScreen, Destinations.AccountScreen
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.LightGray,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            val selected =
                (screen.route == currentRoute) || ((screen.route == Destinations.BookScreen.route && Constants.BookScreen.entries.find { it.name == currentRoute } != null))

            NavigationBarItem(
                label = {
                    Text(text = screen.title!!)
                },
                icon = {
                    Icon(imageVector = screen.icon!!, contentDescription = "")
                },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.Gray, selectedTextColor = Color.White
                ),
            )
        }
    }

}