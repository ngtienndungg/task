package vn.dungnt.nothing.presentation.composes.bottomNav.book

import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import vn.dungnt.nothing.presentation.composes.bottomNav.Destinations
import vn.dungnt.nothing.utils.Constants

fun NavGraphBuilder.bookGraph(
    navController: NavHostController
) {
    navigation(
        route = Constants.BookScreen.Start.name,
        startDestination = Destinations.BookScreen.route
    ) {
        composable(route = Destinations.BookScreen.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }) {
            BookScreen(
                onItemClick = { bookId ->
                    navController.navigate(Constants.BookScreen.Detail.name + "/$bookId")
                }
            )
        }
        composable(
            route = Constants.BookScreen.Detail.name + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("id") ?: 0
            DetailScreen(id = bookId) {
                onBackHandling(navController)
            }
        }
    }
}

fun onBackHandling(navController: NavHostController) {
    navController.popBackStack()
}