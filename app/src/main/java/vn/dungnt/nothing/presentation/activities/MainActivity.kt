package vn.dungnt.nothing.presentation.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import vn.dungnt.nothing.presentation.activities.base.BaseActivity
import vn.dungnt.nothing.presentation.composes.MainScreen
import vn.dungnt.nothing.presentation.composes.bottomNav.BottomNavBar
import vn.dungnt.nothing.presentation.composes.bottomNav.NavigationGraph

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}