package br.com.fiap.lucas_hori_rm89024.consulta

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.fiap.lucas_hori_rm89024.medico.Medico
import br.com.fiap.lucas_hori_rm89024.usuario.Usuario

@Entity(
    tableName = "consultas",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["pacienteId"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Medico::class,
            parentColumns = ["id"],
            childColumns = ["medicoId"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Consulta(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pacienteId: Long?,
    val medicoId: Long,
    val data: String,
    val hora: String
)
