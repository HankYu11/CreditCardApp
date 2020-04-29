package com.hank.credit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hank.credit.R
import com.hank.credit.ioThread

@Database(entities = [Card::class,Coupon::class], version = 1, exportSchema = false)
abstract class CardDatabase : RoomDatabase(){
    abstract val cardDao : CardDao
    companion object{
        @Volatile private var INSTANCE : CardDatabase? = null

        fun getInstance(context: Context) : CardDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CardDatabase::class.java,
                "card_database")
                .addCallback(object : Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            getInstance(context).cardDao.insertCard(PREPOPULATE_CARD)
                            getInstance(context).cardDao.insertCoupon(PREPOPULATE_COUPON)
                        }
                    }
                })
                .build()

        val PREPOPULATE_CARD = listOf(
            Card("玉山雙幣卡", R.drawable.img_card_double),
            Card("花旗現金回饋卡", R.drawable.img_card_huachi),
            Card("國泰白金卡", R.drawable.img_card_guotaibaigin),
            Card("測試", R.drawable.img_card_double),
            Card("測試1", R.drawable.img_card_double),
            Card("測試2", R.drawable.img_card_double),
            Card("測試3", R.drawable.img_card_double),
            Card("測試4", R.drawable.img_card_double)
            )
        val PREPOPULATE_COUPON = listOf(
            Coupon("海外","實體通路現金回饋2.5%","花旗現金回饋卡"),
            Coupon("餐飲","國內餐飲業95折優惠","國泰白金卡"),
            Coupon("加油","不限加油站，加油一律8折","國泰白金卡"),
            Coupon("便利商店","7-11消費5%現金回饋","玉山雙幣卡"),
            Coupon("機場","貴賓休息室","花旗現金回饋卡"),
            Coupon("點心","特約點心商店5折","國泰白金卡"),
            Coupon("海外","50%","國泰白金卡"),
            Coupon("餐飲","50%","花旗現金回饋卡")
        )

    }
}
//        fun getInstance(context : Context) : CardDatabase{
//            synchronized(this){
//                var instance = INSTANCE
//                if(instance == null){
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        CardDatabase::class.java,
//                        "card_database")
//                        .fallbackToDestructiveMigration()
//                        .build()
//                }
//                INSTANCE = instance
//                return instance
//            }
//        }