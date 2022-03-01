package co.pvitor.instagram.post.view

import android.graphics.Bitmap
import android.net.Uri
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.databinding.ItemGridPhotoBinding

class GalleryAdapter(
    private val selectedItemListener: ((Uri) -> Unit)?
): RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    var deviceGalleryPictureList: List<Uri> = mutableListOf()
    private lateinit var selectedUri: Uri

    inner class GalleryViewHolder(private val binding: ItemGridPhotoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUri: Uri) {

            val bitmap: Bitmap = binding.root.context.contentResolver.loadThumbnail(imageUri, Size(200, 200), null)
            binding.imageViewGridPhoto.setImageBitmap(bitmap)

            selectedUri = imageUri

            selectedItemListener?.let { listener ->
                binding.imageViewGridPhoto.setOnClickListener {
                    listener.invoke(imageUri)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = ItemGridPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val uri = deviceGalleryPictureList[position]

        holder.bind(uri)
    }

    override fun getItemCount(): Int = deviceGalleryPictureList.size

}