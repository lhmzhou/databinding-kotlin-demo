package com.lhmzhou.databindingDemo.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lhmzhou.databindingDemo.data.ShoeEntry
import com.lhmzhou.databindingDemo.data.ShoeRepository
import com.lhmzhou.databindingDemo.data.UIState
import com.lhmzhou.databindingDemo.utils.provideRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepo: ShoeRepository = provideRepository(application)

    val uiState = ObservableField<UIState>(UIState.LOADING)

    var shoeList: LiveData<List<ShoeEntry>>? = null
        get() {
            return field ?: mRepo.shoeList.also { field = it }
        }

    fun insertShoe(shoe: ShoeEntry) {
        mRepo.insertShoe(shoe)
    }

    fun deleteShoe(shoe: ShoeEntry) {
        mRepo.deleteShoe(shoe)
    }


}