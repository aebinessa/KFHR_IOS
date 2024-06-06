import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: KFHRViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

           HomeTopBar(viewModel = viewModel, navController = navController)

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var timeLeft = ""
                    var isCheckout = viewModel.attendance?.checkOutDateTime
                    var timeLeftTitle = "Please check in to see time left"
                    if (isCheckout != null) {
                        timeLeft =
                            calculateTimeLeft(viewModel.attendance?.checkOutDateTimeObject()!!)
                        timeLeftTitle = "Time left till check out"
                    }

                    Text(
                        text = timeLeft,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = timeLeftTitle,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var checkInTime = "--:--"
                var checkOutTime = "--:--"
                if (viewModel.attendance != null) {
                    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    checkInTime = timeFormat.format(viewModel.attendance!!.checkInDateTimeObject())
                    checkOutTime = timeFormat.format(viewModel.attendance!!.checkOutDateTimeObject())

                }

                    CheckInOutCard(
                        time = checkInTime,
                        label = "Check In"
                    )

                    CheckInOutCard(
                        time = checkOutTime,
                        label = "Check Out"
                    )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.clock), // Replace with actual image resource
                        contentDescription = "Clock",
                        modifier = Modifier.size(60.dp) // Smaller size
                    )
                    Text(
                        text = "Late Minutes Left : ${viewModel.lateMinutesLeft?.minutesLeft}",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
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
        Image(
            painter = rememberImagePainter(data = viewModel.employee?.employeePic),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(70.dp)
                .background(Color.Gray, shape = CircleShape),
            contentScale = ContentScale.Crop
        )
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
fun CheckInOutCard(time: String, label: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
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


fun calculateTimeLeft(checkOutDateTime: Date): String {
    val currentTime = Date()
    val diff = checkOutDateTime.time - currentTime.time
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
