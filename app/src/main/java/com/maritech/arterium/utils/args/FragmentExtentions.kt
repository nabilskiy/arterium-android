package com.maritech.arterium.utils.args

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T = this.apply {
    arguments = Bundle().apply(argsBuilder)
}

fun <T : Fragment> T.toast(message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}

fun <T : Fragment> T.toast(@StringRes idString: Int) = toast(getString(idString))

fun <T : Fragment> T.replaceFragment(containerViewId: Int, fragment: Fragment, tag: String, isAddToStack: Boolean = false) {
    val transaction = childFragmentManager
        .beginTransaction()
        .replace(containerViewId, fragment, tag)

    if(isAddToStack) transaction.addToBackStack(null)

    transaction.commit()
}