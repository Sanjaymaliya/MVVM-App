package com.e.app.database

import com.e.app.model.TitleModel
import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseHelper {

    private val database = FirebaseDatabase.getInstance()
    private val appointmentRef = database.getReference("TitleModel")

    private val titleModelList = mutableListOf<TitleModel>()

    fun writeTitle() {

        val key = appointmentRef.push().key
        appointmentRef.child(key!!).setValue(TitleModel("Live","#D81B60","1"))

    }

    interface DataStatus {

        fun DataIsLoaded(appointments : List<TitleModel>)
        fun DataIsInserted()
    }

}