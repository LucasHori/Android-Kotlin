package br.com.fiap.lucas_hori_rm89024

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import br.com.fiap.lucas_hori_rm89024.consulta.ConsultaDao
import br.com.fiap.lucas_hori_rm89024.medico.MedicoDao
import br.com.fiap.lucas_hori_rm89024.usuario.UsuarioDao

object Utils {

    fun showToast(context: Context, message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun getUsuarioDao(context: Context): UsuarioDao {
        val database = MyAppDatabase.getDatabase(context)
        return database.usuarioDao()
    }

    fun getMedicoDao(context: Context): MedicoDao {
        val database = MyAppDatabase.getDatabase(context)
        return database.medicoDao()
    }

    fun getConsultaDao(context: Context): ConsultaDao {
        val database = MyAppDatabase.getDatabase(context)
        return database.consultaDao()
    }

    fun getUsuarioLogadoKey(): String {
        return ("email_do_usuario_logado")
    }

    fun getDataAgendaKey(): String {
        return ("data_consulta")
    }

    fun getHoraAgendaKey(): String {
        return ("hora_consulta")
    }
}