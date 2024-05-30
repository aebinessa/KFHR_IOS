package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.models.Certificate
import com.binjesus.kfhr_mobile.utils.Route
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel
import java.util.*

@Composable
fun CertificatesApp(navController: NavHostController, viewModel: KFHRViewModel = viewModel()) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Route.SubmitCertificateRoute) },
                backgroundColor = Color(0xFF4CAF50),
                contentColor = Color.Black,
                modifier = Modifier.size(56.dp) // Smaller FAB
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Add",
                    tint = Color.Black,
                    modifier = Modifier.size(36.dp) // Change 48.dp to the desired size

                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
                .padding(innerPadding)
        ) {
            Header(navController = navController)
            Spacer(modifier = Modifier.height(16.dp))
            CertificateList(viewModel)
        }
    }
}

@Composable
fun Header(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "My Certificates:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { navController.navigate(Route.NotificationsRoute) }) {
                Icon(
                    painter = painterResource(id = R.drawable.bell),
                    contentDescription = "Notifications",
                    tint = Color(0xFF0B0C0C),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Recommended", color = Color.White)
        }
    }
}

@Composable
fun CertificateList(viewModel: KFHRViewModel = viewModel()) {
    LazyColumn {
        items(viewModel.certificates) { certificate ->
            CertificateCard(certificate)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CertificateCard(certificate: Certificate) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = certificate.certificateName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Issue Date: ${certificate.issueDate}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "Expiration Date: ${certificate.expirationDate}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = certificate.verificationURL,
                fontSize = 14.sp,
                color = Color.Blue,
                modifier = Modifier.clickable { /* Handle URL click */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CertificatesScreenPreview() {
    val navController = rememberNavController()
    CertificatesApp(navController = navController, viewModel = viewModel())
}
