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

    private val stateListeners by lazy { mutableSetOf<OnStateUpdatedListener>() }

    private val imageObserver = Observer<Int> { setImage(it ?: 0) }
    private val stateObserver = Observer<Boolean> { stateUpdated(it ?: false) }

    private fun init(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ImageSwitch,
            0, 0
        ).apply {
            if(!hasValue(R.styleable.ImageSwitch_uncheckedImage) || !hasValue(R.styleable.ImageSwitch_checkedImage)) {
                throw RuntimeException("ImageSwitch needs 'custom:uncheckedImage' and 'custom:checkedImage' attributes set")
            }
            setCheckedImage(getResourceId(R.styleable.ImageSwitch_checkedImage, 0))
            setUncheckedImage(getResourceId(R.styleable.ImageSwitch_uncheckedImage, 0))
        }
        View.inflate(context, R.layout.view_image_switch, this)
        setOnClickListener(::onClick)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel.imageRes.observeForever(imageObserver)
        viewModel.isChecked.observeForever(stateObserver)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewModel.imageRes.removeObserver(imageObserver)
        viewModel.isChecked.removeObserver(stateObserver)
        stateListeners.clear()
    }

    fun setCheckedImage(@DrawableRes imageRes: Int) {
        viewModel.setCheckedImage(imageRes)
    }

    fun setUncheckedImage(@DrawableRes imageRes: Int) {
        viewModel.setUncheckedImage(imageRes)
    }

    fun addOnStateUpdateListener(listener: OnStateUpdatedListener) {
        stateListeners.add(listener)
    }

    private fun onClick(v: View) {
        viewModel.onClick()
    }

    private fun setImage(imageRes: Int) {
        iv_image_switch.setImageResource(imageRes)
    }

    private fun stateUpdated(isChecked: Boolean) {
        stateListeners.forEach { it.stateUpdated(isChecked) }
    }

    interface OnStateUpdatedListener {
        fun stateUpdated(isChecked: Boolean)
    }

}