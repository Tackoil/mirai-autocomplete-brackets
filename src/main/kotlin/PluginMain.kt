package org.example.mirai.autobracket

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.info
import org.example.mirai.autobracket.BracketConfig.messagesWithoutLeftBracket
import org.example.mirai.autobracket.BracketConfig.messagesWithoutRightBracket

var brackets = mutableMapOf<Char,Char>()

fun analyzeBracket(msg: String): ArrayList<Char> {
    val stack = ArrayList<Char>(msg.length)
    for (c in msg) {
        if (stack.size != 0 && brackets[stack[stack.size - 1]] == c) {
            stack.removeLastOrNull()
        } else if (brackets.keys.contains(c) || brackets.values.contains(c)) {
            stack.add(c)
        }
    }
    return stack
}

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "org.example.mirai-autocomplete-brackets",
        name = "让我看看谁没有补全括号",
        version = "0.1.3"
    ) {
        author("Tackoil")
        info(
            """
            根据群聊内的消息，自动补全其括号，为强迫症患者与编译器提供便利
        """.trimIndent()
        )
    }
) {
    override fun onEnable() {
        logger.info { "Plugin loaded -  Autocomplete Brackets" }
        //配置文件目录 "${dataFolder.absolutePath}/"

        BracketConfig.reload()
        for (s in BracketConfig.brackets) {
            if (s.contains(":")) {
                val split = s.split(":")
                val left = split[0].toCharArray()
                val right = split[1].toCharArray()
                if (left.size == 1 && right.size == 1) {
                    brackets[left[0]] = right[0]
                }
            }
        }

        val eventChannel = GlobalEventChannel.parentScope(this)
        eventChannel.subscribeAlways<GroupMessageEvent> {
            // 群消息
            val result =
                analyzeBracket(message.filterIsInstance<PlainText>().joinToString(separator = "") { it.content })
            val backBrackets = result.filter { brackets.values.contains(it) }
            if (backBrackets.isNotEmpty()) {
                if (messagesWithoutLeftBracket.isNotEmpty()) {
                    group.sendMessage(messagesWithoutLeftBracket.random())
                }
            } else if (result.isNotEmpty()) {
                if (messagesWithoutRightBracket.isNotEmpty()) {
                    val autoBrackets = result.map {
                        brackets[it]
                    }.reversed().joinToString(separator = "")
                    group.sendMessage(messagesWithoutRightBracket.random().replace(")", autoBrackets))
                }
            }
        }
    }
}
