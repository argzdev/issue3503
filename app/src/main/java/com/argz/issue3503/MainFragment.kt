package com.argz.issue3503

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.argz.issue3503.databinding.FragmentMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val TAG = "MainFragment"
class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var database: DatabaseReference
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.apply {
            fragment = this@MainFragment
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Firebase.database.setPersistenceEnabled(true)
    }
}