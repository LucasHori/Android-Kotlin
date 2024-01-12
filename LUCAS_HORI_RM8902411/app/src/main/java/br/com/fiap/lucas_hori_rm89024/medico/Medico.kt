package br.com.fiap.lucas_hori_rm89024.medico

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicos")
data class Medico(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val especialidade: String,
    val faculdade: String
)