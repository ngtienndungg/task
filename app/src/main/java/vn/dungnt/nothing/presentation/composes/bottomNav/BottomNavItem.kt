package vn.dungnt.nothing.presentation.composes.bottomNav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import vn.dungnt.nothing.R

sealed class Destinations(
    val route: String,
    @StringRes val titleId: Int,
    val icon: ImageVector? = null,
) {
    data object BookScreen : Destinations(
        route = "book_screen",
        titleId = R.string.home,
        icon = Icons.Outlined.Home
    )

    data object AccountScreen : Destinations(
        route = "account_screen",
        titleId = R.string.account,
        icon = Icons.Outlined.AccountCircle
    )
}