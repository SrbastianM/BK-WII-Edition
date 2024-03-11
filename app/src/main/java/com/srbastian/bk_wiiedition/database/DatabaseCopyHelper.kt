package com.srbastian.bk_wiiedition.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import kotlin.jvm.Throws

class DatabaseCopyHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    var DB_PATH: String? = null
    var myContext: Context? = null
    lateinit var myDataBase: SQLiteDatabase

    companion object {
        var DB_NAME: String = "questions.db"
    }

    init {
        DB_PATH = "data/data" + context.packageName + "/" + "databases/"
        myContext = context
    }

    private fun checkDataBase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        try {
            val myPath: String = DB_PATH + DB_NAME
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLiteException) {
            println(e)
        }
        checkDB?.close()
        return checkDB != null
    }

    @Throws(IOException::class)
    private fun copyDataBase() {
        // Open the local db as the input stream
        val myInput = myContext?.assets?.open(DB_NAME)
        // Path to the just created empty db
        val outFileName: String = DB_PATH + DB_NAME
        // Open the Empty db as the output Stream
        val myOutput: OutputStream = FileOutputStream(outFileName)

        // transfer bytes from the input file to the outputfile
        val buffer = ByteArray(1024)
        var length: Int
        if (myInput != null) {
            while (myInput.read(buffer).also { length = it } > 0) {
                myOutput.write(buffer, 0, length)
            }
            // Toast Message
        }
        myOutput.flush()
        myOutput.close()
        myInput?.close()
    }

    @Throws(IOException::class)
    fun createDataBase() {
        val dbExist = checkDataBase()
        if (dbExist) {
        } else {
            this.readableDatabase
            try {
                copyDataBase()
            } catch (e: IOException) {
                throw Error("Error copying database")
            }
        }
    }

    @Throws(IOException::class)
    fun openDataBase() {
        // Open the database
        val myPath: String = DB_PATH + DB_NAME
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
    }

    @Synchronized
    override fun close() {
        myDataBase.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS world_war (question_id INTEGER, question_name TEXT, question_answer TEXT, question_image TEXT)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS wrong_answer (wrong_answer_id INTEGER, question_id INTEGER, wrong_answer_content TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS world_war")
        db?.execSQL("DROP TABLE IF EXISTS wrong_answer")
        onCreate(db)
    }

}