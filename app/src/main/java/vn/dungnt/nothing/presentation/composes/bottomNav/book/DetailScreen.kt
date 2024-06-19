package vn.dungnt.nothing.presentation.composes.bottomNav.book

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import vn.dungnt.nothing.domain.entities.BookDetailEntity
import vn.dungnt.nothing.domain.entities.UiState
import vn.dungnt.nothing.presentation.composes.CustomToast
import vn.dungnt.nothing.presentation.composes.ShowProgressDialog
import vn.dungnt.nothing.presentation.viewmodels.DetailViewModel

@Composable
fun DetailScreen(
    id: Int,
    vm: DetailViewModel = hiltViewModel<DetailViewModel>(),
    onBackClick: () -> Unit
) {

    val detailState by vm.detailState.collectAsState()
    val uiState by vm.uiState.collectAsState()

    LaunchedEffect(Unit) {
        vm.getBookDetail(id)
    }

    when (uiState) {
        is UiState.Loading -> {
            Log.d("DetailScreen", "DetailScreen: Loading")
            ShowProgressDialog()
        }

        is UiState.Error -> {
            (uiState as UiState.Error).errorMessage?.takeIf { it.isNotEmpty() }?.let {
                CustomToast(message = it) {
                    vm.setUiState(UiState.None)
                }
            }
        }

        else -> {}
    }

    BookItem(book = detailState) {
        onBackClick()
    }


}

@Composable
fun BookItem(book: BookDetailEntity, onBackClick: () -> Unit) {
    Scaffold(topBar = {
        CustomTopAppBar(onBackClick = onBackClick)
    }) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AsyncImage(model = book.avatar, contentDescription = "")
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                book.id?.let { DetailText(value = it.toString()) }
                book.title?.let { DetailText(value = it) }
                book.subtitle?.let { DetailText(value = it) }
                book.category?.let { DetailText(value = it) }
            }

            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                book.author?.let { DetailText(value = it) }
                book.publishedYear?.let { DetailText(value = it.toString()) }
                book.isbn?.let { DetailText(value = it) }
            }
            book.summary?.let { Text(text = it) }
        }
    }

}

@Composable
fun BackIcon(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "",
        modifier = modifier
            .height(40.dp)
            .clickable {
                onClick()
            },
        tint = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun CustomTopAppBar(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    BackIcon(modifier = modifier) {
        onBackClick()
    }
}

@Composable
fun DetailText(value: String) {
    Text(text = value, modifier = Modifier.padding(8.dp))
}