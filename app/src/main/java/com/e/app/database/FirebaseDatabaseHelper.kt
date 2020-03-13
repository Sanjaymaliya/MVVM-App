package com.e.app.database

import android.util.Log
import com.e.app.model.TitleModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FirebaseDatabaseHelper {

    private val database = FirebaseDatabase.getInstance()
    private val firebaseDatabaseReference = database.getReference("TitleModel")

    private val titleModelList = mutableListOf<TitleModel>()

    fun writeTitle() {

        val key = firebaseDatabaseReference.push().key
        firebaseDatabaseReference.child(key!!).setValue(TitleModel("Live", "#D81B60", "1"))

    }

    fun readAppointments(status: DataStatus) {
        firebaseDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("DATABASE_ERROR", "Error while reading appointments", error.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                titleModelList.clear()
                for (data in dataSnapshot.children) {
                    titleModelList.add(data.getValue(TitleModel::class.java)!!)
                }

                status.DataIsLoaded(titleModelList)
            }

        })
    }


    interface DataStatus {

        fun DataIsLoaded(appointments: List<TitleModel>)
        fun DataIsInserted()
    }

}