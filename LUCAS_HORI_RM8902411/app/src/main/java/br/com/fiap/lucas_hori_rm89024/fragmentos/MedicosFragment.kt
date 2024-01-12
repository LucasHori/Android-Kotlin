package br.com.fiap.lucas_hori_rm89024.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.lucas_hori_rm89024.R
import br.com.fiap.lucas_hori_rm89024.Utils
import br.com.fiap.lucas_hori_rm89024.adapter.MedicoAdapter
import br.com.fiap.lucas_hori_rm89024.databinding.FragmentMedicosBinding
import br.com.fiap.lucas_hori_rm89024.medico.MedicoRepository
import br.com.fiap.lucas_hori_rm89024.usuario.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MedicosFragment : Fragment() {

    private lateinit var binding: FragmentMedicosBinding
    private lateinit var medicoAdapter: MedicoAdapter
    private lateinit var medicoRepository: MedicoRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id_usuario = getArguments()?.getLong(Utils.getUsuarioLogadoKey())
        val data = getArguments()?.getString(Utils.getDataAgendaKey()).toString()
        val hora = getArguments()?.getString(Utils.getHoraAgendaKey()).toString()

        medicoAdapter = MedicoAdapter(requireContext(), id_usuario, data, hora)
        medicoRepository = MedicoRepository()

        binding.recyclerViewMedicos.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = medicoAdapter
        }

        GlobalScope.launch {
            val medicosList = medicoRepository.inserirMockMedicos(requireContext())
            withContext(Dispatchers.Main) {
                medicoAdapter.submitList(medicosList)
            }
        }
    }
}
