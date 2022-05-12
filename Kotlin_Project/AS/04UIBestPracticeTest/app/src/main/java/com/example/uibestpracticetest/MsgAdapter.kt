package com.example.uibestpracticetest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MsgAdapter(val msgList: List<Msg>) : RecyclerView.Adapter<MsgViewHolder>() {

//    inner class leftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val leftMsg: TextView = view.findViewById(R.id.leftMsg)
//    }
//
//    inner class rightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val rightMsg: TextView = view.findViewById(R.id.rightMsg)
//    }

    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position]
        return msg.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder =
        if (viewType == Msg.TYPE_RECEIVED) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.msg_left_item, parent,false)
            leftViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.msg_right_item, parent, false)
            rightViewHolder(view)
        }

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        val msg = msgList[position]
        when (holder) {
            is leftViewHolder -> {
                holder.leftMsg.text = msg.content
            }
            is rightViewHolder -> {
                holder.rightMsg.text = msg.content
            }
//            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = msgList.size

}