package com.lhmzhou.databindingDemo.utils

import android.content.Context
import com.lhmzhou.databindingDemo.data.ShoeDatabase
import com.lhmzhou.databindingDemo.data.ShoeRepository

fun provideRepository(context: Context): ShoeRepository {
    val executors = AppExecutors.getInstance()
    val db = ShoeDatabase.getInstance(context)
    return ShoeRepository.getInstance(db, executors)
}