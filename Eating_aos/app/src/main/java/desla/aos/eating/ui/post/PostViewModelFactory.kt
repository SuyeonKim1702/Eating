package desla.aos.eating.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.data.repositories.MapRepository

@Suppress("UNCHECKED_CAST")
class MapViewModelFactory(
        val repository: MapRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapViewModel(repository) as T
    }

}