package desla.aos.eating.ui.home.search

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import desla.aos.eating.data.repositories.FilterRepository
import desla.aos.eating.data.repositories.HomeRepository
import desla.aos.eating.data.repositories.SearchRepository
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.ui.base.BaseViewModel

class SearchViewModel(
        private val repository: SearchRepository
) : BaseViewModel() {

    var searchText = ""

    val clicksListener = object : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH  -> {
                    v?.hideKeyboard()
                   // getLocationWithKeyword(searchText)
                }
                else -> return false
            }
            return true
        }

    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

}