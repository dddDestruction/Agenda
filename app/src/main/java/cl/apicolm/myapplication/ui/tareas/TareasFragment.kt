package cl.apicolm.myapplication.ui.tareas

import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_clima.view.*
import kotlinx.android.synthetic.main.fragment_tareas.view.*

class TareasFragment : Fragment() {

    private lateinit var tareasViewModel: TareasViewModel
    private var climaId:Int = 1

    val adapter = TareasAdapter(listOf<TareaEntidad>(
        TareaEntidad(1, "nada", "aadad"),
        TareaEntidad(1, "nadar", "aadad"),
        TareaEntidad(1, "Aloja", "aadad")
    ))

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_tareas, container, false)
        initObservers(climaId)
        initRecycler(root)
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TareasFragment().apply {
                arguments = Bundle().apply {
                    Log.d("AAA", "En TareasFragment climaId Bundle ${this.getInt("climaID")}")
                    climaId = this.getInt("climaID")
                }
            }
    }

    fun initObservers(climaId:Int){
        tareasViewModel =
            ViewModelProviders.of(this).get(TareasViewModel::class.java)
        tareasViewModel.loadTareas(climaId)
            .observe(viewLifecycleOwner, Observer {
                adapter.update(it)
            })
        adapter.selectedItem.observe(viewLifecycleOwner, Observer{
            tareasViewModel.repository.insetarTarea(TareaEntidad(
                climaId,
                it,
                "loquesea"
            ))
        })
    }
    fun initRecycler(root:View){
        val recyclerView = root.recyclerTarea
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
