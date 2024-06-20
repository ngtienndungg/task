package vn.dungnt.nothing.presentation.composes.bottomNav

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
import org.greenrobot.eventbus.EventBus
import vn.dungnt.nothing.R
import vn.dungnt.nothing.data.models.EventType
import vn.dungnt.nothing.data.models.MessageEvent
import vn.dungnt.nothing.domain.entities.LanguageEntity
import vn.dungnt.nothing.domain.entities.UiState
import vn.dungnt.nothing.domain.entities.UserEntity
import vn.dungnt.nothing.presentation.activities.MainActivity
import vn.dungnt.nothing.presentation.composes.CustomDropDownMenu
import vn.dungnt.nothing.presentation.composes.CustomToast
import vn.dungnt.nothing.presentation.composes.ShowProgressDialog
import vn.dungnt.nothing.presentation.viewmodels.AccountViewModel
import vn.dungnt.nothing.utils.Constants
import vn.dungnt.nothing.utils.SharedPrefs
import vn.dungnt.nothing.utils.Utils.updateLocale
import vn.dungnt.nothing.utils.convertToJson

@Composable
fun AccountScreen(
    vm: AccountViewModel = hiltViewModel<AccountViewModel>(),
    defaultLanguage: String
) {

    val userState by vm.userState.collectAsState()
    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current
    val currentActivity = context as? MainActivity


    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
        LanguageChoosing(Modifier, defaultLanguage) {
            SharedPrefs.saveString(Constants.PREFS_LANGUAGE_MODEL, it.convertToJson())
            updateLocale(context, defaultLanguage)
            currentActivity?.recreate()
        }
        AccountInformation(userState)
        LogOutButton(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            vm.logout()
        }
    }

    LaunchedEffect(userState) {
        vm.getCurrentUser(SharedPrefs.getString(Constants.PREFS_USERNAME, ""))
    }

    when (uiState) {
        is UiState.Loading -> ShowProgressDialog()
        is UiState.Error -> {
            (uiState as UiState.Error).errorMessage?.takeIf { it.isNotEmpty() }?.let {
                CustomToast(message = it) {
                    vm.setUiState(UiState.None)
                }

            }
        }

        else -> {}
    }
}

@Composable
fun AccountInformation(user: UserEntity) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.fullName) + ": ${user.lastName} ${user.firstName}",
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.username) + ": ${user.username}",
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
