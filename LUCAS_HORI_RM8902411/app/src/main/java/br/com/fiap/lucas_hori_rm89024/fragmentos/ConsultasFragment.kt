package br.com.fiap.lucas_hori_rm89024.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.lucas_hori_rm89024.Utils
import br.com.fiap.lucas_hori_rm89024.adapter.ConsultasAdapter
import br.com.fiap.lucas_hori_rm89024.databinding.FragmentConsultasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConsultasFragment : Fragment() {

    private lateinit var binding: FragmentConsultasBinding
    private lateinit var consultaAdapter: ConsultasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConsultasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id_usuario = getArguments()?.getLong(Utils.getUsuarioLogadoKey())

        consultaAdapter = ConsultasAdapter(requireContext())

        binding.recyclerViewConsultas.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = consultaAdapter
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val consultasDoPaciente = Utils.getConsultaDao(requireContext()).obterConsultasDoPaciente(id_usuario ?: 0)

                withContext(Dispatchers.Main) {
                    consultaAdapter.submitList(consultasDoPaciente)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
