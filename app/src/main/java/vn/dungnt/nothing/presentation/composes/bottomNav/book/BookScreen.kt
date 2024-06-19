package vn.dungnt.nothing.presentation.composes.bottomNav.book

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import vn.dungnt.nothing.domain.entities.BookEntity
import vn.dungnt.nothing.domain.entities.UiState
import vn.dungnt.nothing.presentation.composes.CustomToast
import vn.dungnt.nothing.presentation.composes.ShowProgressDialog
import vn.dungnt.nothing.presentation.viewmodels.BookViewModel

@Composable
fun BookScreen(
    modifier: Modifier = Modifier,
    vm: BookViewModel = hiltViewModel<BookViewModel>(),
    onItemClick: (id: Int) -> Unit
) {
    val bookListState by vm.bookListState.collectAsState()
    BookListColumn(bookList = bookListState, modifier = modifier.fillMaxWidth(), onClick = { id ->
        onItemClick(id)
    })

    val uiState by vm.uiState.collectAsState()
    when (uiState) {
        is UiState.Loading -> {
            Log.d("BookScreen", "BookScreen: Loading")
            ShowProgressDialog()
        }

        is UiState.Error -> {
            (uiState as UiState.Error).errorMessage?.takeIf { it.isNotEmpty() }?.let {
                CustomToast(message = it, modifier = modifier) {
                    vm.setUiState(UiState.None)
                }
            }
        }

        else -> {}
    }
}

@Composable
fun BookItem(
    id: Int,
    title: String,
    subtitle: String,
    category: String,
    imageUrl: String,
    onItemClick: (id: Int) -> Unit
) {
    Row(modifier = Modifier.clickable {
        onItemClick(id)
    }, horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(model = imageUrl, contentDescription = "", modifier = Modifier.height(80.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = title)
            Text(text = subtitle)
            Text(text = category)
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun BookListColumn(
    modifier: Modifier = Modifier,
    bookList: List<BookEntity>,
    onClick: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(bookList.size) { item ->
            BookItem(
                id = bookList[item].id ?: 0,
                title = bookList[item].title ?: "",
                subtitle = bookList[item].subtitle ?: "",
                category = bookList[item].category ?: "",
                imageUrl = bookList[item].avatar ?: ""
            ) {
                bookList[item].id?.let { it1 -> onClick(it1) }
            }
        }
    }
}