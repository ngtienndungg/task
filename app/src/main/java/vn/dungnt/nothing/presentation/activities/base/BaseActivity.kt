package vn.dungnt.nothing.presentation.activities.base

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import vn.dungnt.nothing.domain.entities.LanguageEntity
import vn.dungnt.nothing.utils.Constants
import vn.dungnt.nothing.utils.SharedPrefs
import vn.dungnt.nothing.utils.Utils
import vn.dungnt.nothing.utils.convertFromJson

open class BaseActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)

        val prefs = SharedPrefs.getString(Constants.PREFS_LANGUAGE_MODEL, "")
        val languageModel = if (prefs.isEmpty()) {
            LanguageEntity.getLanguageList()[0]
        } else {
            prefs.convertFromJson()

        }
        // Log.d("Utils", "attachBaseContext: ${languageModel.getLanguageCode()}")
        newBase?.let {
            Utils.updateLocale(it, languageModel.getLanguageCode())
            Log.d("Utils", "attachBaseContextLocale: ${it.resources.configuration.locales.get(0)}")
        }
    }
}