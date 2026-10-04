package CalculadoraLectura

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
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

// Componentes Reutilizables que aplican los manuales


            CampoLectura(
                valor = paginasTotales,
                etiqueta = "Total de páginas del libro",
                icono = Icons.Default.Book,
                onValorChange = { viewModel.onPaginasTotalesChanged(it) }
            )


            Spacer(modifier = Modifier.height(12.dp))


            CampoLectura(
                valor = paginaActual,
                etiqueta = "Página actual en la que vas",
                icono = Icons.Default.AutoStories,
                onValorChange = { viewModel.onPaginaActualChanged(it) }
            )


            Spacer(modifier = Modifier.height(12.dp))


            CampoLectura(
                valor = minutosPorPagina,
                etiqueta = "Minutos por página",
                icono = Icons.Default.Timer,
                onValorChange = { viewModel.onMinutosPorPaginaChanged(it) }
            )


            Spacer(modifier = Modifier.height(12.dp))


            CampoLectura(
                valor = minutosDiariosDisponibles,
                etiqueta = "Minutos diarios a dedicar",
                icono = Icons.Default.Schedule,
                onValorChange = { viewModel.onMinutosDiariosChanged(it) }
            )


            Spacer(modifier = Modifier.height(24.dp))


            TarjetaResumenLectura(
                progreso = progreso,
                colorHex = colorHexProgreso,
                paginasRestantes = paginasRestantes,
                tiempoRestanteMinutos = tiempoRestanteMinutos,
                diasRestantesEstimados = diasRestantesEstimados
            )
        }
    }
}


// Componentes Reutilizables que solo son manuales

@Composable
fun CampoLectura(
    valor: String,
    etiqueta: String,
    icono: ImageVector,
    onValorChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        label = { Text(etiqueta) },
        leadingIcon = { Icon(icono, contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth()
    )
}


@Composable
fun TarjetaResumenLectura(
    progreso: Float,
    colorHex: Long,
    paginasRestantes: Int,
    tiempoRestanteMinutos: Int,
    diasRestantesEstimados: Int,
    modifier: Modifier = Modifier
) {
    val colorDinamico = Color(colorHex)
    val progresoAnimado by animateFloatAsState(targetValue = progreso, animationSpec = tween(durationMillis = 600),
        label = "animacionProgreso")

    Card(
        modifier = modifier.fillMaxWidth(),
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

            LinearProgressIndicator(
                progress = { progresoAnimado },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = colorDinamico,
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
                    color = colorDinamico
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
