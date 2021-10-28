package com.guelphwellingtonparamedicsapp.enums

import android.util.SparseArray

enum class AnswersEnum(index: Int) {

    TF(0),
    MULTIPLE_SELECT(1),
    FILL_IN(2)
    ;

    var index: Int = 0
        internal set

    val path: String
        get() {

            var description = ""

            val method = valueOfEnum(index)
            when (method) {
                TF -> description = "TF"
                MULTIPLE_SELECT -> description = "MutipleSelect"
                FILL_IN -> description = "FillIn"
            }
            return description
        }

    init {
        this.index = index
    }

    companion object {

        private val array = SparseArray<AnswersEnum>()

        init {
            for (option in values()) {
                array.put(option.index, option)
            }
        }

        fun valueOfEnum(index: Int): AnswersEnum {
            return array.get(index)
        }
    }
}