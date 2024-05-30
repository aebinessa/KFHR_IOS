import android.app.DatePickerDialog
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.models.Leave
import com.binjesus.kfhr_mobile.network.LeaveRequest
import com.binjesus.kfhr_mobile.network.LeaveService
import com.binjesus.kfhr_mobile.network.RetrofitHelper
import com.binjesus.kfhr_mobile.utils.Route
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel

@Composable
fun ApplyForLeaveScreen(
    navController: NavHostController,
    viewModel: KFHRViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var employeeId by remember { mutableStateOf(0) }
    var leaveType by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val leaveTypes =
        listOf("Sick Leave", "Casual Leave", "Maternity Leave", "Paternity Leave", "Annual Leave")

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    val startDatePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            startDate = dateFormat.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val endDatePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            endDate = dateFormat.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val coroutineScope = rememberCoroutineScope()

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
                    text = "Apply for Leave",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { navController.navigate("Notifications") }) {
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
                    Box {
                        OutlinedTextField(
                            value = leaveType,
                            onValueChange = { leaveType = it },
                            label = { Text("Type of Leave") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { expanded = true },
                            enabled = false,
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.downarrow),
                                    contentDescription = "Dropdown",
                                    modifier = Modifier
                                        .clickable { expanded = true }
                                        .size(18.dp),
                                    tint = Color.Black,
                                )
                            },
                            readOnly = true
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            leaveTypes.forEach { type ->
                                DropdownMenuItem(onClick = {
                                    leaveType = type
                                    expanded = false
                                }) {
                                    Text(text = type)
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = startDate,
                        onValueChange = { startDate = it },
                        label = { Text("Start Date") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { startDatePickerDialog.show() },
                        enabled = false
                    )
                    OutlinedTextField(
                        value = endDate,
                        onValueChange = { endDate = it },
                        label = { Text("End Date") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { endDatePickerDialog.show() },
                        enabled = false
                    )
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notes") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                    )
                }
            }
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            if (viewModel.isLeaveRequestSuccessful) {
                //TODO show message
                isLoading = false
            } else {
                errorMessage = "Failed to apply for leave. Please try again."
                isLoading = false
            }
            Button(
                onClick = {
                    isLoading = true
                    errorMessage = ""
                    viewModel.applyForLeave(
                        employeeId = employeeId,
                        leaveType = leaveType,
                        startDate = startDate,
                        endDate = endDate,
                        notes = notes
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(
                        text = "Apply",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApplyForLeaveScreen() {
    val navController = rememberNavController() // Create a dummy NavHostController for preview
    ApplyForLeaveScreen(navController)

}
