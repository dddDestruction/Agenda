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
import kotlinx.android.synthetic.main.item_clima.view.iconoClima
import kotlinx.android.synthetic.main.item_clima.view.temp
import kotlinx.android.synthetic.main.item_clima_cabecera.view.*

class ClimaAdapter(private var lista: List<ClimaEntidad>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val selectedItem = MutableLiveData<ClimaEntidad>()

    val CABEZAL = 1
    val CLIMA = 2

    class ClimaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texto = itemView.temp
        val dia = itemView.dia
        val imagen = itemView.iconoClima
        val view = itemView.cardView
    }

    class ClimaHolderCabecera(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texto = itemView.temp
        val dia = itemView.dia_cabecera
        val imagen = itemView.iconoClima
        val view = itemView.cardView_cabecera
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType){
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_clima_cabecera, parent, false)
                return ClimaHolderCabecera(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_clima, parent, false)
                return ClimaHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0){
            return CABEZAL
        }
        return CLIMA
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var clima = lista.get(position)
        when(holder){
            is ClimaHolderCabecera -> {
                holder.texto.text = clima.temp.toString()
                holder.dia.text = clima.diaSemana
                Picasso.get()
                    .load("http://openweathermap.org/img/wn/${clima.iconClima}@4x.png")
                    .resize(250, 250)
                    .centerCrop()
                    .into(holder.imagen)
                holder.view.setOnClickListener{
                    selectedItem.value = clima
                }
            }
            is ClimaHolder ->{
                holder.texto.text = clima.temp.toString()
                holder.dia.text = clima.diaSemana
                Picasso.get()
                    .load("http://openweathermap.org/img/wn/${clima.iconClima}@4x.png")
                    .resize(120, 120)
                    .centerCrop()
                    .into(holder.imagen)
                holder.view.setOnClickListener{
                    selectedItem.value = clima
                }
            }
        }
    }

    fun update(list:List<ClimaEntidad>){
        lista = list
        notifyDataSetChanged()
    }
}