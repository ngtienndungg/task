package vn.dungnt.nothing.presentation.composes.book

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import vn.dungnt.nothing.presentation.composes.DetailScreen
import vn.dungnt.nothing.presentation.viewmodels.BookViewModel
import vn.dungnt.nothing.utils.Constants

fun NavGraphBuilder.bookGraph(
    navController: NavHostController,
    vm: BookViewModel
) {
    navigation(
        route = Constants.BookScreen.Start.name,
        startDestination = Constants.BookScreen.List.name
    ) {
        composable(route = Constants.BookScreen.List.name) {
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
            DetailScreen(id = bookId)
        }
    }
}

