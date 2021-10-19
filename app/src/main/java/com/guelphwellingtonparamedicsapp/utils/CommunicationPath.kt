package com.guelphwellingtonparamedicsapp.utils

import android.util.SparseArray

enum class CommunicationPath(index: Int) {
    CONTACT_GROUPS(1),
    INTERACTIVE_FORMS(2);


    var index : Int = 0
    internal set

    val path: String
        get() {
            var description = ""

            val method = valueOfEnum(index)
            when(method){
                CONTACT_GROUPS -> description = "ContactGroups"
                INTERACTIVE_FORMS -> description = "InteractiveForms"
            }
            return description
        }
    init {
        this.index = index
    }

    companion object {

        private val array = SparseArray<CommunicationPath>()

        init {
            for(option in values()) {
                array.put(option.index, option)
            }
        }

        fun valueOfEnum(index : Int) : CommunicationPath {
            return array.get(index)
        }
    }
}