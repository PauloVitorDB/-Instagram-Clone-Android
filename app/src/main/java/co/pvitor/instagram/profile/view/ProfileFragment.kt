package co.pvitor.instagram.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.R
import co.pvitor.instagram.databinding.FragmentProfileBinding
import co.pvitor.instagram.databinding.ItemGridPhotoBinding

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

