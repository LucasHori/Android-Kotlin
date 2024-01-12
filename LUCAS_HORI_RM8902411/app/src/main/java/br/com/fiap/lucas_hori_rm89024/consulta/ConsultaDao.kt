package br.com.fiap.lucas_hori_rm89024.consulta

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ConsultaDao {

    @Insert
    fun marcarConsulta(consulta: Consulta)

    @Query("SELECT * FROM consultas WHERE pacienteId = :pacienteId ORDER BY data, hora")
    fun obterConsultasDoPaciente(pacienteId: Long?): List<Consulta>
}
