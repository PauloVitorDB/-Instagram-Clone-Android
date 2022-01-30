package co.pvitor.instagram.register.view

import androidx.fragment.app.Fragment

interface FragmentAttachListener {
    fun replaceFragment(fragment: Fragment)
    fun nextActivity()
    fun openGallery()
    fun openCamera()
}