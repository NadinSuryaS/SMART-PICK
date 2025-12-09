package com.smartpick.mysp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartpick.mysp.data.AdminCredentialsManager
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.SurfaceDark

@Composable
fun AdminLoginDialog(
    onLoginSuccess: () -> Unit,
    onCancel: () -> Unit
) {
    var adminName by remember { mutableStateOf("") }
    var adminPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var loginAttempts by remember { mutableStateOf(0) }
    var isLocked by remember { mutableStateOf(false) }

    // Lock after 3 failed attempts
    val maxAttempts = 3

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1A0F2E),
                            Color(0xFF0F0F1E)
                        )
                    )
                ),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Icon
                Text(
                    text = "🔐",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Title
                Text(
                    text = "Admin Access Required",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle
                Text(
                    text = "Enter your admin credentials to continue",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(28.dp))

                if (isLocked) {
                    // Locked State
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFFFF6B6B).copy(alpha = 0.15f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "🚫",
                                fontSize = 32.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Account Locked",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF6B6B)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Too many failed attempts.\nPlease try again later.",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.7f),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                } else {
                    // Admin Name Input
                    OutlinedTextField(
                        value = adminName,
                        onValueChange = {
                            adminName = it
                            errorMessage = ""
                        },
                        label = { Text("Admin Name", color = Color.White.copy(alpha = 0.7f)) },
                        placeholder = { Text("Enter admin name", color = Color.White.copy(alpha = 0.5f)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = !isLocked,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                            focusedLabelColor = PrimaryBlue,
                            unfocusedLabelColor = Color.White.copy(alpha = 0.5f),
                            cursorColor = PrimaryBlue
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Admin Password Input
                    OutlinedTextField(
                        value = adminPassword,
                        onValueChange = {
                            adminPassword = it
                            errorMessage = ""
                        },
                        label = { Text("Admin Password", color = Color.White.copy(alpha = 0.7f)) },
                        placeholder = { Text("Enter password", color = Color.White.copy(alpha = 0.5f)) },
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(
                                onClick = { showPassword = !showPassword },
                                enabled = !isLocked
                            ) {
                                Text(
                                    text = if (showPassword) "👁️" else "👁️‍🗨️",
                                    fontSize = 18.sp
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = !isLocked,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                            focusedLabelColor = PrimaryBlue,
                            unfocusedLabelColor = Color.White.copy(alpha = 0.5f),
                            cursorColor = PrimaryBlue
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    // Error Message
                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color(0xFFFF6B6B).copy(alpha = 0.15f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "⚠️",
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = errorMessage,
                                    fontSize = 12.sp,
                                    color = Color(0xFFFF6B6B),
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    // Attempt Counter
                    if (loginAttempts > 0 && loginAttempts < maxAttempts) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Attempts remaining: ${maxAttempts - loginAttempts}",
                            fontSize = 11.sp,
                            color = Color(0xFFFF9800),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onCancel,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isLocked
                    ) {
                        Text(
                            "Cancel",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Button(
                        onClick = {
                            if (isLocked) {
                                return@Button
                            }

                            // Validation
                            when {
                                adminName.isEmpty() -> {
                                    errorMessage = "Please enter admin name"
                                }
                                adminPassword.isEmpty() -> {
                                    errorMessage = "Please enter password"
                                }
                                else -> {
                                    // Validate credentials directly
                                    val credentials = AdminCredentialsManager.validateCredentials(
                                        adminName.trim(),
                                        adminPassword.trim()
                                    )

                                    if (credentials != null) {
                                        // Success
                                        onLoginSuccess()
                                    } else {
                                        // Failed
                                        loginAttempts++
                                        errorMessage = "Invalid admin credentials"

                                        if (loginAttempts >= maxAttempts) {
                                            isLocked = true
                                            errorMessage = "Account locked due to too many failed attempts"
                                        }

                                        adminPassword = ""
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue,
                            disabledContainerColor = PrimaryBlue.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isLocked
                    ) {
                        Text(
                            "Login",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

            }
        }
    }
}
