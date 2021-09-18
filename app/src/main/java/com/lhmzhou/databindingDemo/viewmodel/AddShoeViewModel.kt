package com.lhmzhou.databindingDemo.viewmodel

import androidx.lifecycle.ViewModel
import com.lhmzhou.databindingDemo.data.ShoeEntry
import com.lhmzhou.databindingDemo.data.ShoeRepository

class AddShoeViewModel(private val mRepo: ShoeRepository, private val chosenShoe: ShoeEntry?) : ViewModel() {

    val shoeBeingModified: ShoeEntry

    private var mIsEdit: Boolean = false

    init {
        if (chosenShoe != null) {
            //This is edit case
            shoeBeingModified = chosenShoe.copy()
            mIsEdit = true
        } else {
            /*This is for adding a new shoe. We initialize a ShoeEntry with default or null values
            This is because two-way databinding in the AddShoeFragment is designed to
            register changes automatically, but it will need a shoe object to register those changes.*/
            shoeBeingModified = emptyshoe
            mIsEdit = false
        }
    }

    private fun insertShoe(shoe: ShoeEntry) {
        mRepo.insertShoe(shoe)
    }

    private fun updateShoe(shoe: ShoeEntry) {
        mRepo.updateShoe(shoe)
    }

    fun saveShoe() {
        if (!mIsEdit) {
            insertShoe(shoeBeingModified)
        } else {
            updateShoe(shoeBeingModified)
        }
    }

    private val emptyShoe: ShoeEntry
        get() {
            val categories = mutableMapOf(
                WORKOUT to false, EVERYDAY to false,
                RECYCLABLE to false, PLUSH to false, BALLROOM to false, CASUAL to false
            )
            return ShoeEntry(shoeName = "", categories = categories)
        }


   var isChanged : Boolean = false
        get() = if(mIsEdit) shoeBeingModified != chosenShoe
                    else shoeBeingModified != emptyShoe
        private set

    companion object {
        const val WORKOUT = "Workout"
        const val EVERYDAY = "Everyday"
        const val RECYCLABLE = "Recyclable"
        const val PLUSH = "Plush"
        const val BALLROOM = "Ballroom"
        const val CASUAL = "Casual"
    }
}