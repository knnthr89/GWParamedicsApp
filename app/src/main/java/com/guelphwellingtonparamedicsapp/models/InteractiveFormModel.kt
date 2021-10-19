package com.guelphwellingtonparamedicsapp.models

import java.io.Serializable

class InteractiveFormModel {
    private var id: Int = 0
    var title: String = ""
    private var hasScoring: Boolean = false
    private var assessment: ArrayList<AssessmentModel> = ArrayList()
    private var questions: ArrayList<QuestionModel> = ArrayList()

    constructor(
        id: Int,
        title: String,
        hasScoring: Boolean,
        assessment: ArrayList<AssessmentModel>,
        questions: ArrayList<QuestionModel>
    ) {
        this.id = id
        this.title = title
        this.hasScoring = hasScoring
        this.assessment = assessment
        this.questions = questions
    }
}