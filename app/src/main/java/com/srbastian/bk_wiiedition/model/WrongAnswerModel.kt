package com.srbastian.bk_wiiedition.model

data class WrongAnswerModel(
    val wrongAnswerId: Int,
    val questionId: Int,
    val wrongAnswerContent: String
)