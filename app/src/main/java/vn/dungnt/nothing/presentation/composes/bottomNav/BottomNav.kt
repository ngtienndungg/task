package vn.dungnt.nothing.presentation.composes.bottomNav

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import vn.dungnt.nothing.presentation.composes.bottomNav.book.bookGraph
import vn.dungnt.nothing.presentation.theme.Pink80
import vn.dungnt.nothing.presentation.theme.Purple40
import vn.dungnt.nothing.presentation.theme.Purple80
import vn.dungnt.nothing.utils.Constants

@Composable
fun NavigationGraph(navController: NavHostController, defaultLanguage: String) {
    NavHost(
        navController = navController,
        startDestination = Constants.BookScreen.Start.name
    ) {
        bookGraph(navController)
        composable(Destinations.AccountScreen.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }) {
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
        containerColor = Purple80,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            val selected =
                (screen.route == currentRoute) || ((screen.route == Destinations.BookScreen.route && Constants.BookScreen.entries.find {
                    currentRoute?.contains(
                        it.name
                    ) == true
                } != null))

            NavigationBarItem(
                label = {
                    Text(text = stringResource(id = screen.titleId))
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
                    selectedTextColor = Purple40,
                    selectedIconColor = Purple40
                )
            )
        }
    }

}