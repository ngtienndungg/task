package vn.dungnt.nothing.presentation.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import vn.dungnt.nothing.R
import vn.dungnt.nothing.utils.Constants
import vn.dungnt.nothing.utils.Constants.TIME_DISPLAY_TOAST

@Composable
fun CustomToast(
    message: String,
    modifier: Modifier = Modifier,
    toastMode: Constants.ToastMode = Constants.ToastMode.ERROR,
    callBack: () -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(isVisible) {
        delay(TIME_DISPLAY_TOAST)
        isVisible = false
        callBack()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = if (toastMode == Constants.ToastMode.ERROR) {
                        colorResource(id = R.color.color_red)
                    } else {
                        colorResource(id = R.color.color_toast_success)
                    },
                    shape = RoundedCornerShape(4.dp)
                ),
        ) {
            Row(
                modifier = modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (toastMode == Constants.ToastMode.SUCCESS) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_toast_success),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                Text(
                    text = message,
                    color = Color(0xFFF8FEFF),
                    fontSize = 14.sp,
                    modifier = Modifier.wrapContentWidth()
                )
            }
        }
    }
}

@Composable
fun ShowProgressDialog() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(false) {},
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = colorResource(id = R.color.purple_200))
    }
}