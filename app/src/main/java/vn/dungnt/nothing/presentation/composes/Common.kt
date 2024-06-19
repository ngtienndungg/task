package vn.dungnt.nothing.presentation.composes

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import vn.dungnt.nothing.R
import vn.dungnt.nothing.domain.entities.LanguageEntity
import vn.dungnt.nothing.presentation.theme.styleTextGray
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
    Log.d("ShowDialog", "ShowProgressDialog: Called")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(false) {},
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = colorResource(id = R.color.color_main),
            modifier = Modifier.height(30.dp)
        )
    }
}

@Composable
fun CustomDropDownMenu(
    onItemSelected: (LanguageEntity) -> Unit,
    itemList: List<LanguageEntity>,
    modifier: Modifier = Modifier,
    selectedItem: LanguageEntity,
    dropdownWidth: Dp = 200.dp,
    dropdownHeight: Dp = 32.dp
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(
            modifier = modifier
                .width(dropdownWidth)
                .height(dropdownHeight)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(
                    1.dp,
                    colorResource(id = R.color.color_border_and_line),
                    RoundedCornerShape(10.dp)
                )
                .clickable { expanded = true }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = selectedItem.icon), contentDescription = null)
            Text(
                text = selectedItem.name,
                style = styleTextGray,
                modifier = modifier
                    .padding(8.dp)
                    .weight(1f)
                    .wrapContentHeight(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = null
            )
        }


        DropdownMenu(
            modifier = modifier
                .width(dropdownWidth)
                .background(Color.White)
                .padding(vertical = 0.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            itemList.forEach { item ->
                DropdownMenuItem(
                    modifier = modifier.height(25.dp),
                    onClick = {
                        expanded = false
                        onItemSelected(item)
                    },
                    text = {
                        val isSelected = item == selectedItem
                        val textColor = if (isSelected) {
                            colorResource(id = R.color.color_main)
                        } else {
                            Color.Black
                        }
                        Text(text = item.name, color = textColor)
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = item.icon),
                            contentDescription = null
                        )
                    },
                )
            }
        }
    }
}