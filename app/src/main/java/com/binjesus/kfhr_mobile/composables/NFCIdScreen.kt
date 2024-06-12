package com.binjesus.kfhr_mobile.composables

import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.ui.theme.DarkGreen
import com.binjesus.kfhr_mobile.ui.theme.LightGreen
import com.binjesus.kfhr_mobile.utils.Route
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel

@Composable
fun NFCIdScreen(navController: NavHostController, viewModel: KFHRViewModel) {
    val context = LocalContext.current
    // Get the URI of the system notification sound
    val notificationSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    // Initialize MediaPlayer
    val mediaPlayerCheckIn = remember { MediaPlayer.create(context, notificationSoundUri) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Custom Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(DarkGreen), // Replace with your desired background color
            contentAlignment = Alignment.TopStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material.IconButton(onClick = { navController.popBackStack() }) {
                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.arrow), // Replace with your back arrow icon
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                androidx.compose.material.Text(
                    text = viewModel.employee?.employeeId.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                androidx.compose.material.IconButton(onClick = { /* Handle notifications click */ }) {
                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.bell), // Replace with your notification icon
                        contentDescription = "Notifications",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-60).dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberImagePainter(viewModel.employee?.employeePic),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )
        }

        androidx.compose.material.Text(
            text = viewModel.employee?.employeeName.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp)) // Adjust space to move content down

        Image(
            painter = painterResource(id = R.drawable.nfc),
            contentDescription = "Scan Icon",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .clickable(interactionSource = remember { MutableInteractionSource() },
                    indication = null) {
                    viewModel.checkIn()
                    Toast.makeText(context, "Congrats! you have checked in", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                    mediaPlayerCheckIn.start()

                }
        )
        Image(
            painter = painterResource(id = R.drawable.kfh), // Replace with your logo resource
            contentDescription = "Kuwait Finance House Logo",
            modifier = Modifier
                .size(250.dp)
                .clickable(interactionSource = remember { MutableInteractionSource() },
                    indication = null) {
                    viewModel.checkOut()
                    Toast.makeText(context, "Good Job! you have checked out", Toast.LENGTH_SHORT).show()
                    mediaPlayerCheckIn.start()
                    navController.popBackStack()
                }
        )
    }
}

