package br.com.fiap.lucas_hori_rm89024.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.fiap.lucas_hori_rm89024.R
import br.com.fiap.lucas_hori_rm89024.Utils
import br.com.fiap.lucas_hori_rm89024.databinding.FragmentLoginBinding
import br.com.fiap.lucas_hori_rm89024.usuario.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var usuario: Usuario? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener {
            if (validarCamposNulos()) {
                GlobalScope.launch(Dispatchers.Main) {
                    val email = binding.etEmail.text.toString()
                    val senha = binding.etSenha.text.toString()

                    withContext(Dispatchers.IO) {
                        try {
                            usuario = Utils.getUsuarioDao(requireContext())
                                .loginUsuario(email, senha)!!
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    if (usuario != null) {
                        Utils.showToast(requireContext(), "Login bem-sucedido!")
                        irParaAgendar(usuario!!)
                    } else {
                        Utils.showToast(requireContext(), "Credenciais inválidas.")
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
                !binding.etEmail.text.isNullOrBlank() &&
                        !binding.etSenha.text.isNullOrBlank()
                )
    }

    private fun irParaAgendar(usuario: Usuario) {
        val bundle = Bundle()
        val email = usuario.email
        bundle.putString(Utils.getUsuarioLogadoKey(), email)
        findNavController().navigate(R.id.action_loginFragment_to_agendarFragment, bundle)
    }
}

