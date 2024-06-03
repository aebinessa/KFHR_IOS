package com.binjesus.kfhr_mobile.composables

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DatePickerTextField(
    modifier: Modifier = Modifier,
    title: String = "Select Date",
    date: MutableState<String>
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val startDatePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            date.value = dateFormat.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    OutlinedTextField(
        value = date.value,
        onValueChange = { date.value = it },
        label = { Text(title) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { startDatePickerDialog.show() },
        enabled = false,
        readOnly = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)

    )
}