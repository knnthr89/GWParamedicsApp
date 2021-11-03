package com.guelphwellingtonparamedicsapp.daos

import androidx.room.*
import com.guelphwellingtonparamedicsapp.entities.User

@Dao
interface UserDao {

    @Insert
    fun insert(vararg user: User)

    @Query("UPDATE user SET token = :token WHERE uid LIKE :id")
    fun updateById(id : Int, token : String)

    @Query("SELECT * FROM user")
    fun getUser() : List<User>

   @Delete
   fun delete(user : User)

   @Query("DELETE FROM user")
   fun deleteAll()
}