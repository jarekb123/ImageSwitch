package com.butajlo.imageswitch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ImageSwitchViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val imageSwitchViewModel by lazy { ImageSwitchViewModel() }


    @Before
    fun prepareImageSwitch() {
        imageSwitchViewModel.setCheckedImage(CHECKED_IMAGE_RES)
        imageSwitchViewModel.setUncheckedImage(UNCHECKED_IMAGE_RES)
    }

    @Test
    fun `on click should change image to checked`() {
        imageSwitchViewModel.onClick()

        assertEquals(CHECKED_IMAGE_RES, imageSwitchViewModel.imageRes.value)
    }

    @Test
    fun `on click should change state to checked`() {
        imageSwitchViewModel.onClick()
        assertEquals(true, imageSwitchViewModel.isChecked.value)
    }

    @Test
    fun `twice click should change image to unchecked`() {
        imageSwitchViewModel.onClick()
        imageSwitchViewModel.onClick()

        assertEquals(UNCHECKED_IMAGE_RES, imageSwitchViewModel.imageRes.value)
    }

    @Test
    fun `twice click should change state to unchecked`() {
        imageSwitchViewModel.onClick()
        imageSwitchViewModel.onClick()

        assertEquals(false, imageSwitchViewModel.isChecked.value)
    }

    @Test
    fun `setCheckedState should update state`() {
        imageSwitchViewModel.setCheckedState(false)
        assertEquals(false, imageSwitchViewModel.isChecked.value)
        imageSwitchViewModel.setCheckedState(true)
        assertEquals(true, imageSwitchViewModel.isChecked.value)
    }

    @Test
    fun `unchecked change image resource should update image if state == unchecked`() {
        val NEW_UNCHECKED_IMAGE_RES = 2

        imageSwitchViewModel.setCheckedState(false)
        imageSwitchViewModel.setUncheckedImage(NEW_UNCHECKED_IMAGE_RES)

        assertEquals(NEW_UNCHECKED_IMAGE_RES, imageSwitchViewModel.imageRes.value)
    }

    companion object {
        private const val CHECKED_IMAGE_RES = 0
        private const val UNCHECKED_IMAGE_RES = 1
    }
}