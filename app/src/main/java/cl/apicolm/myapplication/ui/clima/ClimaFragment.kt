package cl.apicolm.myapplication.ui.clima

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.apicolm.myapplication.R
import cl.apicolm.myapplication.model.AgendaRepository
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import cl.apicolm.myapplication.util.Location
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_clima.view.*

class ClimaFragment : Fragment() {

    private lateinit var climasViewModel: ClimaViewModel
    val adapter = ClimaAdapter(listOf<ClimaEntidad>())

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_clima, container, false)
        try {
            val fab: FloatingActionButton =requireActivity().findViewById(R.id.fab)
            fab.visibility = View.GONE
        }catch (e:Exception){
            Log.d("AAA", "Error en fab ClimaFragment $e")
        }
        initViewModel()
        initRecycler(root)
        return root
    }

    fun initViewModel(){
        climasViewModel =
            ViewModelProviders.of(this).get(ClimaViewModel::class.java)
        climasViewModel.localizacion(requireActivity()).observe(viewLifecycleOwner, Observer {
            climasViewModel.load(it)
            climasViewModel.climas.observe(viewLifecycleOwner, Observer {
                Log.d("AAA", "En Main, $it")
                adapter.update(it)
            })
        })
    }
    fun initRecycler(root:View){
        val recyclerView = root.recycker_clima
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }
    override fun onAttach(activity: Activity) {
        adapter.selectedItem.observe(this, Observer {
            val bundle = Bundle()
            bundle.putInt("climaId", it.id )
            Navigation.findNavController(this.requireView()).navigate(R.id.action_nav_gallery_to_nav_slideshow, bundle)
        })
        super.onAttach(activity)
    }
}
