package com.hank.credit

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hank.credit.data.Card
import com.hank.credit.data.CardDao
import com.hank.credit.data.CardDatabase
import com.hank.credit.data.Coupon
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var sleepDao: CardDao
    private lateinit var db: CardDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, CardDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        sleepDao = db.cardDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val card = Card("富邦",5,false)
        val coupon = Coupon("海外","打八折","富邦")
        sleepDao.insertCard(card)
        sleepDao.insertCoupon(coupon)
        val fuban = sleepDao.getCard("富邦")
        assertEquals(fuban.cardImg,5)
        val cou = sleepDao.getCoupon("富邦")
        cou.forEach {
            assertEquals(it.couponType,"海外")
        }
    }
}