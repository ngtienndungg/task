package vn.dungnt.nothing.presentation.composes.bottomNav.book

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import vn.dungnt.nothing.presentation.viewmodels.DetailViewModel

@Composable
fun DetailScreen(
    id: Int,
    vm: DetailViewModel = hiltViewModel<DetailViewModel>()
) {
    val uiState by vm.detailState.collectAsState()
    uiState.bookDetail?.let {
        BookItem(book = it)
    }
    LaunchedEffect(uiState) {
        vm.getBookDetail(id)
    }
}

@Composable
fun BookItem(book: BookDetailEntity) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

@Composable
fun DetailText(value: String) {
    Text(text = value, modifier = Modifier.padding(8.dp))
}