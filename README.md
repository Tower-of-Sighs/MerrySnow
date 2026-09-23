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

当然，积雪依旧是在开启天气的情况下才会生效。

此外，本功能也支持细调，如单独使热带雨林下雪时积雪，或使冻洋群系不会结冰。

详见配置文件：
- snowModify = ["minecraft:forest=true", "minecraft:ocean=false"]
- iceModify = ["minecraft:forest=true", "minecraft:ocean=false"]

### 晴雪

原版中日月与天气是无法共存的，下雨下雪时无法看见日月和天空，但本模组允许你调整“晴雪系数”来使天气和日月共存。

具体来说，晴雪系数代表从完全晴朗到完全天气之间的过渡取值，可以简单地理解为小雪、中雪、大雪，未来会支持更多可调整项。

效果：
![img](https://resource-api.xyeidc.com/client/pics/87a23ce2)
光影下效果：
![img](https://resource-api.xyeidc.com/client/pics/438665ad)

可以在配置文件中调整sunnySnow选项的值，或者在游戏中使用热键(默认M)+鼠标滚轮，来调整晴雪系数。

该功能是客户端功能，也可以用来简单地临时关闭天气效果。

此外，如果开启了配置文件中的randomSunnySnow选项，则每次天气切换时都会使用随机晴雪系数。

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

## 项目结构

本仓库使用 `common + targets/<loader>-<minecraft-version>` 结构，一个 target 对应一个加载器与一个 Minecraft 版本，禁止 universal jar。

```text
common/                         不依赖 Minecraft 与加载器的共享逻辑与共享资源
  src/main/java/                规则、DTO 与 SPI 契约（Java 8，无平台依赖）
  src/main/resources/           所有 target 共用的 assets/lang
targets/<loader>-<mc-version>/  该加载器与版本的入口、事件、Mixin、配置与 metadata
gradle/target-conventions/      所有 target 共用的构建约定
```

- `common` 只放 Java 8 兼容、无 Minecraft/loader 依赖的业务逻辑、DTO、算法和测试。
- `targets/*` 只放该 target 的入口、注册、事件、Minecraft API、Mixin、网络、渲染和 metadata。
- 平台差异由各 target 实现 `common` 中的 SPI 契约（`com.sighs.merrysnow.spi`），不用版本判断或反射兼容。

| Target | JDK（构建） | 编译 toolchain |
| --- | --- | --- |
| `forge-1.20.1` | 21 | 17 |
| `fabric-1.20.1` | 21 | 17 |
| `neoforge-1.21.1` | 21 | 21 |
| `neoforge-26.1` | 25 | 25 |

## 构建

每个 target 是独立 Gradle 根工程，应在它自己的目录中构建：

```powershell
cd targets\forge-1.20.1
.\gradlew.bat clean build
```

根项目默认只构建 `common`，也可以选择性构建前三个 JDK 21 target：

```powershell
.\gradlew.bat '-Ptarget=forge-1.20.1' build
.\gradlew.bat '-Ptarget=fabric-1.20.1' build
.\gradlew.bat '-Ptarget=neoforge-1.21.1' build
.\gradlew.bat -PallTargets=true build
```

`neoforge-26.1` 需要 JDK 25，必须独立构建。

## 发布

每个 target 都提供 `publishMods`，可手动发布其自身的产物至 CurseForge 与 Modrinth。项目 ID 在根 `gradle.properties` 中配置；token 只从环境变量读取：

```powershell
$env:CURSEFORGE_TOKEN = '...'
$env:MODRINTH_TOKEN = '...'
$env:PUBLISH_CHANGELOG = '本次版本的更新说明' # 可选

cd targets\forge-1.20.1
.\gradlew.bat publishMods
```

更完整的协作、评审与 CI 规则见 [AGENT.md](AGENT.md) 与 [docs/](docs/)；
各 target 的实现差异见 [docs/merrysnow-differences.md](docs/merrysnow-differences.md)。
