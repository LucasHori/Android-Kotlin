package br.com.fiap.lucas_hori_rm89024.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.lucas_hori_rm89024.Utils
import br.com.fiap.lucas_hori_rm89024.consulta.Consulta
import br.com.fiap.lucas_hori_rm89024.databinding.ItemConsultaViewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConsultasAdapter(
    private val context: Context
) : ListAdapter<Consulta, ConsultasAdapter.ConsultaViewHolder>(ConsultaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemConsultaViewBinding.inflate(inflater, parent, false)
        return ConsultaViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ConsultaViewHolder, position: Int) {
        val consulta = getItem(position)
        holder.bind(consulta)
    }

    class ConsultaViewHolder(
        private val binding: ItemConsultaViewBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(consulta: Consulta) {
            GlobalScope.launch(Dispatchers.IO) {
                val nomeMedico = Utils.getMedicoDao(context).obterNomeMedicoPorId(consulta.medicoId)

                withContext(Dispatchers.Main) {
                    // Atualizar a UI com o nome do médico
                    binding.tvMedicoNome.text = "Médico: $nomeMedico"
                    binding.tvData.text = "Data: ${consulta.data}"
                    binding.tvHora.text = "Hora: ${consulta.hora}"
                }
            }
        }
    }

    private class ConsultaDiffCallback : DiffUtil.ItemCallback<Consulta>() {
        override fun areItemsTheSame(oldItem: Consulta, newItem: Consulta): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Consulta, newItem: Consulta): Boolean {
            return oldItem == newItem
        }
    }
}
