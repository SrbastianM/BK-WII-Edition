package com.srbastian.bk_wiiedition.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.srbastian.bk_wiiedition.R
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
    var correctNumber = 0
    private var wrongNumber = 0
    var emptyNumber = 0

    private var optionControl = false
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

        fragmentQuizBinding.btnNext.setOnClickListener {
            questionNumber++
            Log.d("QuestionNumber", questionNumber.toString())
            if (questionNumber >= 9) {
                if (!optionControl){
                    emptyNumber++
                }
                val direction =
                    FragmentQuizDirections.actionFragmentQuizToFragmentResult().apply {
                        correct = correctNumber
                        wrong = wrongNumber
                        empty = emptyNumber
                    }
                this.findNavController().apply {
                    navigate(direction)
                    popBackStack(R.id.fragmentResult, false)
                }
                Toast.makeText(requireActivity(), "Quiz Finish", Toast.LENGTH_SHORT).show()
            }else{
                if (optionControl) {
                    resetButtons()
                    showData()
                    optionControl = false
                } else {
                    showData()
                    if (!optionControl) {
                        emptyNumber++
                        fragmentQuizBinding.tvEmptyNumber.text = emptyNumber.toString()
                    } else {
                        resetButtons()
                    }
                }
            }
        }
        return fragmentQuizBinding.root
    }

    private fun showData() {
        correctOption = questionsList[questionNumber]
        fragmentQuizBinding.tvQuestion.text = correctOption!!.questionName

        fragmentQuizBinding.ivQuestion.setImageResource(
            resources.getIdentifier(
                correctOption!!.questionImage,
                "drawable",
                requireActivity().packageName
            )
        )
        wrongAnswersList =
            dao.getWrongAnswerByQuestionId(
                correctOption!!.id,
                DatabaseCopyHelper(requireActivity())
            )

        var answers = wrongAnswersList.map { it.wrongAnswerContent }.toMutableList()
        answers.add(correctOption!!.questionAnswer)
        answers = answers.shuffled() as MutableList<String>
        for ((index, answer) in answers.withIndex()) {
            if (answer == correctOption!!.questionAnswer) {
                rightAnswerIndex = index
            }
            answerButtons[index].text = answer
            answerButtons[index].setOnClickListener {
                checkAnswer(index)
            }
        }
    }

    private fun checkAnswer(answer: Int) {
        val button = answerButtons[answer]
        if (answer == rightAnswerIndex) {
            correctNumber++
            fragmentQuizBinding.tvCorrectNumber.text = correctNumber.toString()
            button.setBackgroundColor(button.context.resources.getColor(R.color.light_green))
            button.setTextColor(button.context.resources.getColor(R.color.light_beige))
        } else {
            wrongNumber++
            fragmentQuizBinding.tvWrongNumber.text = wrongNumber.toString()
            button.setBackgroundColor(button.context.resources.getColor(R.color.light_red))
            button.setTextColor(button.context.resources.getColor(R.color.light_beige))
        }
        fragmentQuizBinding.btnA.isClickable = false
        fragmentQuizBinding.btnB.isClickable = false
        fragmentQuizBinding.btnC.isClickable = false
        fragmentQuizBinding.btnD.isClickable = false

        optionControl = true

    }

    private fun resetButtons() {
        for (button in answerButtons) {
            button.setBackgroundColor(button.context.resources.getColor(R.color.white))
            button.setTextColor(button.context.resources.getColor(R.color.dark_blue))
        }
    }
}