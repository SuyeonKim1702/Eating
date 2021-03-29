package desla.aos.eating.ui.view.host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.data.repositories.MapRepository
import desla.aos.eating.data.repositories.PostRepository
import desla.aos.eating.data.repositories.ViewRepository

@Suppress("UNCHECKED_CAST")
class ViewHostModelFactory(
        val repository: ViewRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewHostViewModel(repository) as T
    }

}