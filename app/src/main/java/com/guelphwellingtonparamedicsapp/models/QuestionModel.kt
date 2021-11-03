package com.guelphwellingtonparamedicsapp.models

class QuestionModel(
    var id: Int,
    var type: String,
    var title: String,
    var content: ContentModel?,
    var skippedForScoring : Boolean,
    var showIt : Boolean = true
) {

}