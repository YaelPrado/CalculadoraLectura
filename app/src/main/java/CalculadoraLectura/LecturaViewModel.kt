package CalculadoraLectura

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LecturaViewModel : ViewModel(){
    private val _paginasTotales = MutableStateFlow("")
    val paginasTotales: StateFlow<String> = _paginasTotales.asStateFlow()

    private val _minutosPorPagina = MutableStateFlow("")
    val minutosPorPagina: StateFlow<String> = _minutosPorPagina.asStateFlow()

    private val _tiempoTotalMinutos = MutableStateFlow(0)
    val tiempoTotalMinutos: StateFlow<Int> = _tiempoTotalMinutos.asStateFlow()

    private val _paginasDiarias = MutableStateFlow(0)
    val paginasDiarias: StateFlow<Int> = _paginasDiarias.asStateFlow()

    fun onPaginasChanged(nuevoTexto: String) {
        _paginasTotales.value = nuevoTexto
        calcularLectura()
    }

    fun onMinutosChanged(nuevoTexto: String) {
        _minutosPorPagina.value = nuevoTexto
        calcularLectura()
    }

    private fun calcularLectura() {
        val paginas = _paginasTotales.value.toIntOrNull()
        val minutos = _minutosPorPagina.value.toIntOrNull()

        if (paginas == null || minutos == null || paginas <= 0 || minutos <= 0) {
            _tiempoTotalMinutos.value = 0
            _paginasDiarias.value = 0
            return
        }

        _tiempoTotalMinutos.value = paginas * minutos
        _paginasDiarias.value = (paginas / 7.0).toInt() + 1
    }

}