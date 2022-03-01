package co.pvitor.instagram.search.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.databinding.SearchItemBinding

class SearchAdapter(
    private val itemClick: (String) -> Unit
): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var userList: List<UserAuth> = mutableListOf()

    inner class SearchViewHolder(private val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserAuth) {

            binding.apply {
                textViewSearchProfileUsername.text = user.name
                textViewProfileNickname.text = ""
                civProfileImage.setImageURI(user.profilePhoto)
            }

            binding.root.setOnClickListener {
                itemClick.invoke(user.uuid)
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

