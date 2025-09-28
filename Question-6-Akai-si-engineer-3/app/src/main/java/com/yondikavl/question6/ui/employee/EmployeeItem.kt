package com.yondikavl.question6.ui.employee

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yondikavl.question6.data.model.Employee

@Composable
fun EmployeeItem(employee: Employee) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(2.dp, Color(color = 0xFFE5E6F2)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Name: ${employee.employeeName}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Text(text = "Age: ${employee.employeeAge}", color = Color.DarkGray)
            Text(text = "Salary: ${employee.employeeSalary}", color = Color.DarkGray)
        }
    }
}
