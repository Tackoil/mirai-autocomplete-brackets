package org.example.mirai.autobracket

import net.mamoe.mirai.console.data.*

object BracketConfig : AutoSavePluginConfig("config") {
    @ValueDescription(
        """
        应当自动补全的括号列表
        格式为 `- '左半括号:右半括号'`
        """
    )
    val brackets by value(
        listOf(
            "(:)",
            "[:]",
            "{:}",
            "（:）",
            "【:】",
            "「:」",
            "『:』",
            "《:》",
            "<:>",
            "＜:＞",
            "〈:〉",
            "｛:｝",
            "［:］",
            "／:＼",
            "｟:｠",
        )
    )

    @ValueName("message-without-left-bracket")
    @ValueDescription(
        """
        缺失左半边括号时发送的消息 (随机)
        """
    )
    val messagesWithoutLeftBracket by value(listOf("让我看看谁没有补全括号？哦，补不全"))

    @ValueName("message-without-right-bracket")
    @ValueDescription(
        """
        缺失右半边括号时发送的消息 (随机)
        英文半角右括号将会替换成需要补的括号
        """
    )
    val messagesWithoutRightBracket by value(listOf(")让我看看谁没有补全括号？"))
}