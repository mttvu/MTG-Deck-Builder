package com.example.mtgdeckbuilder


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_info.*

/**
 * A simple [Fragment] subclass.
 */
class InfoFragment : Fragment() {
    private lateinit var viewModel: DeckViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(activity as AppCompatActivity).get(DeckViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.deck.value = (activity as AppCompatActivity).intent.getParcelableExtra(EXTRA_DECK)!!
        var deck = viewModel.deck.value
        if (deck != null){
            tvName.text = deck.name
            tvFormat.text = deck.format
            tvDescription.text = deck.description
        }

    }


}
