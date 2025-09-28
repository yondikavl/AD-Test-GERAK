package com.yondikavl.question6.data.repository

import com.yondikavl.question6.data.remote.RetrofitInstance

class EmployeeRepository {
    suspend fun getEmployees() = RetrofitInstance.api.getEmployees()
}
