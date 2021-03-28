package desla.aos.eating.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.data.repositories.SettingRepository
import desla.aos.eating.data.repositories.UserRepository

@Suppress("UNCHECKED_CAST")
class SettingViewModelFactory(
        private val repository: SettingRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingViewModel(repository) as T
    }

}