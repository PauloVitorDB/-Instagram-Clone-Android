package co.pvitor.instagram.common.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard() {

    val view: View? = currentFocus

    view?.let {

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

    }
}

fun AppCompatActivity.onAnimationEnd(listener: () -> Unit): AnimatorListenerAdapter {
    return object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            listener.invoke()
        }
    }
}

fun AppCompatActivity.replaceFragment(@IdRes fragment_id: Int, fragment: Fragment, fragmentTag: String?) {

    val isFragmentAttached: Boolean = supportFragmentManager.findFragmentById(fragment_id) != null

    val transaction = supportFragmentManager.beginTransaction()

    if(isFragmentAttached) {
        transaction.apply {
            replace(fragment_id, fragment, fragmentTag)
            addToBackStack(null)
        }
    } else {
        transaction.add(fragment_id, fragment, fragmentTag)
    }

    transaction.commit()
}

