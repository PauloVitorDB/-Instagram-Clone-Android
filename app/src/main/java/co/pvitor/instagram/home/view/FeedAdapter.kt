package co.pvitor.instagram.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.R
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.view.BottomSheetItem
import co.pvitor.instagram.common.view.ModalBottomSheetDialog
import co.pvitor.instagram.databinding.ItemPostBinding

class FeedAdapter(
    context: Context
): RecyclerView.Adapter<FeedAdapter.PostViewHolder>() {

    var supportFragmentManager = (context as AppCompatActivity).supportFragmentManager
    var postList: List<Post> = mutableListOf()

    inner class PostViewHolder(
        private val binding: ItemPostBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {

            binding.apply {

                imageViewPostPhoto.setImageURI(post.uri)

                textViewUsername.text = post.publisher.name
                textViewCaption.text = post.caption
                civProfilePhoto.setImageURI(post.publisher.profilePhoto)

                postSettings.setOnClickListener {

                    val modalBottomSheetDialog = ModalBottomSheetDialog()

                    modalBottomSheetDialog.addItems(BottomSheetItem(R.string.stop_following, null)) {
                        when(it.id) {
                            R.string.stop_following -> stopFollowing(post.publisher)
                        }
                        modalBottomSheetDialog.dismiss()
                    }

                    modalBottomSheetDialog.show(supportFragmentManager, null)
                }
            }

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

    private fun stopFollowing(userAuth: UserAuth) {

    }
}