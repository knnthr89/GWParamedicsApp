package com.guelphwellingtonparamedicsapp.utilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guelphwellingtonparamedicsapp.models.ContactModel
import com.guelphwellingtonparamedicsapp.models.DepartmentModel
import com.guelphwellingtonparamedicsapp.models.ParamedicModel

class ContactUtilities {
    companion object {
        fun initialStaticData() : LiveData<List<DepartmentModel>> {

            val services : ArrayList<String> = ArrayList()
            services.add("CHAP")
            services.add("LTC")
            services.add("FLU")
            services.add("COVID")
            services.add("PAD")

            val leanne = ParamedicModel(
                name = "Leanne Swantko",
                email = "leanne.swantko@guelph.ca",
                homePhone = "",
                extension = "2105",
                workPhone = "519-993-5775",
                personalPhone = "519-651-0970",
                availableServices = arrayListOf("CHAP")
            )

            val brad = ParamedicModel(
                name = "Brad Jackson",
                email = "brad.jackson@guelph.ca",
                homePhone = "",
                extension = "3379",
                workPhone = "226-962-4713",
                personalPhone = "519-321-0325",
                availableServices = services
            )

            val emily = ParamedicModel(
                name = "Emily Cooper",
                email = "emily.cooper@guelph.ca",
                homePhone = "",
                extension = "3379",
                workPhone = "226-962-4715",
                personalPhone = "519-212-1189",
                availableServices = services
            )

            val amy = ParamedicModel(
                name = "Amy Courtney",
                email = "amy.courtney@guelph.ca",
                homePhone = "",
                extension = "3379",
                workPhone = "519-820-2507",
                personalPhone = "519-212-5120",
                availableServices = arrayListOf("CHAP")
            )

            val andrea = ParamedicModel(
                name = "Andrea Ieropoli",
                email = "andrea.ieropoli@guelph.ca",
                homePhone = "",
                extension = "3379",
                workPhone = "519-820-2176",
                personalPhone = "",
                availableServices = arrayListOf("")
            )

            val programCoordinationDepartment =
                DepartmentModel(
                    id = 1,
                    name = "Coordination",
                    contacts = arrayListOf(leanne, brad, emily, amy, andrea),
                    email = "PS-ES-EMS-CPCoordinators@guelph.ca"
                )

            val amyBenn = ParamedicModel(
                name = "Amy Benn",
                email = "amy.benn@guelph.ca",
                homePhone = "",
                extension = "",
                workPhone = "",
                personalPhone = "519-313-0490",
                availableServices = services
            )

            val kimGlover = ParamedicModel(
                name = "Kim Glover",
                email = "kim.glover@guelph.ca",
                homePhone = "",
                extension = "",
                workPhone = "",
                personalPhone = "519-939-0619",
                availableServices = arrayListOf("LTC", "COVID")
            )

            val derek = ParamedicModel(
                name = "Derek Bridgwater",
                email = "derek.bridgwater@guelph.ca",
                homePhone = "519-323-3526",
                extension = "",
                workPhone = "",
                personalPhone = "519-323-8254",
                availableServices = arrayListOf("LTC", "FLU", "COVID")
            )

            val communityParamedicDepartment =
                DepartmentModel(
                    id = 2,
                    name = "Community Paramedic",
                    contacts = arrayListOf(amyBenn, kimGlover, derek),
                    email = "PS-ES-EMS-CommunityParamedics@guelph.ca"
                )

            val mike = ParamedicModel(
                name = "Mike Dick",
                email =  "mick.dick@guelph.ca",
                homePhone = "519-343-3891",
                extension = "3379",
                workPhone = "",
                personalPhone = "519-291-8020",
                availableServices = arrayListOf("COVID", "PAD")
            )

            val dwayne = ParamedicModel(
                name = "Dwayne Buhrow",
                email =  "dwayne.buhrow@guelph.ca",
                homePhone = "519-846-1756",
                extension = "3379",
                workPhone = "",
                personalPhone = "519-212-0232",
                availableServices = arrayListOf("PAD")
            )

            val amyBen = ParamedicModel(
                name = "Amy Benn",
                email =  "amy.benn@guelph.ca",
                homePhone = "519-846-1756",
                extension = "3379",
                workPhone = "",
                personalPhone = "519-313-0490",
                availableServices = services
            )

            val padSpecialistDepartment =
                DepartmentModel(
                    id = 3,
                    name = "PAD Specialist",
                    contacts = arrayListOf(mike, dwayne, amyBen),
                    email = "PAD@guelph.ca"
                )

            val cpOffice = ParamedicModel(
                name = "CP Office",
                email =  "",
                homePhone = "",
                extension = "3379",
                workPhone = "519-822-1260",
                personalPhone = "",
                availableServices = arrayListOf()
            )

            val cpOfficeDepartment =
                DepartmentModel(
                    id = 3,
                    name = "CP Office",
                    contacts = arrayListOf(cpOffice),
                    email = "communityparamedic@guelph.ca"
                )

            val southFax = ParamedicModel(
                name = "South Fax",
                email =  "",
                homePhone = "",
                extension = "",
                workPhone = "519-840-2565",
                personalPhone = "",
                availableServices = arrayListOf()
            )

            val southCP = ParamedicModel(
                name = "South CP (#1)",
                email =  "",
                homePhone = "",
                extension = "",
                workPhone = "519-546-5970",
                personalPhone = "",
                availableServices = arrayListOf()
            )

            val cpResourceNight = ParamedicModel(
                name = "CP Resource Night (#2)",
                email =  "",
                homePhone = "",
                extension = "",
                workPhone = "519-546-5412",
                personalPhone = "",
                availableServices = arrayListOf()
            )

            val padClinicCpResourceD = ParamedicModel(
                name = "PAD/Clinic/CP Resource D (#3)",
                email =  "",
                homePhone = "",
                extension = "",
                workPhone = "226-962-3460",
                personalPhone = "",
                availableServices = arrayListOf()
            )

            val cpSouthDepartment =
                DepartmentModel(
                    id = 4,
                    name = "CP South",
                    contacts = arrayListOf(southFax, southCP, cpResourceNight, padClinicCpResourceD),
                    email = "communityparamedic@guelph.ca"
                )

            val northFax = ParamedicModel(
                name = "North Fax",
                email =  "",
                homePhone = "",
                extension = "",
                workPhone = "519-338-3121",
                personalPhone = "",
                availableServices = arrayListOf()
            )

            val northCP = ParamedicModel(
                name = "North CP (7-19) (#4)",
                email =  "",
                homePhone = "",
                extension = "",
                workPhone = "226-821-5007",
                personalPhone = "",
                availableServices = arrayListOf()
            )

            val doorCode = ParamedicModel(
                name = "Door Code",
                email =  "",
                homePhone = "",
                extension = "541*",
                workPhone = "",
                personalPhone = "",
                availableServices = arrayListOf()
            )

            val cpNorthDepartment =
                DepartmentModel(
                    id = 4,
                    name = "CP North",
                    contacts = arrayListOf(northFax, northCP, doorCode),
                    email = "communityparamedic@guelph.ca"
                )

            val departments = MutableLiveData<List<DepartmentModel>>()
            departments.value = arrayListOf(
                programCoordinationDepartment,
                communityParamedicDepartment,
                padSpecialistDepartment,
                cpOfficeDepartment,
                cpSouthDepartment,
                cpNorthDepartment
            )

            return departments
        }
    }
}