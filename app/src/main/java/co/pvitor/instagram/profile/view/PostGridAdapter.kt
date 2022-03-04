package co.pvitor.instagram.profile.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.databinding.ItemGridPhotoBinding
import com.squareup.picasso.Picasso

class PostGridAdapter: RecyclerView.Adapter<PostGridAdapter.PhotoItemViewHolder>() {

    var posts: List<Post> = mutableListOf()

    class PhotoItemViewHolder(private val binding: ItemGridPhotoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            Picasso.with(binding.root.context).load(post.uri).into(binding.imageViewGridPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        val binding = ItemGridPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = posts.size

}