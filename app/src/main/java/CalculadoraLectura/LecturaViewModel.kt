package CalculadoraLectura

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.ceil

class LecturaViewModel : ViewModel() {

    private val _paginasTotales = MutableStateFlow("")
    val paginasTotales: StateFlow<String> = _paginasTotales.asStateFlow()

    private val _paginaActual = MutableStateFlow("")
    val paginaActual: StateFlow<String> = _paginaActual.asStateFlow()

    private val _minutosPorPagina = MutableStateFlow("")
    val minutosPorPagina: StateFlow<String> = _minutosPorPagina.asStateFlow()

    private val _minutosDiariosDisponibles = MutableStateFlow("")
    val minutosDiariosDisponibles: StateFlow<String> = _minutosDiariosDisponibles.asStateFlow()

    private val _tiempoRestanteMinutos = MutableStateFlow(0)
    val tiempoRestanteMinutos: StateFlow<Int> = _tiempoRestanteMinutos.asStateFlow()

    private val _diasRestantesEstimados = MutableStateFlow(0)
    val diasRestantesEstimados: StateFlow<Int> = _diasRestantesEstimados.asStateFlow()

    private val _progreso = MutableStateFlow(0f) // Valor flotante de 0.0 a 1.0 para la barra de progreso
    val progreso: StateFlow<Float> = _progreso.asStateFlow()

    private val _paginasRestantes = MutableStateFlow(0)
    val paginasRestantes: StateFlow<Int> = _paginasRestantes.asStateFlow()

    fun onPaginasTotalesChanged(nuevoTexto: String) {
        _paginasTotales.value = nuevoTexto
        calcularLectura()
    }

    fun onPaginaActualChanged(nuevoTexto: String) {
        _paginaActual.value = nuevoTexto
        calcularLectura()
    }

    fun onMinutosPorPaginaChanged(nuevoTexto: String) {
        _minutosPorPagina.value = nuevoTexto
        calcularLectura()
    }

    fun onMinutosDiariosChanged(nuevoTexto: String) {
        _minutosDiariosDisponibles.value = nuevoTexto
        calcularLectura()
    }

    private fun calcularLectura() {
        val paginasTot = _paginasTotales.value.toIntOrNull() ?: 0
        val pagActual = _paginaActual.value.toIntOrNull() ?: 0
        val minPorPag = _minutosPorPagina.value.toIntOrNull() ?: 0
        val minDiarios = _minutosDiariosDisponibles.value.toIntOrNull() ?: 0

        // Validación de datos
        if (paginasTot <= 0 || minPorPag <= 0 || minDiarios <= 0 || pagActual < 0) {
            _tiempoRestanteMinutos.value = 0
            _diasRestantesEstimados.value = 0
            _progreso.value = 0f
            _paginasRestantes.value = 0
            return
        }

        // Limitar la página actual para que no sea mayor que las páginas totales
        val pagActualValidada = pagActual.coerceAtMost(paginasTot)

        // Cálculos
        val paginasFaltantes = paginasTot - pagActualValidada
        val minRestantes = paginasFaltantes * minPorPag
        val porcentaje = pagActualValidada.toFloat() / paginasTot.toFloat()
        val dias = ceil(minRestantes.toDouble() / minDiarios.toDouble()).toInt()

        // Actualizar estados
        _paginasRestantes.value = paginasFaltantes
        _tiempoRestanteMinutos.value = minRestantes
        _progreso.value = porcentaje
        _diasRestantesEstimados.value = dias
    }
}