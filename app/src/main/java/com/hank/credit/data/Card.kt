package com.hank.credit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
data class Card(
    @PrimaryKey
    var cardName : String,
    var cardImg : Int,
    var cardMine : Int = 0
)

@Entity(tableName = "coupon")
data class Coupon(
    var couponType : String,
    var couponDiscount : String,
    var cardName: String,
    @PrimaryKey(autoGenerate = true)
    var couponId : Long = 0L
)
