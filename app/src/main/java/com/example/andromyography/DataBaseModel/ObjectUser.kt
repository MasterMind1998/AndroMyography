package com.example.andromyography.DataBaseModel

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ObjectUser: RealmObject() {

    @PrimaryKey
    var patientID: Long? = null

    lateinit var patientName: String

    lateinit var patientFamily: String

    internal var patientPhone: Long? = null

    internal var patientAge: Int? = null

    lateinit var patientGender: String

    lateinit var patientDamagedMuscle: String
}