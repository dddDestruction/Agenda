package cl.apicolm.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import cl.apicolm.myapplication.IViews
import cl.apicolm.myapplication.R
import cl.apicolm.myapplication.model.RepoUtil
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.model.sharedPreferences.SharedPrefenrecesManager
import cl.apicolm.myapplication.ui.tareas.TareasAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(), IViews {

    private lateinit var homeViewModel: HomeViewModel
    private val climaId = 0
    private lateinit var adapter: TareasAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initRecycler(root)
        initObservers()
        return root
    }


    override fun initObservers(){
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.loadAllTareas()
            .observe(viewLifecycleOwner, Observer {
                if (RepoUtil().diff(SharedPrefenrecesManager(requireContext()).getDate())> 0L){
                    homeViewModel.actualizarTareas(it)
                }else{
                    var listaAuxiliar = mutableListOf<TareaEntidad>()
                    for (tarea in it){
                        if  (tarea.climaId == 0){
                            listaAuxiliar.add(tarea)
                        }
                    }
                    adapter.update(listaAuxiliar)
                }
            })
        adapter.selectedItem.observe(viewLifecycleOwner, Observer{
            homeViewModel.repository.insetarTarea(
                listOf(
                    TareaEntidad(
                        climaId,
                        it
                    )
                )
            )
        })
    }
    override fun initRecycler(root:View){
        adapter = TareasAdapter(listOf<TareaEntidad>())
        val recyclerView = root.recyclerHome
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
