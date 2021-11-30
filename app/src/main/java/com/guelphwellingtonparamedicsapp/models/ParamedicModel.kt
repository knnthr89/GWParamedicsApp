package com.guelphwellingtonparamedicsapp.models

import java.io.Serializable

class ParamedicModel(var name : String, var email : String, var homePhone : String, var extension : String, var workPhone : String, var personalPhone : String, var availableServices : ArrayList<String>) :
    Serializable {
}