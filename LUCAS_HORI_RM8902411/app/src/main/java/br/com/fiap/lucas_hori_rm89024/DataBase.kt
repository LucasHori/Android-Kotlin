package br.com.fiap.lucas_hori_rm89024

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.lucas_hori_rm89024.consulta.Consulta
import br.com.fiap.lucas_hori_rm89024.consulta.ConsultaDao
import br.com.fiap.lucas_hori_rm89024.medico.Medico
import br.com.fiap.lucas_hori_rm89024.medico.MedicoDao
import br.com.fiap.lucas_hori_rm89024.usuario.Usuario
import br.com.fiap.lucas_hori_rm89024.usuario.UsuarioDao

const val MY_DATABASE_NAME = "my_app_database"

@Database(entities = [Usuario::class, Medico::class, Consulta::class], version = 1, exportSchema = false)
abstract class MyAppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun medicoDao(): MedicoDao
    abstract fun consultaDao(): ConsultaDao

    companion object {
        @Volatile
        private var INSTANCE: MyAppDatabase? = null

        fun getDatabase(context: Context): MyAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyAppDatabase::class.java,
                    MY_DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
