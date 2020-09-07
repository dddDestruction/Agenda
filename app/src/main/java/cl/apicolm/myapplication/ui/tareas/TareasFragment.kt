package cl.apicolm.myapplication.ui.tareas

import android.os.Bundle
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
        tareasViewModel =
                ViewModelProviders.of(this).get(TareasViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tareas, container, false)
        initRecycler(root)
        return root
    }

    fun initRecycler(root:View){
        val recyclerView = root.recyclerTarea
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
