package com.binjesus.kfhr_mobile.composables

import android.text.format.DateUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.models.Certificate
import com.binjesus.kfhr_mobile.ui.theme.DarkGreen
import com.binjesus.kfhr_mobile.ui.theme.LightGreen
import com.binjesus.kfhr_mobile.utils.Route
import com.binjesus.kfhr_mobile.utils.convertDateToString
import com.binjesus.kfhr_mobile.utils.convertStringToDate
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CertificateSubmissionScreen(
    navController: NavHostController,
    viewModel: KFHRViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current

    var certificateName by remember { mutableStateOf("") }
    var issueDate = remember { mutableStateOf("") }
    var expirationDate = remember { mutableStateOf("") }
    var verificationURL by remember { mutableStateOf("") }

    if (viewModel.isSubmitedCertificateSuccessful) {

        Toast.makeText(context, "Certificate submitted successfully", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
        viewModel.isSubmitedCertificateSuccessful = false
    }


    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightGreen)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Submit your Certificates:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { navController.navigate(Route.NotificationsRoute) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.bell),
                        contentDescription = "Notifications",
                        tint = Color.Black,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(LightGreen)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            MyOutlineTextField(value = certificateName,
                onValueChange = { certificateName = it },
                label = "Certificate Name")

            DatePickerTextField(date = issueDate, title = "Certificate Start")
            DatePickerTextField(date = expirationDate, title = "Certificate Expiration")

            MyOutlineTextField(value = verificationURL,
                onValueChange = { verificationURL = it },
                label = "Certificate Url")

            Button(
                onClick = {
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                    val issueDateParsed = dateFormat.parse(issueDate.value)
                        ?: throw IllegalArgumentException("Invalid issue date")
                    val expirationDateParsed = dateFormat.parse(expirationDate.value)
                        ?: throw IllegalArgumentException("Invalid expiration date")
                    val newCertificate = Certificate(
                        id = 0,
                        employeeId = viewModel.employee!!.employeeId, // Replace with actual employee ID
                        certificateName = certificateName,
                        issueDate = convertDateToString(issueDateParsed),
                        expirationDate = convertDateToString(expirationDateParsed),
                        verificationURL = verificationURL
                    )
                    viewModel.submitCertificate(newCertificate)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGreen),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Upload",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCertificateSubmissionScreen() {
    val navController = rememberNavController()
    CertificateSubmissionScreen(navController)
}
