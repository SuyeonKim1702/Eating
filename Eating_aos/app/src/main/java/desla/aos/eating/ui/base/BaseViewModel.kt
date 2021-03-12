package desla.aos.eating.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    val _message = MutableLiveData<String>()
    val message : LiveData<String>
        get() = _message


    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}