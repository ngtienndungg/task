package vn.dungnt.nothing.presentation.composes.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    data object BookScreen : Destinations(
        route = "book_screen",
        title = "Book",
        icon = Icons.Outlined.Home
    )

    data object Account : Destinations(
        route = "account_screen",
        title = "Account",
        icon = Icons.Outlined.AccountCircle
    )
}