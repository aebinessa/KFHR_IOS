package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.models.RecommendedCertificate
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel

@Composable
fun RecommendedCertificatesScreen(navController: NavHostController,
                                  viewModel: KFHRViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
                                  onCertificateClick: (RecommendedCertificate) -> Unit,
                                  onViewAllClick: () -> Unit) {
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
                    text = "Recommended Certificates",
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
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                items(viewModel.recommendedCertificates) { certificate ->
                    RecommendedCertificateCard(certificate = certificate, onClick = { onCertificateClick(certificate) })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            FloatingActionButton(
                onClick = { navController.navigate("MyCertificates") },
                backgroundColor = Color(0xFF4CAF50),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = 64.dp,
                        end = 16.dp
                    ) // Adjust the padding to position the button above the bottom bar
                    .size(75.dp)
                    .shadow(elevation = 20.dp, shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.certificate),
                    contentDescription = "earned certificates",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun RecommendedCertificateCard(certificate: RecommendedCertificate, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = certificate.certificatePicture,
                contentDescription = "Certificate Image",
                placeholder = painterResource(R.drawable.warning),
                error = painterResource(R.drawable.certificate),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = certificate.certificateName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Issued by ${certificate.issuingOrganization}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Reward Points: ${certificate.rewardPoints}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewRecommendedCertificatesScreen() {
    val navController = rememberNavController()

    RecommendedCertificatesScreen(navController, onCertificateClick = { certificate ->
        // Handle certificate click (navigate to detail screen)
    }, onViewAllClick = {
        // Handle view all click (navigate to view all certificates screen)
    })
}
