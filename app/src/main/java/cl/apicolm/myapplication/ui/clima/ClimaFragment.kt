package cl.apicolm.myapplication.ui.clima

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cl.apicolm.myapplication.R
import cl.apicolm.myapplication.model.AgendaRepository
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import kotlinx.android.synthetic.main.fragment_clima.view.*

class ClimaFragment : Fragment() {

    private lateinit var galleryViewModel: ClimaViewModel
    val adapter = ClimaAdapter(listOf<ClimaEntidad>())

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        /*
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel::class.java)

        val textView: TextView = root.findViewById(R.id.text_gallery)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        */
        val root = inflater.inflate(R.layout.fragment_clima, container, false)
        val agenda = AgendaRepository(requireContext())
        agenda.loadData()
        agenda.climas.observe(viewLifecycleOwner, Observer {
            Log.d("AAA", "En Main, $it")
            adapter.update(it)
        })
        val recyclerView = root.recycker_clima
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return root
    }
}
