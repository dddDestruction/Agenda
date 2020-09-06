package cl.apicolm.myapplication.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import cl.apicolm.myapplication.R
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import kotlinx.android.synthetic.main.item_clima.view.*

class ClimaAdapter(private var lista: List<ClimaEntidad>) : RecyclerView.Adapter<ClimaAdapter.ClimaHolder>() {

    val selectedItem = MutableLiveData<ClimaEntidad>()

    class ClimaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texto = itemView.temp
        val imagen = itemView.iconoClima
        val view = itemView.cardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClimaAdapter.ClimaHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clima, parent, false)

        return ClimaHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ClimaAdapter.ClimaHolder, position: Int) {
        var clima = lista.get(position)
        holder.texto.text = clima.temp.toString()
        holder.imagen.setImageResource(R.drawable.ic_wb_sunny_60dp)
        holder.view.setOnClickListener{
            selectedItem.value = clima
        }
    }

    fun update(list:List<ClimaEntidad>){
        lista = list
        notifyDataSetChanged()
    }
}