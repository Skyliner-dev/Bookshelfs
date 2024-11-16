package org.subnull.bookshelfs.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.subnull.bookshelfs.model.Book
import org.subnull.bookshelfs.model.Books

@Composable
fun BookshelfMainScreen(
    bookUiState: BookUiState,
    contentPadding: PaddingValues,
    onError: () -> Unit
) {
    when(bookUiState) {
        is BookUiState.Success -> SuccessScreen(books = bookUiState.books,contentPadding)
        is BookUiState.Loading -> LoadingScreen()
        is BookUiState.Error -> ErrorScreen(onError)
    }
}

@Composable
fun SuccessScreen(
    books: Books,
    contentPadding: PaddingValues
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(books.items) {
            book -> BookCard(book = book)
        }
    }
}

@Composable
fun BookCard(book:Book) {
    ElevatedCard(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        val thumbnailImageLinks = book.volumeInfo.imageLinks

        Column {
            if (thumbnailImageLinks != null ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(thumbnailImageLinks.thumbnail.replace("http","https"))
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().size(250.dp).padding(16.dp)
                )
            } else {
                Text("null")
            }
            Text(book.volumeInfo.title, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun ErrorScreen(
    onError:() -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Error")
            ElevatedButton(onClick = onError) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {
        Text("Loading...")
    }
}
