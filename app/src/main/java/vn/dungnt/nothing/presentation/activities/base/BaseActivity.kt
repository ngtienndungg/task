package vn.dungnt.nothing.presentation.activities.base

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.dungnt.nothing.data.models.EventType
import vn.dungnt.nothing.data.models.MessageEvent
import vn.dungnt.nothing.domain.entities.LanguageEntity
import vn.dungnt.nothing.presentation.activities.LoginActivity
import vn.dungnt.nothing.utils.Constants
import vn.dungnt.nothing.utils.SharedPrefs
import vn.dungnt.nothing.utils.Utils
import vn.dungnt.nothing.utils.convertFromJson


open class BaseActivity : AppCompatActivity() {

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

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messageEvent: MessageEvent) {
        if (messageEvent.eventType == EventType.CLEAR_DATA_GO_TO_LOGIN) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}