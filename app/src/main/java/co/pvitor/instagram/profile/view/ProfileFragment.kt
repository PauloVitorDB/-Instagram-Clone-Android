package co.pvitor.instagram.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.R
import co.pvitor.instagram.common.view.BottomSheetItem
import co.pvitor.instagram.common.view.ModalBottomSheetDialog
import co.pvitor.instagram.databinding.FragmentProfileBinding
import co.pvitor.instagram.databinding.ItemGridPhotoBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

         super.onViewCreated(view, savedInstanceState)
         binding = FragmentProfileBinding.bind(view)

        val rvPhotoGrid: RecyclerView = binding.recyclerViewPhotoGrid
        rvPhotoGrid.layoutManager = GridLayoutManager(requireContext(), 3)
        rvPhotoGrid.adapter = PhotoGridAdapter()

        val modalBottomSheetDialog = ModalBottomSheetDialog()

        modalBottomSheetDialog.addItems(
            BottomSheetItem(
                R.string.config,
                R.drawable.ic_baseline_settings_24
            )
        ) {
            val text = when(it.id) {
                R.string.add_perfil_photo -> "Peril foto"
                R.string.app_name -> "App name"
                else -> "not found"
            }
            modalBottomSheetDialog.dismiss()
        }
        modalBottomSheetDialog.show(childFragmentManager, "")
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

