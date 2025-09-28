package com.yondikavl.question6.data.remote

import com.yondikavl.question6.data.model.EmployeeResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/employees")
    suspend fun getEmployees(): EmployeeResponse
}
