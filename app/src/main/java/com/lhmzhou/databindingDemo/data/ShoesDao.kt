package com.lhmzhou.databindingDemo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoeDao {

    @get:Query("SELECT * FROM shoes")
    val allShoes: LiveData<List<ShoeEntry>>

    @Query("SELECT * FROM shoes WHERE shoeId = :id")
    fun getChosenShoe(id: Int): LiveData<ShoeEntry>

    @Insert
    fun insertShoe(shoe: ShoeEntry)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateShoeInfo(shoe: shoeEntry)

    @Delete
    fun deleteShoe(shoe: ShoeEntry)
}