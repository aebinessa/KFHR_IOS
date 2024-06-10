import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.ui.theme.DarkGreen
import com.binjesus.kfhr_mobile.utils.Route

@Composable
fun NFCFABButton(navController: NavController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(60.dp)
            .shadow(4.dp, CircleShape) // Add shadow
            .clip(CircleShape)
            .background(DarkGreen)
            .clickable { navController.navigate(Route.NFCRoute) } // Make it clickable and navigate
    ) {
        Icon(
            painter = painterResource(id = R.drawable.card), // Replace with your actual icon resource
            contentDescription = null,
            modifier = Modifier
                .size(30.dp) // Adjust the size of the icon
                .align(Alignment.Center), // Center the icon
            tint = Color.White // Set the icon color
        )
    }
}

@Preview(widthDp = 52, heightDp = 52)
@Composable
private fun NFCNavPreview() {
    val navController = rememberNavController()
    Surface {
        NFCFABButton(navController = navController)
    }
}
