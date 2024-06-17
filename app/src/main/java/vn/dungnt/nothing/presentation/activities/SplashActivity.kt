package vn.dungnt.nothing.presentation.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.dungnt.nothing.R
import vn.dungnt.nothing.presentation.activities.base.BaseActivity
import vn.dungnt.nothing.presentation.theme.NothingTheme
import vn.dungnt.nothing.presentation.theme.Pink80
import vn.dungnt.nothing.presentation.theme.Purple80
import vn.dungnt.nothing.utils.Constants
import vn.dungnt.nothing.utils.SharedPrefs

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen()
        }
        lifecycleScope.launch {
            delay(2000)
            if (SharedPrefs.getBoolean(Constants.PREFS_LOGIN, false)) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            finish()
        }
    }

    @Composable
    fun SplashScreen() {
        val systemUiController: SystemUiController = rememberSystemUiController()
        LaunchedEffect(Unit) {
            systemUiController.setStatusBarColor(color = Purple80)
        }
        NothingTheme(darkTheme = false) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.color_toast_success)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.flag_viet_nam),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}