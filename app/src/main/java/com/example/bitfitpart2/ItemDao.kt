package com.example.bitfitpart2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM item_table")
    fun getAll(): Flow<List<Item>>

    @Insert
    fun insert(item: Item)

    @Query("DELETE FROM item_table")
    fun deleteAll()

    @Query("SELECT amount FROM item_table")
    fun getAmount(): List<Int>
}