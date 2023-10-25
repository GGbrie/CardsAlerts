package views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.math.RoundingMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import components.Alert
import components.MainButton
import components.MainTextField
import components.ShowInfoCard
import components.SpaceH
import kotlin.math.pow

@Composable
fun ContentHomeView(paddingValues: PaddingValues){
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var montoPrestamo by remember { mutableStateOf("")}
        var cantCuotas by remember { mutableStateOf("")}
        var tasa by remember { mutableStateOf("")}
        var montoInteres by remember { mutableStateOf(0.0)}
        var montoCuota by remember { mutableStateOf(0.0)}
        var showAlert by remember { mutableStateOf(false)}

        ShowInfoCard(
            titleInteres = "Total: ",
            montoInteres = montoInteres,
            titleMonto = "Cuota: ",
            monto = montoCuota
        )
        MainTextField(value = montoPrestamo, onValueChange = { montoPrestamo = it}, label = "Prestamo" )
        SpaceH()
        MainTextField(value = cantCuotas, onValueChange = {cantCuotas = it}, label = "Cuotas")
        SpaceH(10.dp)
        MainTextField(value = tasa, onValueChange = {tasa = it}, label = "Tasa")
        SpaceH(10.dp)

        MainButton(text = "Calcular", color=Color(0xFFFFCC99)){
            if(montoPrestamo != "" && cantCuotas != ""){
                val tasaInteresAnual=tasa.toDouble()
                montoInteres=calcularTotal(montoPrestamo.toDouble(),cantCuotas.toInt(),tasaInteresAnual)
                montoCuota=calcularCuota(montoPrestamo.toDouble(),cantCuotas.toInt(),tasaInteresAnual)
            }else{
                showAlert=true
            }
        }
        SpaceH()
        MainButton(text="Limpiar",color=Color(0xFFFFCC99)){
            montoPrestamo=""
            cantCuotas=""
            tasa=""
            montoInteres=0.0
            montoCuota=0.0
        }
        if(showAlert){
            Alert(title="Alerta",
                message="Ingresa los datos",
                confirmText="Aceptar",
                onConfirmClick={showAlert=false}){}
        }
    }
}

val CreamOrange = Color(0xFFFFCC99)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView() {
    val newColorScheme = MaterialTheme.colorScheme.copy(primary = CreamOrange)
    Scaffold(topBar={
        CenterAlignedTopAppBar(
            title={Text(text="Calculadora de Prestamos",color=Color.White, fontSize = 30.sp)},
            colors=TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor=newColorScheme.primary
            )
        )
    }){
        ContentHomeView(it)
    }
}

fun calcularCuota(montoPrestamo: Double, cantCuotas: Int, tasaInteresAnual: Double): Double {
    val tasaMensual=tasaInteresAnual/12/100

    return (montoPrestamo*tasaMensual* (1 + tasaMensual).pow(cantCuotas.toDouble()) /
            ((1 + tasaMensual).pow(cantCuotas.toDouble()) -1)).toBigDecimal().setScale(2,RoundingMode.HALF_UP).toDouble()
}

fun calcularTotal(montoPrestamo:Double,cantCuotas:Int,tasaInteresAnual:Double):Double{
    return (cantCuotas*calcularCuota(montoPrestamo,cantCuotas,tasaInteresAnual)).toBigDecimal().setScale(2,RoundingMode.HALF_UP).toDouble()
}
