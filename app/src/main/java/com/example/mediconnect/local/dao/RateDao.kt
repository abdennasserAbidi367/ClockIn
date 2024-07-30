package com.example.mediconnect.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mediconnect.domain.entities.Rate
import org.w3c.dom.Comment

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRate(user: Rate) : Long

    @Query("SELECT * FROM rate WHERE idUser =:userId")
    fun getRateById(userId: String): List<Rate>

    @Query("DELETE FROM rate WHERE id =:idRate")
    fun deleteRate(idRate: Int) : Int

    @Query("UPDATE rate SET comment =:comments WHERE id =:idRate")
    fun updateRate(idRate: Int, comments: MutableList<String>)
}