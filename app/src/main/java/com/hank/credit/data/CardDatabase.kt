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
            Card("玉山1", R.drawable.img_card_double),
            Card("玉山2", R.drawable.img_card_double),
            Card("玉山3", R.drawable.img_card_double),
            Card("玉山4", R.drawable.img_card_double),
            Card("玉山5", R.drawable.img_card_double),
            Card("玉山6", R.drawable.img_card_double),
            Card("玉山7", R.drawable.img_card_double),
            Card("玉山8", R.drawable.img_card_double)
            )
        val PREPOPULATE_COUPON = listOf(
            Coupon("海外","50%","玉山1"),
            Coupon("吃飯","50%","玉山1"),
            Coupon("加油","50%","玉山3"),
            Coupon("喝水","50%","玉山雙幣卡"),
            Coupon("飲料","50%","玉山4"),
            Coupon("點心","50%","玉山5"),
            Coupon("海外","50%","玉山5"),
            Coupon("吃飯","50%","玉山5")
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