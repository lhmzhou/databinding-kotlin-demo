package com.lhmzhou.databindingDemo.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.lhmzhou.databindingDemo.R
import com.lhmzhou.databindingDemo.data.Gender
import com.lhmzhou.databindingDemo.data.ProcurementType

@BindingAdapter("genderDrawable")
fun ImageView.getGenderDrawable(gender: Gender) {
    val resourceId = when (gender) {
        Gender.UNISEX -> R.drawable.ic_rainbow
        Gender.FEMALE -> R.drawable.ic_female
        Gender.MEN -> R.drawable.ic_men
    }
    setImageResource(resourceId)
}

@BindingAdapter("stateDrawable")
fun ImageView.getStateDrawable(procurementType: ProcurementType?) {
    procurementType?.run {
        val resourceId = when (this) {
            ProcurementType.BOUGHT -> R.drawable.ic_money
            ProcurementType.RECEIVED -> R.drawable.ic_gift
        }
        setImageResource(resourceId)
    }
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    visibility = if(visible) View.VISIBLE else View.GONE
}