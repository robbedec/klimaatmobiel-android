package com.klimaatmobiel


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentMainMenuBinding
/**
 * A simple [Fragment] subclass.
 */
class MainMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainMenuBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_menu, container, false
        )
        binding.webshopButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainMenuFragment2_to_webshopFragment)
        }
        return binding.root
    }
}



