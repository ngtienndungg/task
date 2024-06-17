package vn.dungnt.nothing.presentation.composes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.dungnt.nothing.presentation.viewmodels.AccountViewModel

@Composable
fun AccountScreen(vm: AccountViewModel = hiltViewModel<AccountViewModel>()) {

}

@Composable
fun AccountInformation(username: String, email: String, firstName: String, lastName: String) {
    Column {
        Text(text = "Name: $lastName $firstName")
        Spacer(modifier = Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(text = "Username: $username")
            Text(text = "Email: $email")
        }
    }
}