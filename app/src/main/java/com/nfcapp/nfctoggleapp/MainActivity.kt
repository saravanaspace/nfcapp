package com.nfcapp.nfctoggleapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.nfc.NfcAdapter
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nfcapp.nfctoggleapp.ui.theme.NfcAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check if NFC is supported
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)) {
            Toast.makeText(this, "NFC is not supported on this device", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Directly open NFC settings
        try {
            val intent = Intent(Settings.ACTION_NFC_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Could not open NFC settings", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening NFC settings", Toast.LENGTH_SHORT).show()
        }
        
        // Close the app after opening settings
        finish()
    }
}

@Composable
fun NfcToggleScreen(nfcAdapter: NfcAdapter?) {
    val context = LocalContext.current
    var isNfcEnabled by remember { mutableStateOf(nfcAdapter?.isEnabled == true) }
    var showNfcNotSupportedMessage by remember { mutableStateOf(nfcAdapter == null) }
    var userDesiredNfcState by remember { mutableStateOf(isNfcEnabled) }

    // Effect to check NFC availability and state
    LaunchedEffect(key1 = nfcAdapter) {
        if (nfcAdapter == null) {
            showNfcNotSupportedMessage = true
        } else {
            showNfcNotSupportedMessage = false
            isNfcEnabled = nfcAdapter.isEnabled
            userDesiredNfcState = isNfcEnabled
        }
    }

    // Periodically check NFC state
    LaunchedEffect(Unit) {
        while (true) {
            try {
                delay(2000) // Check every 2 seconds
                withContext(Dispatchers.Main) {
                    if (nfcAdapter != null && nfcAdapter.isEnabled != isNfcEnabled) {
                        isNfcEnabled = nfcAdapter.isEnabled
                        userDesiredNfcState = isNfcEnabled
                    }
                }
            } catch (e: Exception) {
                // Handle any exceptions during NFC state check
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error checking NFC state", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showNfcNotSupportedMessage) {
            Text(
                text = "NFC is not supported on this device",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            return@Column
        }

        // NFC Icon
        Icon(
            imageVector = Icons.Filled.Nfc,
            contentDescription = "NFC Status Icon",
            modifier = Modifier.size(64.dp),
            tint = if (isNfcEnabled) 
                MaterialTheme.colorScheme.primary 
            else 
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Status Text
        Text(
            text = "NFC is ${if (isNfcEnabled) "ON" else "OFF"}",
            style = MaterialTheme.typography.headlineMedium,
            color = if (isNfcEnabled) 
                MaterialTheme.colorScheme.primary 
            else 
                MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Toggle Switch
        Switch(
            checked = userDesiredNfcState,
            onCheckedChange = { desiredState ->
                userDesiredNfcState = desiredState
                if (desiredState != isNfcEnabled) {
                    try {
                        redirectToNfcSettings(context)
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Error opening NFC settings",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Helper Text
        Text(
            text = when {
                userDesiredNfcState && !isNfcEnabled -> 
                    "Tap switch to open NFC settings and turn ON"
                !userDesiredNfcState && isNfcEnabled -> 
                    "Tap switch to open NFC settings and turn OFF"
                else -> 
                    "NFC is ${if (isNfcEnabled) "enabled" else "disabled"}"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@SuppressLint("QueryPermissionsNeeded")
private fun redirectToNfcSettings(context: Context) {
    try {
        val intent = Intent(Settings.ACTION_NFC_SETTINGS).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            throw Exception("NFC settings not found")
        }
    } catch (e: Exception) {
        throw Exception("Failed to open NFC settings: ${e.message}")
    }
}

@Preview(showBackground = true, name = "NFC Toggle Screen - Enabled")
@Composable
fun NfcToggleScreenPreviewEnabled() {
    NfcAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Nfc,
                    contentDescription = "NFC Status Icon",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Opening NFC Settings...",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "NFC Toggle Screen - Disabled")
@Composable
fun NfcToggleScreenPreviewDisabled() {
    NfcAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Nfc,
                    contentDescription = "NFC Status Icon",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "NFC is OFF",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(24.dp))

                Switch(
                    checked = false,
                    onCheckedChange = { }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "NFC is disabled",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "NFC Toggle Screen - Not Supported")
@Composable
fun NfcToggleScreenPreviewNotSupported() {
    NfcAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "NFC is not supported on this device",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}