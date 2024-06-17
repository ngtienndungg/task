package vn.dungnt.nothing.presentation.activities.base

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import vn.dungnt.nothing.domain.entities.LanguageEntity
import vn.dungnt.nothing.utils.Constants
import vn.dungnt.nothing.utils.SharedPrefs
import vn.dungnt.nothing.utils.Utils
import vn.dungnt.nothing.utils.convertFromJson

open class BaseActivity: ComponentActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)

        val prefs = SharedPrefs.getString(Constants.PREFS_LANGUAGE_MODEL, "")
        val languageModel = if (prefs.isEmpty()) {
            LanguageEntity.getLanguageList()[0]
        } else {
            prefs.convertFromJson()
        }
        newBase?.let { Utils.updateLocale(it, languageModel.getLanguageCode()) }
    }
}