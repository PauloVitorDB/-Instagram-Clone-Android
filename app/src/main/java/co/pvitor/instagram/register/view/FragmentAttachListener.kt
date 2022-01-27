package co.pvitor.instagram.register.view

import androidx.fragment.app.Fragment

interface FragmentAttachListener {
    fun nextStep(fragment: Fragment, email: String)
}