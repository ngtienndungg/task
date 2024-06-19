package vn.dungnt.nothing.presentation.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import vn.dungnt.nothing.domain.entities.LanguageEntity
import vn.dungnt.nothing.presentation.activities.base.BaseActivity
import vn.dungnt.nothing.presentation.composes.LoginScreen
import vn.dungnt.nothing.utils.Constants
import vn.dungnt.nothing.utils.SharedPrefs
import vn.dungnt.nothing.utils.convertFromJson

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val pref = SharedPrefs.getString(Constants.PREFS_LANGUAGE_MODEL, "")
            val languageModel = if (pref.isEmpty()) {
                LanguageEntity.getLanguageList()[0]
            } else {
                pref.convertFromJson()
            }

            LoginScreen(defaultLanguage = languageModel.name)
        }
    }
}