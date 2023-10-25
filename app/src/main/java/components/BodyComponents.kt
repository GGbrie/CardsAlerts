package components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SpaceH(size: Dp = 10.dp){
    Spacer(modifier = Modifier.height(size))
}

@Composable
fun SpaceW(size: Dp = 10.dp){
    Spacer(modifier = Modifier.width(size))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTextField(value: String, onValueChange: (String) -> Unit, label: String){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text(text = label, fontSize = when(label) {
            "Calculadora Prestamos" -> 15.sp
            "Prestamo", "Cuotas", "Tasa" -> 20.sp
            else -> MaterialTheme.typography.bodyLarge.fontSize
        })},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    )
}

@Composable
fun MainButton(text: String, color: Color = Color(0xFFD3771B), onClick: () -> Unit){
    OutlinedButton( onClick = onClick, colors = ButtonDefaults.outlinedButtonColors(
        contentColor = color, containerColor = Color.Transparent
    ),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)
    ){
        Text(text = text, fontSize = 15.sp)
    }
}

@Composable
fun Alert(
    title: String, message: String,
    confirmText: String, onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismissClick,
        title = {Text(text = title)},
        text = {Text(text = message)},
        confirmButton = {
            Button(onClick = {onConfirmClick()}){
                Text(text = confirmText)
            }
        }
    )
}
