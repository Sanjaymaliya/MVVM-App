package com.e.app.database

import android.util.Log
import com.e.app.model.ContactUs
import com.e.app.model.ContentAmount
import com.e.app.model.ContestModel
import com.e.app.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FirebaseDatabaseHelper {

    private val database = FirebaseDatabase.getInstance()

    var currentUser = FirebaseAuth.getInstance().currentUser

    private val firebaseDatabaseUser = database.getReference("User")

    private val firebaseDatabaseReferenceAmount = database.getReference("Amount")

    private val firebaseDatabaseReferenceContest = database.getReference("Contest")

    val firebaseDatabaseReferenceContestUs = database.getReference("ContactUs")

    private val contentAmountList = mutableListOf<ContentAmount>()

    private val contestList = mutableListOf<ContestModel>()

    fun writeContest() {
        //seReferenceContest.push().key

     /*   var cont = ContestModel()

        cont.dateTime = "20-03-2020 10:00:PM"
        cont.map = "Erangel"
        cont.price = "50"
        cont.type = "TPP"
        cont.winAmount = "500"
        firebaseDatabaseReferenceContest.child(type!!)
            .setValue(cont)*/

        var cont = ContactUs("+919724365084","hello@gmail.com","63  New India House,\nst Floor,\nTirbhuvan Road, Girgaum\nMumbai,  Maharashtra\n400004")

        firebaseDatabaseReferenceContest.child("contact")
            .setValue(cont)

    }

    fun writeAmount(id: String, type: String, mModel: ContentAmount, status: UserStatus) {
        val key = firebaseDatabaseReferenceAmount.push().key
        mModel.id = key!!
        firebaseDatabaseReferenceAmount.child(id).child(type).setValue(mModel)
            .addOnSuccessListener {
                status.onSuccess()
            }
            .addOnFailureListener {
                status.onError()
            }
    }


    fun writeUserDetail(model: UserModel, userId: String, status: UserStatus) {
        firebaseDatabaseUser.child(userId)
            .setValue(model, object : DatabaseReference.CompletionListener {
                override fun onComplete(firebaseError: DatabaseError?, p1: DatabaseReference) {
                    if (firebaseError != null) {
                        status.onSuccess()
                    } else {
                        status.onError()
                    }
                }

            })
    }

    interface DataStatus {

        fun DataIsLoaded(appointments: List<Any>)
        fun DataIsInserted()
        fun onError()
    }

    interface UserStatus {
        fun onSuccess()
        fun onError()
    }

    interface UserInfo {
        fun onSuccess(mModel:Any)
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
                    } else {
                        contestList.clear()
                        contestList.add(contestModel)
                        status.DataIsLoaded(contestList)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    status.onError()
                }
            })
    }

    fun readUserAmount(status: DataStatus, uId: String, Type: String) {

        firebaseDatabaseReferenceAmount.child(uId).child(Type)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val contentAmount: ContentAmount? =
                        dataSnapshot.getValue<ContentAmount>(ContentAmount::class.java) as ContentAmount?
                    if (contentAmount == null) {
                        status.onError()
                        Log.e("Nish Error", "Erroor")
                    } else {
                        Log.e("Nish SUcess", contentAmount.amount)
                        contentAmountList.clear()
                        contentAmountList.add(contentAmount)
                        status.DataIsLoaded(contentAmountList)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    status.onError()
                }
            })

    }

    fun addUserChangeListener(status: UserInfo, uId: String) {
        this.firebaseDatabaseUser.child(uId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userModel: UserModel? =
                        dataSnapshot.getValue<UserModel>(UserModel::class.java) as UserModel?
                    if (userModel == null) {
                        status.onError()
                    } else {
                        status.onSuccess(userModel)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    status.onError()
                    Log.e("Cancel Data", "Failed to read user", error.toException())
                }
            })
    }

    fun getContactUs(status: UserInfo) {
        firebaseDatabaseReferenceContestUs.child("contact")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val contactUs: ContactUs? =
                        dataSnapshot.getValue<ContactUs>(ContactUs::class.java) as ContactUs?
                    if (contactUs == null) {
                        status.onError()
                    } else {
                        status.onSuccess(contactUs)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    status.onError()
                    Log.e("Cancel Data", "Failed to read user", error.toException())
                }
            })
    }

}