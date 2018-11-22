package com.butajlo.imageswitch

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.view_image_switch.view.*

class ImageSwitch : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(attrs)
    }

    private val viewModel by lazy { ImageSwitchViewModel() }
    private val imageObserver = Observer<Int> { setImage(it ?: 0) }

    private fun init(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ImageSwitch,
            0, 0
        ).apply {
            setCheckedImage(getResourceId(R.styleable.ImageSwitch_checkedImage, 0))
            setUncheckedImage(getResourceId(R.styleable.ImageSwitch_uncheckedImage, 0))
        }
        View.inflate(context, R.layout.view_image_switch, this)
        setOnClickListener(::onClick)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel.imageRes.observeForever(imageObserver)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewModel.imageRes.removeObserver(imageObserver)
    }

    fun setCheckedImage(@DrawableRes imageRes: Int) {
        viewModel.setCheckedImage(imageRes)
    }

    fun setUncheckedImage(@DrawableRes imageRes: Int) {
        viewModel.setUncheckedImage(imageRes)
    }

    private fun onClick(v: View) {
        viewModel.onClick()
    }

    private fun setImage(imageRes: Int) {
        iv_image_switch.setImageResource(imageRes)
    }

}