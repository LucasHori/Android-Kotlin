package br.com.fiap.lucas_hori_rm89024.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.fiap.lucas_hori_rm89024.R
import br.com.fiap.lucas_hori_rm89024.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener(View.OnClickListener { irParaLogin() })
        binding.cadastroButton.setOnClickListener(View.OnClickListener { irParaCadastro() })

        return binding.root
    }

    private fun irParaLogin() {
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }

    private fun irParaCadastro() {
        findNavController().navigate(R.id.action_homeFragment_to_cadastroFragment)
    }
}

