package vn.dungnt.nothing.presentation.composes

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.dungnt.nothing.R
import vn.dungnt.nothing.domain.entities.UiState
import vn.dungnt.nothing.presentation.activities.LoginActivity
import vn.dungnt.nothing.presentation.activities.MainActivity
import vn.dungnt.nothing.presentation.theme.NothingTheme
import vn.dungnt.nothing.presentation.viewmodels.LoginViewModel
import vn.dungnt.nothing.utils.Constants
import vn.dungnt.nothing.utils.SharedPrefs

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    vm: LoginViewModel = hiltViewModel<LoginViewModel>()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val currentActivity = LocalContext.current as? LoginActivity
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    NothingTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    label = {
                        Text(text = stringResource(id = R.string.username))
                    }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    label = {
                        Text(text = stringResource(id = R.string.password))
                    }
                )
                LoginButton(username = username, password = password) { username, password ->
                    vm.login(username, password) { _, _ ->
                        SharedPrefs.saveBoolean(Constants.PREFS_LOGIN, true)
                        val intent = Intent(currentActivity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        currentActivity?.startActivity(
                            intent
                        )
                    }
                }
            }
        }

        val uiState by vm.uiState.collectAsState()
        when (uiState) {
            is UiState.Loading -> ShowProgressDialog()
            is UiState.Error -> {
                (uiState as UiState.Error).errorMessage?.takeIf { it.isNotEmpty() }?.let {
                    keyboardController?.hide()
                    CustomToast(message = it, modifier = modifier) {
                        vm.setUiState(UiState.None)
                    }

                }
            }

            else -> {}
        }
    }
}

@Composable
fun LoginButton(username: String, password: String, onLogin: (String, String) -> Unit) {
    OutlinedButton(
        onClick = { onLogin(username, password) }, modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.login))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}