package com.guelphwellingtonparamedicsapp.models

import java.util.*
import kotlin.collections.ArrayList

class IndividualFormModel(
    var id: Int,
    var title: String,
    var hasScoring: Boolean,
    var sections: ArrayList<SectionModel> = ArrayList()
) {

}