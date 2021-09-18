package com.lhmzhou.databindingDemo.data

import androidx.lifecycle.LiveData
import com.lhmzhou.databindingDemo.utils.AppExecutors

class ShoeRepository private constructor(private val mDatabase: ShoeDatabase, private val mExecutors: AppExecutors) {

    val shoeList: LiveData<List<ShoeEntry>>
        get() = mDatabase.shoeDao().allShoes

    fun getChosenShoe(shoeId: Int): LiveData<ShoeEntry> {
        return mDatabase.shoeDao().getChosenShoe(shoeId)
    }

    fun insertShoe(shoe: ShoeEntry) {
        mExecutors.diskIO().execute{ mDatabase.shoeDao().insertShoe(shoe) }
    }

    fun updateShoe(shoe: ShoeEntry) {
        mExecutors.diskIO().execute{ mDatabase.shoeDao().updateShoeInfo(shoe) }
    }

    fun deleteShoe(shoe: ShoeEntry) {
        mExecutors.diskIO().execute{ mDatabase.shoeDao().deleteShoe(shoe) }
    }

    companion object {

        private var sInstance: ShoeRepository? = null

        fun getInstance(database: ShoeDatabase, executors: AppExecutors): ShoeRepository {
            return sInstance ?: synchronized(this) {
                sInstance
                    ?: ShoeRepository(
                        database,
                        executors
                    ).also { sInstance = it }
            }
        }
    }

}
