package co.pvitor.instagram.common.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import co.pvitor.instagram.databinding.DialogCustomBinding

class CustomDialog(context: Context): Dialog(context) {

    private lateinit var binding: DialogCustomBinding
    private lateinit var textButtonsArray: Array<TextView>
    private var textTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DialogCustomBinding.inflate(layoutInflater)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)

    }

    override fun setTitle(title: CharSequence?) {
        textTitle = title.toString()
    }

    fun addTextButton(vararg textButtons: Int, listener: View.OnClickListener) {

         textButtonsArray = Array(textButtons.size) {
            TextView(context)
         }

        textButtons.forEachIndexed { index, titleId ->
            textButtonsArray[index].apply {
                id = titleId
                text = context.getText(titleId)
                setOnClickListener(listener)
            }
        }

    }

    override fun show() {

        super.show()
        textTitle?.let {
            binding.textViewTitle.text = it
        }

        for(textView in textButtonsArray) {
            binding.root.addView(textView, itemLayoutParams())
        }

    }

    private fun itemLayoutParams (): LinearLayout.LayoutParams {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(
            30,
            50,
            30,
            50
        )
        return layoutParams
    }


}