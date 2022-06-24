# mirai-autocomplete-brackets

> 让我看看谁没有补全括号?

## 简介

mirai-autocomplete-brackets 基于 [Mirai Console](https://github.com/mamoe/mirai-console) 插件模板开发，根据群聊内的消息，自动补全其括号，为强迫症患者与编译器提供便利。例如：

> Usr： 我觉得今天挺冷的（
>
> Bot：）让我看看谁没有补全括号？

> Usr： 我觉得今天挺冷的）
>
> Bot：让我看看谁没有补全括号？哦，补不上

> Usr： 我觉得今天挺冷的([（）{
>
> Bot：}])让我看看谁没有补全括号？

### 受支持的自动补全符号

以下为默认支持的括号，如有需要可到配置文件添加

| 左符号 | 右符号 |
| ------ | ------ |
| ( | ) |
| [ | ] |
| { | } |
| （ | ） |
| 【 | 】 |
| 「 | 」 |
| 『 | 』 |
| 《 | 》 |
| < | > |
| ＜ | ＞ |
| 〈 | 〉|
|｛ | ｝ |
| ［ | ］ |
| ／ | ＼ |
| ｟ | ｠ |


## 构建

本插件完全基于插件模板开发，构建方法详见模板使用文档。[how-to-use-plugin-template](https://github.com/project-mirai/how-to-use-plugin-template)

## 安装

在 [Releases](https://github.com/Tackoil/mirai-autocomplete-brackets/releases) 中下载插件包，放入 Mirai Console plugins 目录中加载。详见 Mirai Console 文档。

---

Have Fun!
