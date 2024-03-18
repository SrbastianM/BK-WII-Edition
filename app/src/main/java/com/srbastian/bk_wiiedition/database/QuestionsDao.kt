package com.srbastian.bk_wiiedition.database

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.srbastian.bk_wiiedition.model.QuestionModel
import com.srbastian.bk_wiiedition.model.WrongAnswerModel


class QuestionsDao {

    @SuppressLint("Range")
    fun getRandomTenRecords(helper: DatabaseCopyHelper): ArrayList<QuestionModel> {
        val recordList = ArrayList<QuestionModel>()
        val database: SQLiteDatabase = helper.readableDatabase
        //read the data line by line
        val cursor: Cursor =
            database.rawQuery("SELECT * FROM world_war ORDER BY RANDOM() LIMIT 10", null)
        val idIndex = cursor.getColumnIndex("question_id")
        val questionName = cursor.getColumnIndex("question_name")
        val questionAnswer = cursor.getColumnIndex("question_answer")
        val questionImage = cursor.getColumnIndex("question_image")

        while (cursor.moveToNext()) {

            val record = QuestionModel(
                cursor.getInt(idIndex),
                cursor.getString(questionName),
                cursor.getString(questionAnswer),
                cursor.getString(questionImage)
            )
            recordList.add(record)
        }
        Log.d("cursor", cursor.count.toString())
        cursor.close()
        return recordList
    }

    fun getWrongAnswerByQuestionId(id: Int, helper: DatabaseCopyHelper): ArrayList<WrongAnswerModel> {
        val wrongAnswerRecords  = ArrayList<WrongAnswerModel>()
        val database : SQLiteDatabase = helper.writableDatabase
        // read the data line by line
        val cursor : Cursor = database.rawQuery("SELECT * FROM wrong_answers WHERE question_id == ?", arrayOf(id.toString()))
        for (column in cursor.columnNames){
            Log.d("columns", column)
        }
        Log.d("columns", cursor.columnNames.toString())
        val wrongAnswerId = cursor.getColumnIndex("wrong_answer_id ")
        val wrongQuestionId = cursor.getColumnIndex("question_id")
        val wrongAnswerContent = cursor.getColumnIndex("wrong_answer_content")

        while (cursor.moveToNext()) {

            val record = WrongAnswerModel(
                cursor.getInt(wrongAnswerId),
                cursor.getInt(wrongQuestionId),
                cursor.getString(wrongAnswerContent)
            )
            wrongAnswerRecords.add(record)
        }
        return wrongAnswerRecords
    }

}