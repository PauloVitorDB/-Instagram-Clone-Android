package co.pvitor.instagram.profile.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.R
import co.pvitor.instagram.common.view.BottomSheetItem
import co.pvitor.instagram.common.view.ModalBottomSheetDialog
import co.pvitor.instagram.databinding.FragmentProfileBinding
import co.pvitor.instagram.databinding.ItemGridPhotoBinding
import com.google.android.material.tabs.TabLayout

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var modalBottomSheetDialog: ModalBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        val rvPhotoGrid: RecyclerView = binding.recyclerViewPhotoGrid
        rvPhotoGrid.layoutManager = GridLayoutManager(requireContext(), 3)
        rvPhotoGrid.adapter = PhotoGridAdapter()

        modalBottomSheetDialog = ModalBottomSheetDialog()
        modalBottomSheetDialog.addItems(
            BottomSheetItem(
                R.string.config,
                R.drawable.ic_baseline_settings_24
            )
        ) {

            when(it.id) {
                R.string.config -> Log.d("BottomSheet", "config")
            }

            modalBottomSheetDialog.dismiss()
        }

        val tabLayout: TabLayout = binding.tabLayoutProfileGrid

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabId = tab?.id
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabId = tab?.id
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val tabId = tab?.id
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_item_config -> {
                modalBottomSheetDialog.show(childFragmentManager, "")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

class PhotoGridAdapter(

): RecyclerView.Adapter<PhotoGridAdapter.PhotoItemViewHolder>() {

    class PhotoItemViewHolder(private val binding: ItemGridPhotoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Int) {
            binding.imageViewGridPhoto.setImageResource(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        val binding = ItemGridPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        holder.bind(R.drawable.ic_insta_add)
    }

    override fun getItemCount(): Int = 24

}

