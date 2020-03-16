package com.e.app.database

import android.util.Log
import com.e.app.model.ContentAmount
import com.e.app.model.ContestModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FirebaseDatabaseHelper {

    private val database = FirebaseDatabase.getInstance()

    private val firebaseDatabaseReference = database.getReference("TitleModel")

    private val firebaseDatabaseReferenceAmount = database.getReference("Amount")

    private val firebaseDatabaseReferenceContest = database.getReference("Contest")

    private val titleModelList = mutableListOf<ContentAmount>()

    private val titleModelList1 = mutableListOf<ContestModel>()

    private val contestList = mutableListOf<ContestModel>()

    fun writeContest(type: String) {
        //val key = firebaseDatabaseReferenceContest.push().key

        var cont = ContestModel()

        cont.dateTime = "20-03-2020 10:00:PM"
        cont.map = "50 Rs"
        cont.price = "500"
        cont.type = "Erangel"
        cont.winAmount = "TPP"
        firebaseDatabaseReferenceContest.child(type!!)
            .setValue(cont)

    }


    interface DataStatus {

        fun DataIsLoaded(appointments: List<Any>)
        fun DataIsInserted()
        fun onError()
    }

    fun readContest(status: DataStatus, Type: String) {
        firebaseDatabaseReferenceContest.child(Type)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    status.onError()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    contestList.clear()
                    for (data in dataSnapshot.children) {
                        contestList.add(data.getValue(ContestModel::class.java)!!)
                    }

                    status.DataIsLoaded(contestList)
                }

            })
    }


    fun readAppointments(status: DataStatus) {
        firebaseDatabaseReferenceContest.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("DATABASE_ERROR", "Error while reading appointments", error.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                titleModelList1.clear()
                for (data in dataSnapshot.children) {
                    titleModelList1.add(data.getValue(ContestModel::class.java)!!)
                }

                status.DataIsLoaded(titleModelList1)
            }

        })
    }


    fun readUserAmount(
        firebaseDatabaseHelper: FirebaseDatabaseHelper,
        status: DataStatus, uId: String, Type: String
    ) {

        firebaseDatabaseHelper.firebaseDatabaseReferenceAmount.child(uId).child(Type)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    status.onError()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    firebaseDatabaseHelper.titleModelList.clear()
                    for (data in dataSnapshot.children) {
                        firebaseDatabaseHelper.titleModelList.add(data.getValue(ContentAmount::class.java)!!)
                    }

                    status.DataIsLoaded(firebaseDatabaseHelper.titleModelList)
                }

            })
    }


}