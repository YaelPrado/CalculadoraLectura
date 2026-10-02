package CalculadoraLectura

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LecturaViewModel : ViewModel() {

    private val _paginasTotales = MutableStateFlow("")
    val paginasTotales: StateFlow<String> = _paginasTotales.asStateFlow()

    private val _minutosPorPagina = MutableStateFlow("")
    val minutosPorPagina: StateFlow<String> = _minutosPorPagina.asStateFlow()

    private val _minutosDiariosDisponibles = MutableStateFlow("")
    val minutosDiariosDisponibles: StateFlow<String> = _minutosDiariosDisponibles.asStateFlow()

    private val _tiempoTotalMinutos = MutableStateFlow(0)
    val tiempoTotalMinutos: StateFlow<Int> = _tiempoTotalMinutos.asStateFlow()

    private val _diasEstimados = MutableStateFlow(0)
    val diasEstimados: StateFlow<Int> = _diasEstimados.asStateFlow()

    fun onPaginasChanged(nuevoTexto: String) {
        _paginasTotales.value = nuevoTexto
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
        val paginas = _paginasTotales.value.toIntOrNull()
        val minPorPag = _minutosPorPagina.value.toIntOrNull()
        val minDiarios = _minutosDiariosDisponibles.value.toIntOrNull()

        if (paginas == null || minPorPag == null || minDiarios == null ||
            paginas <= 0 || minPorPag <= 0 || minDiarios <= 0) {
            _tiempoTotalMinutos.value = 0
            _diasEstimados.value = 0
            return
        }

        val totalMinutos = paginas * minPorPag
        _tiempoTotalMinutos.value = totalMinutos

        // Se calcula la cantidad de días requeridos según el tiempo diario que dará el usuario
        val diasExactos = totalMinutos.toDouble() / minDiarios.toDouble()
        _diasEstimados.value = kotlin.math.ceil(diasExactos).toInt()
    }
}

