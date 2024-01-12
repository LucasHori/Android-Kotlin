package br.com.fiap.lucas_hori_rm89024.medico

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MedicoDao {

    @Insert
    fun inserirMedico(medico: Medico)

    @Insert
    fun inserirMedicos(medicos: List<Medico>)

    @Query("SELECT * FROM medicos")
    fun listarMedicos(): List<Medico>

    @Query("SELECT id FROM medicos")
    fun listarIdsMedicos(): List<Long>

    @Query("SELECT nome FROM medicos WHERE id = :medicoId")
    fun obterNomeMedicoPorId(medicoId: Long): String?
}