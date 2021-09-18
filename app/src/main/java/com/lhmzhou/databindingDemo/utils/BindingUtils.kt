@file:JvmName("BindingUtils")
package com.lhmzhou.databindingDemo.utils

import androidx.databinding.InverseMethod
import com.lhmzhou.databindingDemo.R
import com.lhmzhou.databindingDemo.data.Gender
import com.lhmzhou.databindingDemo.data.ProcurementType

fun attachCategories(categories: Map<String, Boolean>): String? {
    return categories.filter { it.value }
        .keys
        .joinToString(separator = ", ")
}

@InverseMethod("buttonIdToProcurementType")
fun procurementTypeToButtonId(procurementType: ProcurementType?): Int {
    var selectedButtonId = -1

    procurementType?.run {
        selectedButtonId = when (this) {
            ProcurementType.BOUGHT -> R.id.radioBtn_bought
            ProcurementType.RECEIVED -> R.id.radioBtn_received
        }
    }

    return selectedButtonId
}

fun buttonIdToProcurementType(selectedButtonId: Int): ProcurementType? {
    return when (selectedButtonId) {
        R.id.radioBtn_bought -> ProcurementType.BOUGHT
        R.id.radioBtn_received -> ProcurementType.RECEIVED
        else -> null
    }
}

@InverseMethod("positionToGender")
fun genderToPosition(gender: Gender?): Int {
    return gender?.ordinal ?: 0
}

fun positionToGender(position: Int): Gender {
    return Gender.values()[position]
}