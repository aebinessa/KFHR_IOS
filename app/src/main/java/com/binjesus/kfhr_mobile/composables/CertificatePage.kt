import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import com.binjesus.kfhr_mobile.R

class CertificatesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CertificatesApp()
        }
    }
}

@Composable
fun CertificatesApp() {
    Scaffold(
        bottomBar = { BottomNavigationBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add certificate action */ },
                backgroundColor = Color.Green,
                contentColor = Color.White
            ) {
                Text("+", fontSize = 24.sp)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false,
    ) { innerPadding ->
        // Add padding provided by scaffold to avoid overlapping with FAB
        Box(modifier = Modifier.padding(innerPadding)) {
            CertificatesScreen()
        }
    }
}

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        BottomNavItem("HOME", R.drawable.home, false),
        BottomNavItem("ATTENDANCE", R.drawable.security, false),
        BottomNavItem("LEAVES", R.drawable.document, false),
        BottomNavItem("CERTS", R.drawable.onlinecertificate, true),
        BottomNavItem("DIRECTORY", R.drawable.agenda, false)
    )

    BottomNavigation(
        backgroundColor = Color(0xFF4CAF50)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = item.selected,
                onClick = { /* Handle navigation click */ },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 8.sp,
                        fontWeight = if (item.selected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                modifier = Modifier.width(70.dp)
            )
        }
    }
}

@Composable
fun CertificatesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header()
        Spacer(modifier = Modifier.height(16.dp))
        CertificateList()
    }
}

@Composable
fun Header() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "My Certificates:",
            fontSize = 24.sp,
            fontWeight =  FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = { /* Recommended action */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Text("Recommended", color = Color.White)
        }
        IconButton(onClick = { /* Notification action */ }) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.bell),
                contentDescription = "Notifications"
            )
        }
    }
}

@Composable
fun CertificateList() {
    val certificates = List(5) { "Certificate ${it + 1}" }
    LazyColumn {
        items(certificates) { certificate ->
            CertificateCard(certificate)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CertificateCard(certificate: String) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = Color(0xFFF5F5F5) // light gray background color
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp)
        ) {
            Text(
                text = certificate,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = "Supporting line text lorem ipsum dolor sit amet...",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

// Example data class for BottomNavItem
//data class BottomNavItem(val label: String, val iconRes: Int, val selected: Boolean)

@Preview(showBackground = true)
@Composable
fun CertificatesScreenPreview() {
    CertificatesApp()
}
