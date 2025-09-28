package com.yondikavl.question6.data.model

data class EmployeeResponse(
    val status: String,
    val data: List<Employee>,
    val message: String
)