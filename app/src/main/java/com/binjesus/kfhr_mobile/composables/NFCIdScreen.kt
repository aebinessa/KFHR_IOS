package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.utils.Route
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel

@Composable
fun NFCIdScreen(navController: NavHostController, viewModel: KFHRViewModel) {
    Surface(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF4CAF50))
                )

                // Back button
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

                // Notification button
                IconButton(
                    onClick = { navController.navigate(Route.NotificationsRoute)},
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bell),
                        contentDescription = "Notifications",
                        tint = Color.Black
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 48.dp)
                ) {
                    Text(
                        text = viewModel.employee?.employeeId.toString(),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AsyncImage(
                        model = viewModel.employee?.employeePic?.ifEmpty { "https://example.com/profile.jpg" },
                        contentDescription = "Profile Picture",
                        placeholder = painterResource(R.drawable.user),
                        error = painterResource(R.drawable.warning),
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(120.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    viewModel.employee?.employeeName?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = viewModel.employee?.employeeDepartmentId.toString(),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.nfc),
                contentDescription = "Scan Icon",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Image(
                painter = painterResource(id = R.drawable.kfh),
                contentDescription = "KFH Logo",
                modifier = Modifier
                    .size(300.dp)  // Increased size of the KFH Logo image
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

