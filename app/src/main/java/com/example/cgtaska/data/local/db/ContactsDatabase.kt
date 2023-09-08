package com.example.cgtaska.data.local.db

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.example.cgtaska.common.Constants.DB_NAME
import com.example.cgtaska.data.local.dao.ContactsDao
import com.example.cgtaska.data.local.dao.ContactsKeysDao
import com.example.cgtaska.data.local.model.ContactsRemoteKeys
import com.example.cgtaska.domain.model.ContactList
import com.example.cgtaska.domain.model.LoginAuth
import net.sqlcipher.database.SupportFactory
import java.io.File
import java.security.SecureRandom

@Database(
    entities = [ContactList::class, ContactsRemoteKeys::class, LoginAuth::class],
    version = 1
)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactsDao
    abstract fun contactRemoteKeysDao(): ContactsKeysDao

    companion object {

        @Volatile
        private var instance: ContactsDatabase? = null

        @RequiresApi(Build.VERSION_CODES.O)
        fun getInstance(context: Context): ContactsDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun buildDatabase(context: Context): ContactsDatabase {

            val passphrase = getPassphrase(context)
            val helperFactory = SupportFactory(passphrase)

            return Room.databaseBuilder(context.applicationContext, ContactsDatabase::class.java, DB_NAME)
                .openHelperFactory(helperFactory)
                .fallbackToDestructiveMigration()
                .build()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getPassphrase(context: Context): ByteArray {
            val file = File(context.filesDir, "passphrase.bin")
            val encryptedFile = EncryptedFile.Builder (
                file,
                context.applicationContext,
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()

            return if (file.exists()) {
                encryptedFile.openFileInput().use { it.readBytes() }
            } else {
                generatePassphrase().also { passphrase ->
                    encryptedFile.openFileOutput().use { it.write(passphrase) }
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun generatePassphrase(): ByteArray {
            val random = SecureRandom.getInstanceStrong()
            val result = ByteArray(32)

            random.nextBytes(result)
            while (result.contains(0)) {
                random.nextBytes(result)
            }

            return result
        }


    }
}