package com.srbastian.bk_wiiedition.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.srbastian.bk_wiiedition.database.DatabaseCopyHelper.Companion.DB_NAME

import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.io.FileInputStream
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.io.File
import java.io.IOException
import java.io.InputStream

@RunWith(AndroidJUnit4::class)
open class QuestionsDaoTest {

    @Mock
    private lateinit var mockHelper: DatabaseCopyHelper

    @Mock
    private lateinit var mockDatabase: SQLiteDatabase

    @Mock
    private lateinit var mockCursor: Cursor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetRandomTenRecords() {
        // Configurar el comportamiento simulado del helper
        `when`(mockHelper.writableDatabase).thenReturn(mockDatabase)

        // Configurar el comportamiento simulado del cursor
        `when`(
            mockDatabase.rawQuery(
                "SELECT * FROM world_war ORDER BY RANDOM() LIMIT 10",null
            )
        ).thenReturn(mockCursor)

        // Configurar el comportamiento simulado del cursor.moveToNext()
        `when`(mockCursor.moveToNext())
            .thenReturn(true)  // Primera llamada al método
            .thenReturn(true)  // Segunda llamada al método
            .thenReturn(false) // Tercera llamada al método (termina el ciclo)

        // Configurar el comportamiento simulado de los métodos del cursor
        `when`(mockCursor.getColumnIndex("question_id")).thenReturn(0)
        `when`(mockCursor.getColumnIndex("question_name")).thenReturn(1)
        `when`(mockCursor.getColumnIndex("question_answer")).thenReturn(2)

        // Configurar el comportamiento simulado de los métodos del cursor para devolver valores específicos
        `when`(mockCursor.getInt(0)).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("Pregunta 1")
        `when`(mockCursor.getString(2)).thenReturn("Respuesta 1")

        // Crear el objeto de la clase bajo prueba
        val mockContext = mock(Context::class.java)
        val helper = DatabaseCopyHelper(mockContext)

        // Llamar al método que se está probando
        val dao = QuestionsDao()
        val result= dao.getRandomTenRecords(helper)

        // Verificar el resultado
        assertEquals(1, result.size)
        assertEquals(1, result[0].id)
        assertEquals("Pregunta 1", result[0].questionName)
        assertEquals("Respuesta 1", result[0].questionAnswer)
    }

}