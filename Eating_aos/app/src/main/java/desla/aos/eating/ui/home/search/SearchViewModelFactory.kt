package desla.aos.eating.ui.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.data.repositories.FilterRepository
import desla.aos.eating.data.repositories.SearchRepository
import desla.aos.eating.data.repositories.UserRepository

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
        private val repository: SearchRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }

}