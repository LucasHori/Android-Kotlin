package br.com.fiap.lucas_hori_rm89024.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.lucas_hori_rm89024.Utils
import br.com.fiap.lucas_hori_rm89024.consulta.Consulta
import br.com.fiap.lucas_hori_rm89024.databinding.ItemMedicoViewBinding
import br.com.fiap.lucas_hori_rm89024.medico.Medico
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MedicoAdapter(
    private val context: Context,
    private val id_usuario: Long?,
    private val data: String,
    private val hora: String
) : ListAdapter<Medico, MedicoAdapter.MedicoViewHolder>(MedicoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMedicoViewBinding.inflate(inflater, parent, false)
        return MedicoViewHolder(binding, context, id_usuario, data, hora)
    }

    override fun onBindViewHolder(holder: MedicoViewHolder, position: Int) {
        val medico = getItem(position)
        holder.bind(medico)
    }

    class MedicoViewHolder(
        private val binding: ItemMedicoViewBinding,
        private val context: Context,
        private val id_usuario: Long?,
        private val data: String,
        private val hora: String
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(medico: Medico) {
            binding.tvNome.text = medico.nome
            binding.tvEspecialidade.text = medico.especialidade
            binding.tvFaculdade.text = medico.faculdade
            binding.btnMarcarConsulta.setOnClickListener(View.OnClickListener {
                GlobalScope.launch {
                    try {
                        val consulta = Consulta(
                            pacienteId = id_usuario,
                            medicoId = (layoutPosition + 1).toLong(),
                            data = data,
                            hora = hora
                        )

                        Utils.getConsultaDao(context).marcarConsulta(consulta)

                        withContext(Dispatchers.Main) {
                            Utils.showToast(context,"Consulta marcada para o Médico: ${medico.nome}, Data: $data, Hora: $hora")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Utils.showToast(context,"Falha ao marcar consulta: ${e.message}")
                    }
                }
            })
        }
    }

    private class MedicoDiffCallback : DiffUtil.ItemCallback<Medico>() {
        override fun areItemsTheSame(oldItem: Medico, newItem: Medico): Boolean {
            return oldItem.nome == newItem.nome
        }

        override fun areContentsTheSame(oldItem: Medico, newItem: Medico): Boolean {
            return oldItem == newItem
        }
    }
}

