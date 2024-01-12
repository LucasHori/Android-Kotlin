package br.com.fiap.lucas_hori_rm89024.usuario

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Insert
    fun inserirUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha")
    fun loginUsuario(email: String, senha: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE email = :email")
    fun buscarUsuarioPorEmail(email: String): Usuario?
}