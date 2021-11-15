package com.alan.core.utils

import androidx.recyclerview.widget.DiffUtil

/*
* Clase que hereda de DiffUtil.Callback -De RecyclerView
* el cual se implementara en el adaptador de recyclerView.
* Es recomendable para utlizar a la hora de insertar o actualizar
* nuestro dataset, este remplaza el notifyDatasetChanged y derivados
* reduce la carga y es mas eficiente
* Ver: https://bhttps://blog.mindorks.com/the-powerful-tool-diff-util-in-recyclerview-android-tutoriallog.mindorks.com/the-powerful-tool-diff-util-in-recyclerview-android-tutorial
* */
class DiffCallback<T>(
    private var oldList: List<T>,
    private var newList: List<T>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        oldList[oldItemPosition]?.let {
            return it == newList[newItemPosition]
        }
        return false
    }
}