package co.pvitor.instagram.search.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.common.model.User
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.databinding.SearchItemBinding
import com.squareup.picasso.Picasso

class SearchAdapter(
    private val itemClick: (String) -> Unit
): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var userList: List<User> = mutableListOf()

    inner class SearchViewHolder(private val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {

            binding.apply {
                textViewSearchProfileUsername.text = user.name
                textViewProfileNickname.text = ""
                Picasso.with(binding.root.context).load(user.profilePhotoUrl).into(binding.civProfileImage)
            }

            binding.root.setOnClickListener {
                user.uuid?.let { it1 -> itemClick.invoke(it1) }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : SearchViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = userList.size
}

