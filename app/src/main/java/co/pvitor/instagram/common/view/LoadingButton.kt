package co.pvitor.instagram.common.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import co.pvitor.instagram.R
import co.pvitor.instagram.common.util.InputListener

class LoadingButton: FrameLayout {

    private lateinit var button: Button
    private lateinit var progressBar: ProgressBar
    private var text: String? = null
    private lateinit var typedArray: TypedArray

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {

        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.button_loading, this)

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0)

        text = typedArray.getString(R.styleable.LoadingButton_text)

        button = getChildAt(0) as Button
        button.isEnabled = typedArray.getBoolean(R.styleable.LoadingButton_enabled, false)
        button.text = text

        progressBar = getChildAt(1) as ProgressBar

        typedArray.recycle()

    }

    override fun setEnabled(enabled: Boolean) {
        button.isEnabled = enabled
    }

    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener {
            l?.onClick(it)
            InputListener.hideInputOnClick(it)
        }
    }

    fun showProgress(enabled: Boolean) {
        if(enabled) {
            button.isEnabled = false
            button.text = null
            progressBar.visibility = View.VISIBLE
        } else {
            button.isEnabled = true
            button.text = text
            progressBar.visibility = View.GONE
        }
    }


}