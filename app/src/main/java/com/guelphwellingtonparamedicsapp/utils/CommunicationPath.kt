package com.guelphwellingtonparamedicsapp.utils

import android.util.SparseArray

enum class CommunicationPath(index: Int) {
    CONTACT_GROUPS(1),
    INTERACTIVE_FORMS(2),
    INDIVIDUAL_FORM(3),
    LOGIN(4),
    ASSESSMENTS(5),
    RESOURCE_REGIONS(6),
    RESOURCES(7);

    var index : Int = 0
    internal set

    val path : String
        get() {
            var description = ""

            val method = valueOfEnum(index)
            description = when(method){
                CONTACT_GROUPS -> "ContactGroups"
                INTERACTIVE_FORMS -> "InteractiveForms"
                INDIVIDUAL_FORM -> "InteractiveForms"
                LOGIN -> "Accounts/login"
                ASSESSMENTS -> "Assessments"
                RESOURCE_REGIONS -> "ResourceRegions"
                RESOURCES -> "Resources"

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