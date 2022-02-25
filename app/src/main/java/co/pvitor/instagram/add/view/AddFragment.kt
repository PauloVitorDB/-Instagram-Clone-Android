package co.pvitor.instagram.add.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.viewpager2.widget.ViewPager2
import co.pvitor.instagram.R
import co.pvitor.instagram.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayout

class AddFragment: Fragment(R.layout.fragment_add)  {

    companion object {

        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA

        const val CAMERA_KEY = "camera_key"
        const val START_CAMERA_KEY = "startCamera"

        const val URI_SAVED_KEY = "uri_key"
        const val URI_KEY = "uri"
    }

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(URI_SAVED_KEY) { key, bundle ->

            val uri: Uri? = bundle.getParcelable<Uri>(URI_KEY)

            uri?.let {
                val intent = Intent(requireContext(), AddActivity::class.java)
                intent.putExtra(URI_KEY, uri)
            }

        }

        setupOnViewCreated()
    }

    private fun setupOnViewCreated() {

        val tabLayoutAdd: TabLayout = binding.tabLayoutAdd

        tabLayoutAdd.addOnTabSelectedListener( object: TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.id == R.string.camera) {
                    openCameraView()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

        })

        val viewPagerAdd: ViewPager2 = binding.viewpagerAdd
        val viewPagerAdapter = AddViewPagerAdapter(requireActivity())
        viewPagerAdd.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayoutAdd, viewPagerAdd) { tab, position ->
            val tabLayoutTextRes = viewPagerAdapter.tabs[position]
            tab.id = tabLayoutTextRes
            tab.text = getString(tabLayoutTextRes)
        }.attach()

    }

    private fun openCameraView() {
        if(allPermissionGranted()) {
            startCamera()
        } else {
            requirePermission.launch(REQUIRED_PERMISSION)
        }
    }
    private fun allPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), REQUIRED_PERMISSION) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        setFragmentResult(CAMERA_KEY, bundleOf(
            START_CAMERA_KEY to true
        ))
    }

    private val requirePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if(granted) {
            startCamera()
        } else {
            Snackbar.make(binding.root, getText(R.string.permission_camera_denied), Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}