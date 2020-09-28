package cl.apicolm.myapplication

import android.view.View

interface IViews {
    //Inicializa observer y viewmodels
    fun initObservers()
    //inicializa recclers y adapters
    fun initRecycler(root: View)
}