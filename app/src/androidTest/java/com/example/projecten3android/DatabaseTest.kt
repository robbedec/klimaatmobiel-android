package com.example.projecten3android

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.klimaatmobiel.data.database.DatabaseProduct
import com.klimaatmobiel.data.database.ProductDao
import com.klimaatmobiel.data.database.ProductsDatabase
import com.klimaatmobiel.domain.Category
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class DatabaseTest {

    private lateinit var productDao: ProductDao
    private lateinit var db: ProductsDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ProductsDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        productDao = db.productDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetProduct() {
        val product = DatabaseProduct(1, "Hout", "Beschrijving van hout", null, 1, 10.0, Category(1, "test", "test"))

        productDao.insertAll(listOf(product))

        val testProduct = productDao.getProduct(1, 1)
        assertEquals(testProduct?.productName, product.productName)
    }
}