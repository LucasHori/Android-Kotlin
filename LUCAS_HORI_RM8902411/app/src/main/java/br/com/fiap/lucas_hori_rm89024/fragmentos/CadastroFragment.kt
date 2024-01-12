package br.com.fiap.lucas_hori_rm89024.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.fiap.lucas_hori_rm89024.R
import br.com.fiap.lucas_hori_rm89024.Utils
import br.com.fiap.lucas_hori_rm89024.databinding.FragmentCadastroBinding
import br.com.fiap.lucas_hori_rm89024.usuario.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadastroFragment : Fragment() {

    private lateinit var binding: FragmentCadastroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCadastroBinding.inflate(inflater, container, false)

        binding.btnCadastrar.setOnClickListener {
            if (validarCamposNulos()) {
                GlobalScope.launch(Dispatchers.Main) {
                    val nome = binding.etNome.text.toString()
                    val email = binding.etEmail.text.toString()
                    val telefone = binding.etTelefone.text.toString()
                    val senha = binding.etSenha.text.toString()

                    try {
                        withContext(Dispatchers.IO) {
                            val usuario = Usuario(nome = nome, email = email, telefone = telefone, senha = senha)
                            Utils.getUsuarioDao(requireContext()).inserirUsuario(usuario)
                        }
                        Utils.showToast(requireContext(), "Usuário cadastrado com sucesso.")
                        irParaHome()
                    } catch (e: Exception) {
                        Utils.showToast(
                            requireContext(),
                            "Erro ao cadastrar usuário: E-mail já cadastrado"
                        )
                    }
                }
            } else {
                Utils.showToast(requireContext(), "Preencha todos os campos.")
            }
        }
        return binding.root
    }

    private fun validarCamposNulos(): Boolean {
        return (
                !binding.etNome.text.isNullOrBlank() &&
                        !binding.etEmail.text.isNullOrBlank() &&
                        !binding.etTelefone.text.isNullOrBlank() &&
                        !binding.etSenha.text.isNullOrBlank()
                )
    }

    private fun irParaHome() {
        findNavController().navigate(R.id.action_cadastroFragment_to_homeFragment)
    }
}

