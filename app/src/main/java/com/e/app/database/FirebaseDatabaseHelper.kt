package com.e.app.database

import android.content.Intent
import android.util.Log
import android.widget.Toast
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

    var zone1Ref = firebaseDatabaseReferenceContest.child("solo")

    private val titleModelList = mutableListOf<ContentAmount>()

    private val titleModelList1 = mutableListOf<ContestModel>()

    private val contestList = mutableListOf<ContestModel>()

    fun writeContest(type: String) {
        //val key = firebaseDatabaseReferenceContest.push().key

        var cont = ContestModel()

        cont.dateTime = "20-03-2020 10:00:PM"
        cont.map = "Erangel"
        cont.price = "₹ 50"
        cont.type = "TPP"
        cont.winAmount = "₹ 500"
        firebaseDatabaseReferenceContest.child(type!!)
            .setValue(cont)

    }


    fun writeAmount(id:String,type: String) {
        //val key = firebaseDatabaseReferenceContest.push().key

        //ContentAmount("101","25")
        firebaseDatabaseReferenceAmount.child(id).child(type).setValue(ContentAmount("101", "50"))
    }


    interface DataStatus {

        fun DataIsLoaded(appointments: List<Any>)
        fun DataIsInserted()
        fun onError()
    }

    fun readContest(status: DataStatus, Type: String) {
        this.firebaseDatabaseReferenceContest.child(Type)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val contestModel: ContestModel? =
                        dataSnapshot.getValue<ContestModel>(ContestModel::class.java) as ContestModel?
                    if (contestModel == null) {
                        status.onError()
                    }
                    else
                    {
                        contestList.add(contestModel)
                        status.DataIsLoaded(contestList)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    status.onError()
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

    fun addUserChangeListener() {
        this.firebaseDatabaseReferenceContest.child("solo")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val udateChacker: ContestModel? =
                        dataSnapshot.getValue<ContestModel>(ContestModel::class.java) as ContestModel?

                    if (udateChacker == null) {
                        Log.e("TAg C", "User data is null!")
                        Log.e("Nishh ->",""+udateChacker!!.dateTime)
                    }
                    else
                    {
                        Log.e("Nishh ->",""+udateChacker!!.dateTime)
                        Log.e("TAg C", "User data is null!")
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Cancel Data", "Failed to read user", error.toException())
                }
            })
    }


}