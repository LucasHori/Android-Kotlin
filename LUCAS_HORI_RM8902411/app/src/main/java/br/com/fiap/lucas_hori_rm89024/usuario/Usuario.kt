package br.com.fiap.lucas_hori_rm89024.usuario

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios",
        indices = [Index(value = ["email"], unique = true)])

data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val email: String,
    val telefone: String,
    val senha: String
)