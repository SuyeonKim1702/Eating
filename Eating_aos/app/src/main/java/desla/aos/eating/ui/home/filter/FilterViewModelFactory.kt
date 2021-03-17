package desla.aos.eating.ui.home.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.data.repositories.FilterRepository
import desla.aos.eating.data.repositories.UserRepository

@Suppress("UNCHECKED_CAST")
class FilterViewModelFactory(
        private val repository: FilterRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FilterViewModel(repository) as T
    }

}