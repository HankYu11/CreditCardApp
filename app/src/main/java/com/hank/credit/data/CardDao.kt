package com.hank.credit.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CardDao{
    @Insert
    fun insertCard(card: List<Card>)

    @Insert
    fun insertCoupon(coupon: List<Coupon>)

    @Query("select * from card")
    fun getAllCards() : List<Card>

    @Query("select * from card where cardMine = 1")
    fun getMyCards() : List<Card>

    @Query("select distinct couponType from coupon")
    fun getAllType() : List<String>

    @Query("select distinct couponType from coupon inner join card on coupon.cardName = card.cardName where cardMine = 1")
    fun getMyType() : List<String>

    @Query("select distinct cardName from coupon where couponType = :type")
    fun getTypeCardName(type : String) : List<String>

    @Query("select distinct card.cardName from coupon inner join card on coupon.cardName = card.cardName where couponType = :type and cardMine = 1")
    fun getTypeMyCardName(type: String) : List<String>

    @Query("select * from card where cardName = :cardName")
    fun getCard(cardName : String) : Card

    @Query("select * from coupon where cardName = :cardName")
    fun getCoupon(cardName: String) : List<Coupon>

    @Query("update card set cardMine = 1 where cardName = :cardName")
    fun setCardMine(cardName: String)
}