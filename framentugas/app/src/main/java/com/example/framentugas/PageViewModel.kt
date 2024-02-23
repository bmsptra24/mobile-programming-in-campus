package com.example.framentugas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PageViewModel : ViewModel() {
    /**
     * Live Data Instance
     */
    private val mName = MutableLiveData<String>()
    fun setName(name: String) {
        mName.value = name
    }

    val name: LiveData<String>
        get() = mName
}