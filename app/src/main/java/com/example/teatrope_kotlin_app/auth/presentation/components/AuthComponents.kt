package com.example.teatrope_kotlin_app.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teatrope_kotlin_app.ui.theme.*

@Composable
fun AuthBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x3325A0FF), SurfaceDeep),
                    center = androidx.compose.ui.geometry.Offset(0.35f, 0.25f),
                    radius = 900f
                )
            )
    ) {
        Box(
            Modifier
                .size(520.dp)
                .align(Alignment.Center)
                .blur(80.dp)
                .background(
                    Brush.radialGradient(listOf(Color(0x333A3F47), Color.Transparent)),
                    CircleShape
                )
        )
        content()
    }
}

@Composable
fun BrandTitle() {
    Text(
        text = "teatrope",
        color = AccentRed,
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp
    )
}

@Composable
fun PanelCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier
            .padding(horizontal = 24.dp, vertical = 28.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0x1AFFFFFF))
            .padding(22.dp)
    ) { content() }
}

@Composable
fun FieldLabel(text: String) {
    Text(text = text, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
}

@Composable
fun FilledField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(color = TextPrimary, fontSize = 14.sp),
        singleLine = true,
        placeholder = { Text(placeholder, color = TextSecondary) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = FieldFill,
            unfocusedContainerColor = FieldFill,
            disabledContainerColor = FieldFill,
            focusedIndicatorColor = Color.Transparent, // Remove indicator
            unfocusedIndicatorColor = Color.Transparent, // Remove indicator
            cursorColor = AccentRed,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedPlaceholderColor = TextSecondary,
            unfocusedPlaceholderColor = TextSecondary
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun PrimaryButton(
    text: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentRed,
            contentColor = Color.White,
            disabledContainerColor = AccentRed.copy(alpha = 0.5f),
            disabledContentColor = Color.White.copy(alpha = 0.7f)
        )
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text)
            Icon(Icons.Rounded.ArrowForward, contentDescription = null)
        }
    }
}

@Composable
fun UnderlineLink(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        color = TextSecondary,
        fontSize = 13.sp,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable { onClick() }
    )
}
