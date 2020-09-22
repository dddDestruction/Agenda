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
import cl.apicolm.myapplication.model.RepoUtil
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import cl.apicolm.myapplication.model.sharedPreferences.SharedPrefenrecesManager
import cl.apicolm.myapplication.ui.tareas.TareasAdapter
import cl.apicolm.myapplication.ui.tareas.TareasViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_tareas.view.*

class HomeFragment : Fragment() {

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


    fun initObservers(){
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.loadAllTareas()
            .observe(viewLifecycleOwner, Observer {
                Log.d("AAA", "fecha SharedPreferences ${SharedPrefenrecesManager(requireContext()).getDate()}")
                if (RepoUtil().diff(SharedPrefenrecesManager(requireContext()).getDate())> 0L){
                    homeViewModel.actualizarTareas(it)
                }else{
                    for (tarea in it as MutableList<TareaEntidad>){
                        if  (tarea.climaId != 0){
                            it.remove(tarea)
                        }
                    }
                    adapter.update(it)
                }
            })
        adapter.selectedItem.observe(viewLifecycleOwner, Observer{
            Log.d("AAA", "Lista tareas ${climaId}")
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
    fun initRecycler(root:View){
        adapter = TareasAdapter(listOf<TareaEntidad>())
        val recyclerView = root.recyclerHome
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
