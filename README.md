## 瑞雪物语

这是一个以雪为核心的机制调整模组，主题是让原版关于雪的机制可以进行更多变化。

### 冻寂魔国

所有的群系都可以将天气形态指定为下雪，就算是沙漠也可以！

只需在配置文件中设置：enforceSnowWeather = "true"，再使用指令：weather rain，就可以让整个维度都下雪了。

这只是从客户端改变天气形态，不会强制设置天气为下雪。如需产生积雪、结冰的效果，请往下看。

此外，本功能也支持细调，如单独指定热带雨林群系，将其天气形态更改为下雪，指定冻洋群系，将其天气形态更改为下雨。

详见配置文件：weatherModify = ["minecraft:forest=snow", "minecraft:ocean=rain", "minecraft:jungle=none"]，none表示始终无天气。

### 冰天雪地

除了天气，还可以调整某个群系是否积雪、是否结冰。

但还是先看一下如何让整个维度都冰天雪地！

配置文件：enforceSnowCover = "true"，可启用强制积雪；enforceIceFreeze = true，可启用强制结冰。

当然，依旧是在开启天气的情况下才会生效。

此外，本功能也支持细调，如单独使热带雨林下雪时积雪，或使冻洋群系不会结冰。

详见配置文件：
- snowModify = ["minecraft:forest=true", "minecraft:ocean=false"]
- iceModify = ["minecraft:forest=true", "minecraft:ocean=false"]

### 晴雪

原版中日月与天气是无法共存的，下雨下雪时无法看见日月和天空，但本模组允许你调整“晴雪系数”来使天气和日月共存。



### 瑞雪

不想村庄变鬼村？开启merryPowderSnow配置后，细雪将始终具有等同于底部台阶的碰撞箱，且无冻伤伤害。

下雪总是伴随着刷怪？开启merrySnowWeather配置后，如果怪物即将自然生成的位置是下着雪的，那它将不会生成。

另外，如果开启了merrySnowLayer配置，那么积雪将不再有碰撞体积，也就不会挡路了。

### 其他

本模组还提供了一个新的指令，用于快速获取群系ID：
- /biomeid current，获取玩家所在群系的ID。
- /biomeid all，获取包含所有群系ID的数组。

### 未来计划

- 生物踩过的积雪层数-1，并且在积雪中会受到轻微减速效果，积雪层数越高减速越多。
- 与下雪、积雪相关的道具，如雪天娃娃。
- 滑冰玩法。