package co.pvitor.instagram.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.databinding.ItemPostBinding

class PostAdapter(
): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    lateinit var postList: List<Post>

    class PostViewHolder(private val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.imageViewPostPhoto.setImageURI(post.uri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = postList.size

}