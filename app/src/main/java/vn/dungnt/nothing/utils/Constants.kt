package vn.dungnt.nothing.utils

import androidx.annotation.StringRes
import vn.dungnt.nothing.R

object Constants {

    const val PREFS_LANGUAGE_MODEL = "PREFS_LANGUAGE_MODEL"
    const val PREFS_LOGIN = "PREFS_LOGIN"
    const val PREFS_ACCESS_TOKEN = "PREFS_ACCESS_TOKEN"

    const val PRICE_PER_CUPCAKE = 2.00

    const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

    enum class ToastMode {
        ERROR, SUCCESS
    }

    enum class BookScreen(@StringRes val title: Int) {
        Start(title = R.string.app_name),
        Detail(title = R.string.detail),
        List(title = R.string.list)
    }

    const val TIME_DISPLAY_TOAST = 3000L
}