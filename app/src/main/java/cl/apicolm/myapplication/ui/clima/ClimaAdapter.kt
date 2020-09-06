package cl.apicolm.myapplication.ui.clima

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import cl.apicolm.myapplication.R
import cl.apicolm.myapplication.model.entidades.ClimaEntidad
import com.squareup.picasso.Picasso
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
        when(position){
            0 -> {
                val blanco = ContextCompat.getColor(holder.view.context, R.color.colorWhite)
                val azul = ContextCompat.getColor(holder.view.context, R.color.colorAccent)
                holder.texto.text = clima.temp.toString()
                holder.texto.setTextColor(blanco)
                holder.view.setBackgroundColor(azul)
                Picasso.get()
                    .load("http://openweathermap.org/img/wn/${clima.iconClima}@4x.png")
                    .resize(250, 250)
                    .centerCrop()
                    .into(holder.imagen)
            }
            else ->{
                holder.texto.text = clima.temp.toString()
                Picasso.get()
                    .load("http://openweathermap.org/img/wn/${clima.iconClima}@4x.png")
                    .resize(120, 120)
                    .centerCrop()
                    .into(holder.imagen)
            }
        }
        holder.view.setOnClickListener{
            selectedItem.value = clima
        }
    }

    fun update(list:List<ClimaEntidad>){
        lista = list
        notifyDataSetChanged()
    }
}