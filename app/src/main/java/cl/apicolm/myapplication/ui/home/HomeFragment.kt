package cl.apicolm.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import cl.apicolm.myapplication.R
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.ui.tareas.TareasAdapter
import cl.apicolm.myapplication.ui.tareas.TareasViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_tareas.view.*

class HomeFragment : Fragment() {

    private lateinit var tareasViewModel: TareasViewModel
    private var climaId:Int = 0

    private lateinit var adapter: TareasAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initRecycler(root)
        initObservers(climaId)
        return root
    }


    fun initObservers(climaId:Int){
        tareasViewModel =
            ViewModelProviders.of(this).get(TareasViewModel::class.java)
        tareasViewModel.loadTareas(climaId)
            .observe(viewLifecycleOwner, Observer {
                for (ele in it){
                    Log.d("AAA", "Lista tareas ${ele.climaId}")
                }

                adapter.update(it)
            })
        adapter.selectedItem.observe(viewLifecycleOwner, Observer{
            Log.d("AAA", "Lista tareas ${climaId}")
            tareasViewModel.repository.insetarTarea(
                TareaEntidad(
                    climaId,
                    it,
                    "loquesea"
                )
            )
        })
    }
    fun initRecycler(root:View){
        adapter = TareasAdapter(listOf<TareaEntidad>())
        val recyclerView = root.recyclerHome
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
