package com.mywings.twolevelqrcodeproject.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mywings.twolevelqrcodeproject.R
import com.mywings.twolevelqrcodeproject.model.Transaction
import kotlinx.android.synthetic.main.layout_transaction.view.*

class TransactionAdapter(var list: ArrayList<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_transaction,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.lblToAc.text = "Transfer to : ".plus(list[position].toAc)
        holder.lblAmount.text = "Amount : ".plus(list[position].amount)
    }


    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lblToAc = itemView.lblToAc
        val lblAmount = itemView.lblAmount
    }
}