package com.srbastian.bk_wiiedition.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class DatabaseCopyHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    private var DB_PATH: String? = ""
    var myContext: Context? = null
    lateinit var myDataBase: SQLiteDatabase

    companion object {
        var DB_NAME: String = "questions.db"
    }

    init {
        DB_PATH = "/data/data/" + context.packageName + "/" + "databases/"
        myContext = context
        Log.d("DatabaseCopyHelper", "DB_PATH: $DB_PATH")
    }


    fun checkDataBase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        return try {
            val myPath: String = DB_PATH + DB_NAME
            checkDB =
                SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
            Log.d("DatabaseCopyHelper", "DB_PATH_CHECK_DATA: $myPath")
            true
        } catch (e: SQLiteException) {
            Log.e("errorOpeningDataBase", "Error opening the database", e)
            false
        } finally {
            checkDB?.close()
        }
    }

    @Throws(IOException::class)
    private fun copyDataBase() {
        // Open the local db as the input stream
        val myInput = myContext!!.assets?.open(DB_NAME)
            ?: throw IOException("Error opening the origin archive")
        Log.d("DatabaseCopyHelper", "DB_PATH_INPUT: $myInput")
        // Path to the just created empty db
        val outFileName: String = DB_PATH + DB_NAME
        Log.d("DatabaseCopyHelper", "DB_PATH_OUTPUT: $outFileName")
        // Open the Empty db as the output Stream
        val myOutput: OutputStream = FileOutputStream(outFileName)
        val buffer = ByteArray(1024)
        var length: Int

        try {
            while (myInput.read(buffer).also { length = it } > 0) {
                myOutput.write(buffer, 0, length)
            }
            copyQuestions("world_war")
            copyWrongQuestions("wrong_answers")
            Log.d("DatabaseCopyHelper", "Copied")
        } catch (e: IOException) {
            Log.e("errorOpeningDataBase", "Error copying the data base", e)
        }
        myOutput.flush()
        myOutput.close()
        myInput.close()
    }

    @Throws(IOException::class)
    fun createDataBase() {
        val dbExist = checkDataBase()
        if (!dbExist) {
            try {
                copyDataBase()
                Log.d("DatabaseCopyHelper", "Create database")
            } catch (e: IOException) {
                Log.e("errorOpeningDataBase", "Error copying database", e)
                throw Error("Error copying database")
            }
        } else {
            this.readableDatabase
        }
    }

    @Throws(IOException::class)
    fun openDataBase() {
        // Open the database
        if (myDataBase.isOpen) {
            return
        }
        val myPath: String = DB_PATH + DB_NAME
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
    }

    @Synchronized
    override fun close() {
        myDataBase.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //db?.execSQL("CREATE TABLE IF NOT EXISTS world_war (question_id INTEGER, question_name TEXT, question_answer TEXT, question_image TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db?.execSQL("DROP TABLE IF EXISTS world_war")
//        onCreate(db)
    }

    private fun copyQuestions(tableName: String){
        val cursor : Cursor = myDataBase.rawQuery("SELECT * FROM $tableName", null)
        val newDatabase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READONLY)
        try {
            val values = ContentValues()
            values.put("question_id", cursor.getColumnIndex("question_id"))
            values.put("question_name", cursor.getColumnIndex("question_name"))
            values.put("question_answer", cursor.getColumnIndex("question_answer"))
            values.put("question_image", cursor.getColumnIndex("question_image"))
            newDatabase.insert(tableName, null, values)
        } finally {
            cursor.close()
            newDatabase.close()
        }
    }
    private fun copyWrongQuestions(tableName: String){
        val cursor : Cursor = myDataBase.rawQuery("SELECT * FROM $tableName", null)
        val newDatabase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READONLY)
        try {
            val values = ContentValues()
            values.put("wrong_answer_id", cursor.getColumnIndex("wrong_answer_id"))
            values.put("question_id", cursor.getColumnIndex("question_id"))
            values.put("wrong_answer_content", cursor.getColumnIndex("wrong_answer_content"))
            newDatabase.insert(tableName, null, values)
        } finally {
            cursor.close()
            newDatabase.close()
        }
    }

}