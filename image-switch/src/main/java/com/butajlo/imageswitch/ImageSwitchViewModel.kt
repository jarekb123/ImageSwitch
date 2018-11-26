package com.butajlo.imageswitch

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class ImageSwitchViewModel : ViewModel() {

    private val _isChecked = MutableLiveData<Boolean>()
    val isChecked: LiveData<Boolean>
        get() = _isChecked

    private val _currentImageRes = MutableLiveData<Int>()
    val imageRes: LiveData<Int>
        get() = _currentImageRes

    private val checkedImageRes = MutableLiveData<Int>()
    private val uncheckedImageRes = MutableLiveData<Int>()

    init {
        setCheckedState(false)
    }

    fun onClick() {
        _isChecked.value?.apply {
            setCheckedState(!this)
        }
    }

    fun setCheckedImage(@DrawableRes imageRes: Int) {
        checkedImageRes.value = imageRes
        updateImage()
    }

    fun setUncheckedImage(@DrawableRes imageRes: Int) {
        uncheckedImageRes.value = imageRes
        updateImage()
    }

    fun setCheckedState(isChecked: Boolean) {
        _isChecked.value = isChecked
        updateImage()
    }

    private fun updateImage() {
        _currentImageRes.value = if (_isChecked.value ?: return) {
            checkedImageRes.value
        } else {
            uncheckedImageRes.value
        }
    }
}