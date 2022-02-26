package co.pvitor.instagram.post.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.pvitor.instagram.R


class AddViewPagerAdapter(
    private val fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {

    val tabs = arrayOf(
        R.string.camera,
        R.string.gallery
    )

    override fun createFragment(position: Int) : Fragment {
        return when(tabs[position]) {
            R.string.gallery -> GalleryFragment()
            R.string.camera -> CameraFragment()
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = tabs.size


}

