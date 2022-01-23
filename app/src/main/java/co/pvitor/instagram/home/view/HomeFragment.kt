package co.pvitor.instagram.home.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.R
import co.pvitor.instagram.common.view.BottomSheetItem
import co.pvitor.instagram.common.view.ModalBottomSheetDialog
import co.pvitor.instagram.databinding.FragmentHomeBinding
import co.pvitor.instagram.databinding.ItemPostBinding

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var modalBottomSheetDialog: ModalBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        modalBottomSheetDialog = ModalBottomSheetDialog()
        modalBottomSheetDialog.addItems(
            BottomSheetItem(
                R.string.stop_following,
                null
            )
        ) {
            when(it.id) {
                R.string.stop_following -> Log.d("BottomSheetHome", "Stop")
            }
            modalBottomSheetDialog.dismiss()
        }


        val recyclerViewPosts = binding.recyclerViewPosts
        recyclerViewPosts.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerViewPosts.adapter = PostAdapter(this, modalBottomSheetDialog)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}

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