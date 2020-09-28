package cl.apicolm.myapplication.ui.clima

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import cl.apicolm.myapplication.IViews
import cl.apicolm.myapplication.R
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_clima.view.*

class ClimaFragment : Fragment(), IViews {

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
        }
        initObservers()
        initRecycler(root)
        return root
    }

    override fun initObservers(){
        climasViewModel =
            ViewModelProviders.of(this).get(ClimaViewModel::class.java)
        //carga los datos del clima con los datos de localización guardados en base de datos
        // o con la coordenada por defecto
        climasViewModel.load()
        climasViewModel.climas.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })
        //Llama a la clase lozalización para obtenerla, espera la respuesta y cuando la recibe carga los datos
        //de clima con los datos del GPS
        climasViewModel.localizacion(requireActivity()).observe(viewLifecycleOwner, Observer {
            climasViewModel.load()
        })
    }
    override fun initRecycler(root:View){
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
