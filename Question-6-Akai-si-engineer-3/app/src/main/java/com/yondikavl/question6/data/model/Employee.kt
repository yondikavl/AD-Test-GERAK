package com.yondikavl.question6.data.model

import com.google.gson.annotations.SerializedName

data class Employee(
    val id: Int,

    @SerializedName("employee_name")
    val employeeName: String?,

    @SerializedName("employee_salary")
    val employeeSalary: Int?,

    @SerializedName("employee_age")
    val employeeAge: Int?,

    @SerializedName("profile_image")
    val profileImage: String?
)
