package desla.aos.eating.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.data.repositories.LoginRepository
import desla.aos.eating.data.repositories.MapRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
        val repository: LoginRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }

}