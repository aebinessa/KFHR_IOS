package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.models.Employee
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun SignInScreen(navController: NavController) {
    var employeeId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "KFHR",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFF078544),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.kfh_logo),
            contentDescription = "KFHR Logo",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = employeeId,
            onValueChange = { employeeId = it },
            shape = RoundedCornerShape(14.dp),
            label = { Text(text = "Employee ID") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            shape = RoundedCornerShape(14.dp),
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it }
            )
            Text(
                text = "Remember Me",
                modifier = Modifier.clickable { rememberMe = !rememberMe }
            )
        }

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        errorMessage = ""
                        // Call backend API for sign-in
                        val result = signIn(employeeId, password)
                        if (result != null) {
                            // Navigate to another screen or handle successful sign-in
                            // For example: navController.navigate("HomeScreen")
                        } else {
                            errorMessage = "Invalid Employee ID or Password"
                        }
                        isLoading = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF078544)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Forgot Password? Contact IT",
            fontSize = 16.sp,
            color = Color.Blue,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable { navController.navigate("ContactIT") }
        )
    }
}

// API Call goes down here
suspend fun signIn(employeeId: String, password: String): Employee? {
    // Simulate a network call
    // Replace with actual sign-in logic
    return if (employeeId == "test" && password == "password") {
        Employee(1, password, "Test User", "Role", "email@example.com", "1234567890", Date(), "Male", "profilePicURL", 1, 1, 1, 0)
    } else {
        null
    }
}