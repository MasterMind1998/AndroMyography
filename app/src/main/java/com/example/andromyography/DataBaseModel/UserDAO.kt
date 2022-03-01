package com.example.andromyography.DataBaseModel

import android.util.Log
import io.realm.Realm

class UserDAO {

    private var realm = Realm.getDefaultInstance()


    fun insertDB(objectUser: ObjectUser) {

        realm.executeTransactionAsync(
            {

                val realmObject = it.createObject(ObjectUser::class.java, objectUser.patientID)
                realmObject.patientName = objectUser.patientName
                realmObject.patientFamily = objectUser.patientFamily
                realmObject.patientAge = objectUser.patientAge
                realmObject.patientPhone = objectUser.patientPhone
                realmObject.patientGender = objectUser.patientGender
                realmObject.patientDamagedMuscle = objectUser.patientDamagedMuscle

            },
            {
                Log.i("REALM_TAG", "Success")
            },
            {
                Log.i("REALM_TAG", "Error ${it.message}")
            }
        )
    }

    fun readByID(id: Long): ObjectUser? =
        realm.where(ObjectUser::class.java).equalTo("patientID", id).findFirst()

    fun editDB(objectUser: ObjectUser){

        realm.executeTransaction {

            it.copyToRealmOrUpdate(objectUser)
        }
    }

    fun closeDB() {

        realm.close()
    }
}