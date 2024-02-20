package msi.paria.currencyexchanger.data.datasource.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import msi.paria.currencyexchanger.data.dto.db.Transaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction` order by id asc")
    fun getTransactions(): List<Transaction>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(transactions: List<Transaction>)

    @Delete
    fun delete(transaction: Transaction)
}