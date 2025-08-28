TodayWife - 今日老婆插件
一个有趣的Minecraft服务器插件，让玩家可以随机获取"今日老婆"并与她互动，增加服务器乐趣。

功能特性
🎯 随机老婆系统：每天可以随机选择一名在线玩家作为你的"今日老婆"

💖 互动系统：亲吻、拥抱、抚摸、调戏、SM等多种互动方式

📊 好感度系统：互动随机增减好感度

🔧 多版本支持：理论支持 1.12.x - 1.21.x 所有版本

🖥️ 多服务端兼容：支持 Spigot、Paper、Folia 等主流服务端

命令列表
命令	权限	描述
/todaywife 或 /tw	todaywife.use	显示帮助信息
/tw start	todaywife.use	随机获取今日老婆
/tw hate	todaywife.use	解除与老婆的关系
/tw kiss	todaywife.use	亲吻老婆
/tw molest	todaywife.use	调戏老婆
/tw hug	todaywife.use	拥抱老婆
/tw bondage	todaywife.use	SM互动
/tw caress	todaywife.use	抚摸老婆
/tw info	todaywife.use	查看老婆信息
/tw reload	todaywife.reload	重载配置（OP权限）

权限节点
权限节点	默认	描述
todaywife.use	true	使用插件基本功能
todaywife.reload	op	重载插件配置
todaywife.*	op	所有权限

安装方法
下载最新版本的 TodayWife.jar
将文件放入服务器的 plugins 文件夹
重启或重载服务
插件会自动生成配置文件

配置文件
config.yml
yaml
# 插件开关
enabled: true
# 互动功能设置
interactions:
  kiss: true
  molest: true
  hug: true
  bondage: true
  caress: true
# 语言文件设置
language: zh_CN
# 音效设置
sounds:
  enabled: true
  wife-selected: true
  interaction: true

语言文件
插件支持多种语言，文件位于 /resources/languages/：
zh_CN.yml - 简体中文
zh_TW.yml - 繁体中文
en.yml - 英文
（推荐简体中文，毕竟作者就是中国的）

兼容性
服务端	支持情况	备注
Spigot	✅ 完全支持
Paper	✅ 完全支持
Folia	✅ 完全支持
CraftBukkit	⚠️ 部分支持

开发者信息
插件名称: TodayWife
主类: com.todayWife.TodayWife
作者: 蓝影
API版本: 1.13+

构建说明
如果需要自行构建插件：
bash
# 克隆项目
git clone <repository-url>
# 进入目录
cd TodayWife
# 使用Maven构建
mvn clean package
# 构建后的文件在 target/TodayWife.jar

免责声明
本插件仅供娱乐目的使用，请确保在使用前获得所有玩家的同意。插件作者不对因使用本插件导致的任何问题负责。

开源协议
本项目采用 MIT 协议开源，详情请查看 LICENSE 文件。

享受与你的今日老婆互动吧！ 💕
