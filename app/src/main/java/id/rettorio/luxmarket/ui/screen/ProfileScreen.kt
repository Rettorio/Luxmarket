package id.rettorio.luxmarket.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.rettorio.luxmarket.R
import id.rettorio.luxmarket.ui.vm.ProfileViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel
) {
    val profile by viewModel.profile.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .padding(horizontal = 18.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Profile", style = MaterialTheme.typography.headlineSmall)
        }
        Spacer(Modifier.height(4.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0XFFfaf8f8)

                ),
                modifier = Modifier
                    .padding(top = 60.dp)
                    .padding(horizontal = 8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Spacer(Modifier.height(68.dp))
                Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(profile.name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text(profile.gender, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.tertiary)
                }
                Spacer(Modifier.height(24.dp))
                Column(
                    modifier = Modifier.padding(start = 32.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, null, tint = MaterialTheme.colorScheme.tertiaryContainer, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(12.dp))
                        Text(profile.address, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.tertiary)
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Email, null, tint = MaterialTheme.colorScheme.tertiaryContainer, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(12.dp))
                        Text(profile.email, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.tertiary)
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(R.drawable.outline_phone_android_24), null, tint = MaterialTheme.colorScheme.tertiaryContainer, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(12.dp))
                        Text(profile.phoneNumber, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.tertiary)
                    }
                }
                Spacer(Modifier.height(24.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = viewModel::randomUser) {
                        Text("Random User  ")
                        Icon(painterResource(R.drawable.icons8_dice_30), null, modifier = Modifier.size(24.dp))
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .crossfade(true)
                        .data(profile.photo)
                        .build(),
                    contentDescription = "photos of ${profile.name}",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(120.dp)
                )
            }
        }
    }
}