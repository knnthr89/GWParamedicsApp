package com.guelphwellingtonparamedicsapp.models

import java.io.Serializable

class InteractiveFormModel(
    var id: Int,
    var title: String,
    var hasScoring: Boolean,
    var assessment: ArrayList<AssessmentModel>,
    var questions: ArrayList<QuestionModel>
) {

}