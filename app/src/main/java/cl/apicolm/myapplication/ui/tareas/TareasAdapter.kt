package cl.apicolm.myapplication.ui.tareas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import cl.apicolm.myapplication.R
import cl.apicolm.myapplication.model.entidades.TareaEntidad
import kotlinx.android.synthetic.main.item_tarea.view.*
import kotlinx.android.synthetic.main.item_tarea_formulario.view.*

class TareasAdapter(private var lista: List<TareaEntidad>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedItem = MutableLiveData<String>()

    val TAREA = 1
    val EDITABLE = 2

    class TareaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val texto = itemView.textoTarea
    }

    class FormularioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val editable = itemView.editTextoTarea
        val botonIngresar = itemView.botonIngresar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType){
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_tarea, parent, false)
                return TareaHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_tarea_formulario, parent, false)
                return FormularioHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == lista.size){
            return EDITABLE
        }
        return TAREA
    }

    override fun getItemCount(): Int {
        return lista.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TareaHolder -> {
                holder.texto.text = lista.get(position).descripcion
            }
            is FormularioHolder ->{
                holder.botonIngresar.setOnClickListener{
                    if(!holder.editable.text.toString().equals("")){
                        selectedItem.value = holder.editable.text.toString()
                    }
                }
            }
        }
    }

    fun update(list:List<TareaEntidad>){
        lista = list
        notifyDataSetChanged()
    }
}