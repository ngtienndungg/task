package vn.dungnt.nothing.presentation.composes.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val bookState by vm.bookState.collectAsState()
    bookState.bookList?.let {
        BookListColumn(bookList = it, onClick = { id ->
            onItemClick(id)
        })
    }

    val uiState by vm.uiState.collectAsState()
    when (uiState) {
        is UiState.Loading -> ShowProgressDialog()
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
    Column(modifier = Modifier.clickable {
        onItemClick(id)
    }, verticalArrangement = Arrangement.Center) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(model = imageUrl, contentDescription = "")
            Text(text = title)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
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
        items(bookList.size) { id ->
            BookItem(
                id = bookList[id].id ?: 0,
                title = bookList[id].title ?: "",
                subtitle = bookList[id].subtitle ?: "",
                category = bookList[id].category ?: "",
                imageUrl = bookList[id].avatar ?: ""
            ) {
                onClick(id)
            }
        }
    }
}