package com.binjesus.kfhr_mobile.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.models.Certificate
import com.binjesus.kfhr_mobile.utils.Route
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CertificateSubmissionScreen(navController: NavHostController,
                                viewModel: KFHRViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current

    var certificateName by remember { mutableStateOf("") }
    var issueDate by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var verificationURL by remember { mutableStateOf("") }

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
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
                        painter = painterResource(id = R.drawable.bell), // Replace with actual icon resource
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
                .padding(16.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = certificateName,
                        onValueChange = { certificateName = it },
                        label = { Text("Certificate Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                    )
                    OutlinedTextField(
                        value = issueDate,
                        onValueChange = { issueDate = it },
                        label = { Text("Certificate Start") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                    )
                    OutlinedTextField(
                        value = expirationDate,
                        onValueChange = { expirationDate = it },
                        label = { Text("Certificate Expiration") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                    )
                    OutlinedTextField(
                        value = verificationURL,
                        onValueChange = { verificationURL = it },
                        label = { Text("Certificate URL") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                    )
                }
            }
            Button(
                onClick = {
                    try {
                        val issueDateParsed = dateFormat.parse(issueDate) ?: throw IllegalArgumentException("Invalid issue date")
                        val expirationDateParsed = dateFormat.parse(expirationDate) ?: throw IllegalArgumentException("Invalid expiration date")
                        val newCertificate = Certificate(
                            id = 0, // Replace with actual ID generation logic
                            employeeId = 1, // Replace with actual employee ID
                            certificateName = certificateName,
                            issueDate = issueDateParsed,
                            expirationDate = expirationDateParsed,
                            verificationURL = verificationURL
                        )
                        viewModel.submitCertificate(newCertificate)
                        Toast.makeText(context, "Certificate submitted successfully", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Upload", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
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
