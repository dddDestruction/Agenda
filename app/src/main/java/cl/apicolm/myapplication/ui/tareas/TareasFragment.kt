package cl.apicolm.myapplication.ui.tareas

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
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_tareas.view.*

class TareasFragment : Fragment(), IViews {

    private lateinit var tareasViewModel: TareasViewModel
    private var climaId:Int = 0

    private lateinit var adapter: TareasAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        climaId = requireArguments().getInt("climaId")
        initObservers()
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_tareas, container, false)
        initRecycler(root)
        try {
            val fab: FloatingActionButton =requireActivity().findViewById(R.id.fab)
            fab.visibility = View.VISIBLE
        }catch (e:Exception){
        }
        return root
    }

    override fun initObservers(){
        tareasViewModel =
            ViewModelProviders.of(this).get(TareasViewModel::class.java)
        tareasViewModel.loadTareas(climaId)
            .observe(viewLifecycleOwner, Observer {
                adapter.update(it)
            })
        adapter.selectedItem.observe(viewLifecycleOwner, Observer{
            Log.d("AAA", "Lista tareas ${climaId}")
            tareasViewModel.repository.insetarTarea(
                listOf(TareaEntidad( climaId,it))
            )
        })
    }
    override fun initRecycler(root:View){
        adapter = TareasAdapter(listOf<TareaEntidad>())
        val recyclerView = root.recyclerTarea
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
