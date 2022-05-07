package com.example.app.samples

class View {
    fun setOnClickListener(listener: (View) -> Unit) {

    }
}
fun main() {
    val view = View()
    view.setOnClickListener(::onClick)
    view.setOnClickListener(fun (view: View) {
        println("被点击了")
    })
    view.setOnClickListener {
        println("被点击了")
    }
}
fun onClick(view: View) {
    println("被点击了")
}
