package co.pvitor.instagram.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import co.pvitor.instagram.R
import co.pvitor.instagram.databinding.ModalBottomSheetListBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheetDialog: BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetListBinding? = null
    private val binding get() = _binding!!

    private var bottomSheetItemList = mutableListOf<BottomSheetItem>()
    private lateinit var itemListener: View.OnClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.modal_bottom_sheet_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _binding = ModalBottomSheetListBinding.bind(view)

        for(bottomSheetItem in bottomSheetItemList) {

            val textView = TextView(
                context,
                null,
                0,
                R.style.Theme_Instagram_BottomSheetTextView
            )

            textView.apply {
                id = bottomSheetItem.textViewRes.toInt()
                text = context?.getText(bottomSheetItem.textViewRes)
                setOnClickListener(itemListener)
            }

            if(bottomSheetItem.drawableIcon != null) {
                val drawableIcon = ContextCompat.getDrawable(requireContext(), bottomSheetItem.drawableIcon)
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableIcon, null, null, null)
                textView.compoundDrawablePadding = 10
            }

            binding.root.addView(textView, layoutParams())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun addItems(vararg bottomSheetItem: BottomSheetItem, listener: View.OnClickListener) {

        for(bottomItem in bottomSheetItem) {
            bottomSheetItemList.add(bottomItem)
        }

        itemListener = listener
    }

    override fun getTheme(): Int = R.style.BaseBottomSheetDialogTheme

    private fun layoutParams(): LinearLayout.LayoutParams {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        layoutParams.setMargins(
            24,
            32,
            24,
            32
        )

        return layoutParams
    }

}

data class BottomSheetItem(
    @StringRes val textViewRes: Int,
    @DrawableRes val drawableIcon: Int?
)