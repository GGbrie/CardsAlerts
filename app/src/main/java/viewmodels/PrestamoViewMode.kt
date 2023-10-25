package viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import models.PrestamoState
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class PrestamoViewModel : ViewModel(){
    private var state by mutableStateOf(PrestamoState())

    @Suppress("UNUSED")
    fun confirmDialog(){
        state = state.copy(showAlert = false)
    }

    @Suppress("UNUSED")
    fun limpiar(){
        state = state.copy(
            montoPrestamo = "",
            cantCuotas = "",
            tasa = "",
            montoInteres = 0.0,
            montoCuota = 0.0
        )
    }

    @Suppress("UNUSED")
    fun onValue(value:String, campo:String){
        Log.i("Gabriel", campo)
        Log.i("Gabriel", value)

        when(campo){
            "montoPrestamo" -> state = state.copy(montoPrestamo = value)
            "cuotas" -> state = state.copy(cantCuotas = value)
            "tasa" -> state = state.copy(tasa = value)
        }
    }

    private fun calcularTotal(montoPrestamo: Double, cantCuotas: Int, tasaInteresAnual: Double):Double{
        val res = cantCuotas * calcularCuota(montoPrestamo, cantCuotas, tasaInteresAnual)
        return BigDecimal(res).setScale(2, RoundingMode.HALF_UP).toDouble()
    }

    @Suppress("UNUSED")
    private fun calcularCuota(montoPrestamo: Double, cantCuotas: Int, tasaInteresAnual: Double): Double{
        val tasaInteresMensual = tasaInteresAnual / 12 / 100

        val cuota = montoPrestamo * tasaInteresMensual * (1 + tasaInteresMensual).pow(cantCuotas.toDouble()) / ((1 + tasaInteresMensual).pow(cantCuotas.toDouble())-1)

        return BigDecimal(cuota).setScale(2, RoundingMode.HALF_UP).toDouble()
    }

    @Suppress("UNUSED")
    fun calcular(){
        val montoPrestamo = state.montoPrestamo
        val cantCuotas = state.cantCuotas
        val tasa = state.tasa

        val showAlertValue = montoPrestamo=="" || cantCuotas=="" || tasa==""

        state = state.copy(
            montoCuota = if (!showAlertValue) calcularCuota(montoPrestamo.toDouble(), cantCuotas.toInt(), tasa.toDouble()) else state.montoCuota,
            montoInteres = if (!showAlertValue) calcularTotal(montoPrestamo.toDouble(), cantCuotas.toInt(), tasa.toDouble()) else state.montoInteres,
            showAlert = showAlertValue
        )
    }
}

