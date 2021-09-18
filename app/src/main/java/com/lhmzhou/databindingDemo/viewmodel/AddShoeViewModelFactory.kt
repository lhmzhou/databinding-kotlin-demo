package com.lhmzhou.databindingDemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lhmzhou.databindingDemo.data.ShoeEntry
import com.lhmzhou.databindingDemo.data.ShoeRepository

class AddShoeViewModelFactory(private val mRepo: ShoeRepository, private val mChosenShoe: ShoeEntry?) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddShoeViewModel(mRepo, mChosenShoe) as T
    }
}