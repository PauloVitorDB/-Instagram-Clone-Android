package co.pvitor.instagram.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.R
import co.pvitor.instagram.common.view.ModalBottomSheetDialog
import co.pvitor.instagram.databinding.ItemPostBinding

class PostAdapter(
    private val context: Fragment,
    private val modalBottomSheetDialog: ModalBottomSheetDialog
): RecyclerView.Adapter<PostAdapter.PostItemViewHolder>() {

    class PostItemViewHolder(
        private val binding: ItemPostBinding,
        private val onClick: (() -> Unit)?
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(@DrawableRes drawable: Int) {
            binding.imageViewPostPhoto.setImageResource(R.drawable.insta_grid_photo)

            binding.postSettings.setOnClickListener{
                onClick?.invoke()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostItemViewHolder(binding) {
            modalBottomSheetDialog.show(context.childFragmentManager, "")
        }
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        holder.bind(R.drawable.ic_insta_add)
    }

    override fun getItemCount(): Int = 20

}