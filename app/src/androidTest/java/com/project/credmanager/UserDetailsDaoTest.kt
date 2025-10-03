package com.project.credmanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.project.credmanager.dao.UserDetailsDao
import com.project.credmanager.db.CredDB
import com.project.credmanager.model.UserDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws
import com.google.common.truth.Truth.assertThat

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class UserDetailsDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: CredDB
    private lateinit var userDetailsDao: UserDetailsDao

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CredDB::class.java
        ).allowMainThreadQueries().build()
        userDetailsDao = database.userDetailsDao()
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertUser() = runTest {
        val user = UserDetails(
            0,
            "Test12345",
            8956234578,
            "Pass12345",
            "a1b2c3d4e5f67890"
        )
        userDetailsDao.insertUser(user)

        val insertedUser = userDetailsDao.getAllUser()[0]
        val updateUser = insertedUser.copy(userPhone = 9874563210)
        userDetailsDao.updateUser(updateUser)

        val retrieveData = userDetailsDao.getAllUser()
        assertThat(retrieveData[0].userId).isEqualTo("Test12345")
        assertThat(retrieveData[0].userPhone).isEqualTo(9874563210)
    }


}