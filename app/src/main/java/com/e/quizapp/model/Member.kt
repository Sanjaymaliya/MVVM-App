package com.e.quizapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Member {
    @SerializedName("memberId")
    @Expose
    var members: String? = null
}

