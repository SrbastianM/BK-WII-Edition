package com.srbastian.bk_wiiedition.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.srbastian.bk_wiiedition.database.DatabaseCopyHelper
import com.srbastian.bk_wiiedition.database.QuestionsDao
import com.srbastian.bk_wiiedition.databinding.FragmentQuizBinding
import com.srbastian.bk_wiiedition.model.QuestionModel
import com.srbastian.bk_wiiedition.model.WrongAnswerModel

class FragmentQuiz : Fragment() {
    lateinit var fragmentQuizBinding: FragmentQuizBinding
    var correctOption: QuestionModel? = null

    val dao = QuestionsDao()

    var questionsList = ArrayList<QuestionModel>() // [0, 1, 2, 3, 4]
    var wrongAnswersList = ArrayList<WrongAnswerModel>()
    var answerButtons = ArrayList<Button>()

    var questionNumber = 0
    var rightAnswerIndex = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentQuizBinding = FragmentQuizBinding.inflate(inflater, container, false)
        questionsList = dao.getRandomTenRecords(DatabaseCopyHelper(requireActivity()))
        answerButtons.add(fragmentQuizBinding.btnA)
        answerButtons.add(fragmentQuizBinding.btnB)
        answerButtons.add(fragmentQuizBinding.btnC)
        answerButtons.add(fragmentQuizBinding.btnD)

        showData()

        return fragmentQuizBinding.root
    }

    private fun showData() {
        correctOption = questionsList[questionNumber]
        fragmentQuizBinding.tvQuestion.text = correctOption!!.questionName.plus(questionNumber + 1)

        fragmentQuizBinding.ivQuestion.setImageResource(
            resources.getIdentifier(
                correctOption!!.questionImage,
                "drawable",
                requireActivity().packageName
            )
        )
//        // [1, 1, 1, - option 1, option 2, option 3] -> WrongAnswer = correctOption
        wrongAnswersList =
            dao.getWrongAnswerByQuestionId(correctOption!!.id, DatabaseCopyHelper(requireActivity()))
//        // HashSet to put the correct and false answer like an option
//        val mixOption = HashSet<QuestionModel>()
//        mixOption.clear()
//        // add the options like an objet using the add() method
//        mixOption.add(correctOption)
//        mixOption.add(wrongAnswersList)
//        mixOption.add(wrongAnswersList[1] as QuestionModel)
//        mixOption.add(wrongAnswersList[2] as QuestionModel)

        var answers = wrongAnswersList.map { it.wrongAnswerContent }.toMutableList()
        answers.add(correctOption!!.questionAnswer)
        answers = answers.shuffled() as MutableList<String>
        for ( (index, answer) in answers.withIndex())
        {
            if (answer == correctOption!!.questionAnswer) {
                rightAnswerIndex = index
            }
            answerButtons[index].text = answer
            answerButtons[index].setOnClickListener {
                checkAnswer(index)
            }
        }
    }

    //Logica fallo o correcto
    fun checkAnswer(answer: Int){
        Log.d("checkAnswer", (answer == rightAnswerIndex).toString())
    }
}