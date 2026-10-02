package CalculadoraLectura

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LecturaVMPage(viewModel: LecturaViewModel = viewModel()) {

    val paginasTotales by viewModel.paginasTotales.collectAsStateWithLifecycle()
    val paginaActual by viewModel.paginaActual.collectAsStateWithLifecycle()
    val minutosPorPagina by viewModel.minutosPorPagina.collectAsStateWithLifecycle()
    val minutosDiariosDisponibles by viewModel.minutosDiariosDisponibles.collectAsStateWithLifecycle()
    val tiempoRestanteMinutos by viewModel.tiempoRestanteMinutos.collectAsStateWithLifecycle()
    val diasRestantesEstimados by viewModel.diasRestantesEstimados.collectAsStateWithLifecycle()
    val progreso by viewModel.progreso.collectAsStateWithLifecycle()
    val paginasRestantes by viewModel.paginasRestantes.collectAsStateWithLifecycle()


    val colorHexProgreso by viewModel.colorHexProgreso.collectAsStateWithLifecycle()
    val colorDinámico = Color(colorHexProgreso)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planificador de Lectura") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            OutlinedTextField(
                value = paginasTotales,
                onValueChange = { viewModel.onPaginasTotalesChanged(it) },
                label = { Text("Total de páginas del libro") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = paginaActual,
                onValueChange = { viewModel.onPaginaActualChanged(it) },
                label = { Text("Página actual en la que vas") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = minutosPorPagina,
                onValueChange = { viewModel.onMinutosPorPaginaChanged(it) },
                label = { Text("Minutos por página") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = minutosDiariosDisponibles,
                onValueChange = { viewModel.onMinutosDiariosChanged(it) },
                label = { Text("Minutos diarios a dedicar") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Progreso de Lectura",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // Barra de Progreso con el color que proviene exclusivamente del ViewModel
                    LinearProgressIndicator(
                        progress = { progreso },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = colorDinámico,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Avance:")
                        Text(
                            text = "${(progreso * 100).toInt()}%",
                            fontWeight = FontWeight.Bold,
                            color = colorDinámico
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Páginas restantes:")
                        Text(
                            text = "$paginasRestantes pág",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Tiempo restante necesario:")
                        Text(
                            text = "$tiempoRestanteMinutos min",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Días restantes para terminar:")
                        Text(
                            text = "$diasRestantesEstimados días",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}