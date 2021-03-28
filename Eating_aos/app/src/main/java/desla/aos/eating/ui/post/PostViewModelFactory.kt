package desla.aos.eating.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.data.repositories.MapRepository
import desla.aos.eating.data.repositories.PostRepository

@Suppress("UNCHECKED_CAST")
class PostViewModelFactory(
        val repository: PostRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repository) as T
    }

}