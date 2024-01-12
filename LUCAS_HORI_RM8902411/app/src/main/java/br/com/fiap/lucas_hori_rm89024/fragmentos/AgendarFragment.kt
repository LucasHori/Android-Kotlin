package br.com.fiap.lucas_hori_rm89024.fragmentos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.fiap.lucas_hori_rm89024.R
import br.com.fiap.lucas_hori_rm89024.Utils
import br.com.fiap.lucas_hori_rm89024.databinding.FragmentAgendarBinding
import br.com.fiap.lucas_hori_rm89024.usuario.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgendamentoFragment : Fragment() {

    private lateinit var binding: FragmentAgendarBinding
    private lateinit var usuario: Usuario

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgendarBinding.inflate(inflater, container, false)

        getUsuario(getArguments()?.getString(Utils.getUsuarioLogadoKey()).toString())
        binding.btnAgendar.setOnClickListener(View.OnClickListener {
            if (validarCamposNulos()) {
                irParaMedicos()
            } else {
                Utils.showToast(requireContext(), "Preencha todos os campos")
            }
        })
        binding.btnMinhasConsultas.setOnClickListener(View.OnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val consultasDoPaciente = Utils.getConsultaDao(requireContext()).obterConsultasDoPaciente(usuario.id)
                    if (!consultasDoPaciente.isEmpty()) {
                        irParaMinhasConsultas()
                    } else {
                        Utils.showToast(requireContext(), "Não há nenhuma consulta marcada")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })

        return binding.root
    }

    private fun getUsuario(email: String) {
        GlobalScope.launch(Dispatchers.Main) {
            usuario = withContext(Dispatchers.IO) {
                Utils.getUsuarioDao(requireContext()).buscarUsuarioPorEmail(email)!!
            }

            if (usuario != null) {
                println("Usuário encontrado")
                binding.tvBemVindo.setText("Olá, ${usuario.nome}!")
            } else {
                println("Usuário não encontrado para o e-mail: $email")
            }
        }
    }

    private fun validarCamposNulos(): Boolean {
        return (
                !binding.etData.text.isNullOrBlank() &&
                        !binding.etHorario.text.isNullOrBlank()
                )
    }

    private fun irParaMedicos() {
        val bundle = Bundle()
        val usuario_id = usuario.id
        val data = binding.etData.text
        val horario = binding.etHorario.text
        bundle.putLong(Utils.getUsuarioLogadoKey(), usuario_id)
        bundle.putString(Utils.getDataAgendaKey(), data.toString())
        bundle.putString(Utils.getHoraAgendaKey(), horario.toString())
        findNavController().navigate(R.id.action_agendarFragment_to_medicosFragment, bundle)
    }

    private fun irParaMinhasConsultas() {
        val bundle = Bundle()
        val usuario_id = usuario.id
        bundle.putLong(Utils.getUsuarioLogadoKey(), usuario_id)
        findNavController().navigate(R.id.action_agendarFragment_to_consultasFragment, bundle)
    }
}
