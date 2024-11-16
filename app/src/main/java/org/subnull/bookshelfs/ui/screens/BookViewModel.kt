package org.subnull.bookshelfs.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import kotlinx.coroutines.launch
import okio.IOException
import org.subnull.bookshelfs.BookApplication
import org.subnull.bookshelfs.data.BookRepository
import org.subnull.bookshelfs.model.Books

sealed interface BookUiState {
    data class Success(val books: Books) : BookUiState
    data object Error : BookUiState
    data object Loading : BookUiState
}

class BookViewModel(
    private val bookRepository: BookRepository
): ViewModel() {
    var bookUiState: BookUiState by mutableStateOf(BookUiState.Loading)
        private set

    init {
       getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
            bookUiState = try {
                val res = bookRepository.getBooks("jazz+history")
                BookUiState.Success(res)
            } catch (e: IOException) {
                BookUiState.Error
            } catch (e: HttpException) {
                BookUiState.Error
            }
        }
    }

//    private fun get

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookApplication)
                val bookRepository = application.container.bookRepository
                BookViewModel(bookRepository = bookRepository)
            }
        }
    }
}