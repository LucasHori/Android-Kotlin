package br.com.fiap.lucas_hori_rm89024.medico

import android.content.Context
import android.util.Log
import br.com.fiap.lucas_hori_rm89024.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicoRepository {

    suspend fun inserirMockMedicos(context: Context): List<Medico>? {
        return try {
            // Obtém a lista de IDs de médicos já inseridos no banco de dados
            val idsMedicosInseridos = Utils.getMedicoDao(context).listarIdsMedicos()

            // Lista de médicos simulados
            val medicosSimulados = listOf(
                Medico(
                    nome = "Dr. João Silva",
                    especialidade = "Cardiologia",
                    faculdade = "Faculdade de Medicina ABC"
                ),
                Medico(
                    nome = "Dra. Maria Oliveira",
                    especialidade = "Ortopedia",
                    faculdade = "Faculdade de Medicina XYZ"
                ),
                Medico(
                    nome = "Dr. Marcos Santos",
                    especialidade = "Dermatologia",
                    faculdade = "Faculdade de Medicina DEF"
                ),
                Medico(
                    nome = "Dra. Ana Rodrigues",
                    especialidade = "Ginecologia",
                    faculdade = "Faculdade de Medicina GHI"
                ),
                Medico(
                    nome = "Dr. Pedro Costa",
                    especialidade = "Oftalmologia",
                    faculdade = "Faculdade de Medicina JKL"
                ),
                Medico(
                    nome = "Dra. Carla Lima",
                    especialidade = "Pediatria",
                    faculdade = "Faculdade de Medicina MNO"
                ),
                Medico(
                    nome = "Dr. André Almeida",
                    especialidade = "Urologia",
                    faculdade = "Faculdade de Medicina PQR"
                ),
                Medico(
                    nome = "Dra. Renata Silva",
                    especialidade = "Neurologia",
                    faculdade = "Faculdade de Medicina STU"
                ),
                Medico(
                    nome = "Dr. Paulo Oliveira",
                    especialidade = "Oncologia",
                    faculdade = "Faculdade de Medicina VWX"
                ),
                Medico(
                    nome = "Dra. Fernanda Santos",
                    especialidade = "Psiquiatria",
                    faculdade = "Faculdade de Medicina YZ"
                )
            )

            // Filtra os médicos simulados que não estão na lista de médicos inseridos
            val medicosNaoInseridos = medicosSimulados.filter { medico ->
                !idsMedicosInseridos.contains(medico.id)
            }

            // Se há médicos não inseridos, realiza a inserção no banco de dados
            if (medicosNaoInseridos.isNotEmpty()) {
                withContext(Dispatchers.IO) {
                    Utils.getMedicoDao(context).inserirMedicos(medicosNaoInseridos)
                }

                Log.d("MedicoRepository", "Lista de médicos inserida com sucesso.")
            } else {
                Log.d("MedicoRepository", "Nenhum médico novo para inserir.")
            }

            medicosNaoInseridos
        } catch (e: Exception) {
            Log.e("MedicoRepository", "Erro ao inserir lista de médicos: ${e.message}", e)
            null
        }
    }
}
