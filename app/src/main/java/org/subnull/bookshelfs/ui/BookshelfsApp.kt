package org.subnull.bookshelfs.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.subnull.bookshelfs.ui.screens.BookViewModel
import org.subnull.bookshelfs.ui.screens.BookshelfMainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShelfApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                   Text("Bookshelf App")
                }
            )
        }
    ) {
        innerPadding ->
        Surface(modifier = Modifier.fillMaxSize()) {
            val bookViewModel:BookViewModel = viewModel(factory = BookViewModel.Factory)
            BookshelfMainScreen(
                contentPadding = innerPadding,
                bookUiState = bookViewModel.bookUiState,
                onError = {})
        }

    }
}