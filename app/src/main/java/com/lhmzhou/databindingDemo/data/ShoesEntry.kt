package com.lhmzhou.databindingDemo.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "shoes")
data class ShoeEntry(
    var shoeName: String,
    var categories: Map<String, Boolean>,
    var gender: Gender = Gender.UNISEX,
    var procurementType: ProcurementType? = null,
    @PrimaryKey(autoGenerate = true) val shoeId: Int = 0
): Parcelable{

    /*This function is needed for a healthy comparison of two items,
    particularly for detecting changes in the contents of the map.
    Native copy method of the data class assign a map with same reference
    to the copied item, so equals() method cannot detect changes in the content.*/
    fun copy() : ShoeEntry{
        val newCategories = mutableMapOf<String, Boolean>()
        newCategories.putAll(categories)
        return ShoeEntry(shoeName, newCategories, gender, procurementType, shoeId)
    }
}

