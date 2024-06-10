import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.utils.Route
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timer

val DarkGreen = Color(0xFF006400)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: KFHRViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var isCheckedOut = viewModel.attendance?.checkOutTime != null
    var timeLeft by remember { mutableStateOf(calculateTimeLeft(isCheckedOut)) }
    timer(initialDelay = 1000L, period = 1000L) {
        timeLeft = calculateTimeLeft(isCheckedOut)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            HomeTopBar(viewModel = viewModel, navController = navController)

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Adjusted to be wider
                        .border(2.dp, DarkGreen, RoundedCornerShape(8.dp))
                ) {
                    CombinedCheckInOutCard(viewModel = viewModel, timeLeft = timeLeft)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, DarkGreen, RoundedCornerShape(8.dp))
                ) {
                    LateMinutesCard(viewModel = viewModel)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        NFCFABButton(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomEnd) // Align to the bottom right
                .padding(16.dp) // Add padding from the edges
        )
    }
}

@Composable
fun HomeTopBar(viewModel: KFHRViewModel, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(60.dp) // Total size with border
                .background(DarkGreen, shape = CircleShape) // Black border
                .padding(3.dp) // Adjust the padding to control border thickness
        ) {
            Image(
                painter = rememberImagePainter(data = viewModel.employee?.employeePic),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(54.dp) // Size of the image inside the border
                    .background(Color.Gray, shape = CircleShape)
                    .clip(CircleShape), // Ensure the image is clipped to a circle shape
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        var name = ""
        if (viewModel.employee?.employeeName != null) {
            name = viewModel.employee?.employeeName!!
        }
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(24.dp))

        IconButton(onClick = { navController.navigate(Route.NotificationsRoute) }) {
            Icon(
                painter = painterResource(id = R.drawable.bell),
                contentDescription = "Notifications",
                tint = Color.Black,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun CombinedCheckInOutCard(viewModel: KFHRViewModel, timeLeft: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var timeLeftTitle = "Please check in to see time left"
            if (viewModel.attendance?.checkInTime != null) {
                timeLeftTitle = "Time left till check out"
            }

            Text(
                text = timeLeftTitle,
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = timeLeft,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                var checkInTime = "--:--"
                var checkOutTime = "--:--"
                if (viewModel.attendance != null) {
                    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    Log.e("SSSSS", viewModel.attendance!!.checkInTime.toString())
                    Log.e("SSSSS", viewModel.attendance!!.checkOutTime.toString())
                    if (viewModel.attendance!!.checkInTime != null)
                        checkInTime =
                            timeFormat.format(viewModel.attendance!!.checkInDateTimeObject())
                    if (viewModel.attendance!!.checkOutTime != null)
                        checkOutTime =
                            timeFormat.format(viewModel.attendance!!.checkOutDateTimeObject())
                }

                // Using weight modifier correctly inside Row scope
                CheckInOutCard(
                    time = checkInTime,
                    label = "Check In",
                    modifier = Modifier.weight(1f)
                )

                CheckInOutCard(
                    time = checkOutTime,
                    label = "Check Out",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun LateMinutesCard(viewModel: KFHRViewModel) {
    val progress = viewModel.lateMinutesLeft?.minutesLeft?.toFloat()?.div(60) ?: 0f

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier.size(60.dp)) {
                drawArc(
                    color = Color.Red,
                    startAngle = -90f,
                    sweepAngle = progress * 360,
                    useCenter = true
                )
                drawArc(
                    color = Color.Gray,
                    startAngle = -90f + progress * 360,
                    sweepAngle = (1 - progress) * 360,
                    useCenter = true,
                    style = Stroke(width = 8.dp.toPx())
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Late Minutes Left : ${viewModel.lateMinutesLeft?.minutesLeft ?: "--"}",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun CheckInOutCard(time: String, label: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = time,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
    }
}

fun calculateTimeLeft(isCheckedOut: Boolean): String {
    val currentTime = Date()
    val calendar = Calendar.getInstance()
    calendar.time = currentTime

    // Set the time to 3 PM today
    calendar.set(Calendar.HOUR_OF_DAY, 15)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    if (currentTime.after(calendar.time) || isCheckedOut) {
        calendar.time = currentTime
    }

    val threePmToday = calendar.time
    val diff = threePmToday.time - currentTime.time

    val hours = (diff / (1000 * 60 * 60)).toInt()
    val minutes = ((diff / (1000 * 60)) % 60).toInt()
    val seconds = ((diff / 1000) % 60).toInt()

    return String.format("%02d:%02d:%02d hrs", hours, minutes, seconds)
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController)
}
