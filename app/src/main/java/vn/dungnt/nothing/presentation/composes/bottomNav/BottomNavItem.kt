package vn.dungnt.nothing.presentation.composes.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import vn.dungnt.nothing.R
import vn.dungnt.nothing.application
import vn.dungnt.nothing.utils.Utils.getString

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    data object BookScreen : Destinations(
        route = "book_screen",
        title = getString(application!!, R.string.home),
        icon = Icons.Outlined.Home
    )

    data object AccountScreen : Destinations(
        route = "account_screen",
        title = getString(application!!, R.string.account),
        icon = Icons.Outlined.AccountCircle
    )
}