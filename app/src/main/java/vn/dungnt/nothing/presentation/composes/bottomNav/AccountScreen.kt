package vn.dungnt.nothing.presentation.composes

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.dungnt.nothing.R
import vn.dungnt.nothing.domain.entities.LanguageEntity
import vn.dungnt.nothing.domain.entities.UserEntity
import vn.dungnt.nothing.presentation.activities.LoginActivity
import vn.dungnt.nothing.presentation.activities.MainActivity
import vn.dungnt.nothing.presentation.viewmodels.AccountViewModel
import vn.dungnt.nothing.utils.Constants
import vn.dungnt.nothing.utils.SharedPrefs
import vn.dungnt.nothing.utils.Utils.updateLocale
import vn.dungnt.nothing.utils.convertFromJson
import vn.dungnt.nothing.utils.convertToJson

@Composable
fun AccountScreen(vm: AccountViewModel = hiltViewModel<AccountViewModel>()) {

    val userState by vm.userState.collectAsState()
    val currentActivity = LocalContext.current as? MainActivity
    val context = LocalContext.current

    val pref = SharedPrefs.getString(Constants.PREFS_LANGUAGE_MODEL, "")
    val languageModel = if (pref.isEmpty()) {
        LanguageEntity.getLanguageList()[0]
    } else {
        pref.convertFromJson()
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
        LanguageChoosing(Modifier, languageModel.name) {
            SharedPrefs.saveString(Constants.PREFS_LANGUAGE_MODEL, it.convertToJson())
            updateLocale(context, it.getLanguageCode())
            currentActivity?.recreate()
        }
        AccountInformation(userState)
        LogOutButton(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            vm.logout()
            currentActivity?.startActivity(Intent(currentActivity, LoginActivity::class.java))
            currentActivity?.finish()
        }
    }

    LaunchedEffect(userState) {
        vm.getCurrentUser(SharedPrefs.getString(Constants.PREFS_USERNAME, ""))
    }
}

@Composable
fun AccountInformation(user: UserEntity) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Name: ${user.lastName} ${user.firstName}",
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Username: ${user.username}",
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Email: ${user.email}",
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun LogOutButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(onClick = {
        onClick()
    }, modifier = modifier) {
        Text(text = stringResource(id = R.string.logout))
    }
}

@Composable
fun LanguageChoosing(
    modifier: Modifier,
    defaultLanguage: String,
    onItemSelected: (LanguageEntity) -> Unit
) {
    var selectedLanguage by remember { mutableStateOf(defaultLanguage) }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        CustomDropDownMenu(
            onItemSelected = {
                onItemSelected(it)
                selectedLanguage = it.name
            },
            itemList = LanguageEntity.getLanguageList(),
            modifier = modifier.align(alignment = Alignment.End),
            selectedItem = LanguageEntity.getLanguageList().find { it.name == selectedLanguage }
                ?: LanguageEntity.getLanguageList()[0],
            dropdownWidth = 129.dp,
            dropdownHeight = 32.dp
        )
    }
}
