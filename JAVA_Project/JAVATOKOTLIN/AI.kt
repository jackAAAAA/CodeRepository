import java.util.*

object AI {
    @JvmStatic
    fun main(args: Array<String>) {
        val `in` = Scanner(System.`in`)
        while (true) {
            val input = `in`.next()
            if ("exit" == input) {
                println("再见！")
                break
            }
            var replied = false
            val canStart = arrayOf("会", "能", "有", "敢", "在")
            run {
                var i = 0
                while (i < canStart.size && !replied) {
                    if (input.startsWith(canStart[i])) {
                        println(canStart[i] + "！")
                        replied = true
                        break
                    }
                    i++
                }
            }
            val askTail = arrayOf("吗？", "吗?", "吗")
            var i = 0
            while (i < askTail.size && !replied) {
                if (input.endsWith(askTail[i])) {
                    println(input.replace(askTail[i], "！"))
                    replied = true
                    break
                }
                i++
            }
            if (!replied) {
                println("$input！")
            }
        }
    }
}