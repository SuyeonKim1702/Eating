package desla.aos.eating.ui.user.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.data.repositories.UserRepository

@Suppress("UNCHECKED_CAST")
class MyListViewModelFactory(
        private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyListViewModel(repository) as T
    }

}