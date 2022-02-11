package org.example.mirai.autobracket

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.Image.Key.queryUrl
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.info

/**
 * 使用 kotlin 版请把
 * `src/main/resources/META-INF.services/net.mamoe.mirai.console.plugin.jvm.JvmPlugin`
 * 文件内容改成 `org.example.mirai.plugin.PluginMain` 也就是当前主类全类名
 *
 * 使用 kotlin 可以把 java 源集删除不会对项目有影响
 *
 * 在 `settings.gradle.kts` 里改构建的插件名称、依赖库和插件版本
 *
 * 在该示例下的 [JvmPluginDescription] 修改插件名称，id和版本，etc
 *
 * 可以使用 `src/test/kotlin/RunMirai.kt` 在 ide 里直接调试，
 * 不用复制到 mirai-console-loader 或其他启动器中调试
 */

val brackets = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '（' to '）',
    '【' to '】',
    '「' to '」',
    '『' to '』',
    '《' to '》',
    '<' to '>',
    '＜' to '＞',
    '〈' to '〉',
    '｛' to '｝',
    '［' to '］',
    '／' to '＼',
    '｟' to '｠',
)

fun analyzeBracket(msg: String) : ArrayList<Char> {
    val stack = ArrayList<Char>(msg.length)
    for (c in msg) {
        if (stack.size != 0 && brackets[stack[stack.size - 1]] == c){
           stack.removeLastOrNull()
        } else if (brackets.keys.contains(c) || brackets.values.contains(c)){
            stack.add(c)
        }
    }
    return stack
}

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "org.example.mirai-autocomplete-brackets",
        name = "让我看看谁没有补全括号",
        version = "0.1.0"
    ) {
        author("Tackoil")
        info(
            """
            这是一个测试插件, 
            在这里描述插件的功能和用法等.
        """.trimIndent()
        )
        // author 和 info 可以删除.
    }
) {
    override fun onEnable() {
        logger.info { "Plugin loaded -  Autocomplete Brackets" }
        //配置文件目录 "${dataFolder.absolutePath}/"
        val eventChannel = GlobalEventChannel.parentScope(this)
        eventChannel.subscribeAlways<GroupMessageEvent>{
            //群消息
            val result = analyzeBracket(message.contentToString())
            val backBrackets = result.filter { brackets.values.contains(it) }
            if (backBrackets.isNotEmpty()){
                group.sendMessage("让我看看谁没有补全括号？哦，补不全")
            } else if (result.isNotEmpty()) {
                val autoBrackets = result.map {
                    brackets[it]
                }.reversed().joinToString(separator = "")
                group.sendMessage("${autoBrackets}让我看看谁没有补全括号？")
            }
        }
    }
}
