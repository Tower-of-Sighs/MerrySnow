# MerrySnow 多目标实现差异

本文件记录无法统一到 `common` 的差异。`common` 只保留不含 Minecraft 类型的规则与 SPI 契约
（`com.sighs.merrysnow.spi`），每个 target 只实现平台相关的接线。

## common 与 target 的职责划分

| 内容 | 位置 |
| --- | --- |
| 天气/积雪/结冰判定规则、晴雪系数计算、滚轮步进、修饰列表解析、JSON 数组拼接 | `common`（`com.sighs.merrysnow.logic`） |
| 默认值与哨兵字符串（`default`） | `common`（`ConfigDefaults`） |
| 配置契约、平台契约、运行时服务定位、客户端随机晴雪状态 | `common`（`spi` / `MerrySnowRuntime`） |
| 配置存储、事件接线、Mixin、指令、按键、metadata | 各 target |

`MerrySnowConfig` 的每个 getter 都必须返回**实时值**，因为晴雪系数可以在游戏内通过滚轮修改。

## 加载器差异

| 能力 | forge-1.20.1 | fabric-1.20.1 | neoforge-1.21.1 / 26.1 |
| --- | --- | --- | --- |
| 入口 | `MerrySnowForge`（`@Mod`） | `MerrySnowFabric` / `MerrySnowFabricClient` | `MerrySnowNeoForge`（`@Mod`，构造注入 `ModContainer`） |
| 配置存储 | `ForgeConfigSpec`（TOML，`Config`） | `config/merrysnow.json`（Gson，`Config`） | `ModConfigSpec`（TOML，`Config`） |
| 下雪不刷怪 | `MobSpawnEvent.FinalizeSpawn` 事件 | `NaturalSpawnerMixin#isValidPositionForMob` | `FinalizeSpawnEvent` 事件 |
| 鼠标滚轮 | `InputEvent.MouseScrollingEvent`（可取消） | `MouseHandlerMixin#onScroll` | `InputEvent.MouseScrollingEvent`（可取消） |
| 按键注册 | `RegisterKeyMappingsEvent` | `KeyBindingHelper.registerKeyBinding`（`ClientModInitializer`） | `RegisterKeyMappingsEvent` |
| 取当前绑定键 | `KeyMapping#getKey()` | `KeyBindingHelper#getBoundKeyOf` | `KeyMapping#getKey()` |
| 按键上下文 | `KeyConflictContext.IN_GAME` + `KeyModifier.NONE` | 无此概念，使用 vanilla 构造 | `KeyConflictContext.IN_GAME` + `KeyModifier.NONE` |
| 玩家进入世界 | `ClientPlayerNetworkEvent.LoggingIn` | `ClientPlayConnectionEvents.JOIN` | `ClientPlayerNetworkEvent.LoggingIn` |
| 指令注册 | `RegisterCommandsEvent` | `CommandRegistrationCallback.EVENT` | `RegisterCommandsEvent` |
| metadata | `META-INF/mods.toml` | `fabric.mod.json` | `META-INF/neoforge.mods.toml` |
| mixin 声明 | `[[mixins]]` + `MixinConfigs` manifest | `fabric.mod.json` 的 `mixins` | `[[mixins]]` |
| mixin refmap | 需要（SRG 运行时），由 Mixin 注解处理器生成 | 需要（intermediary），由 Loom 生成并在 remapJar 时注入 `refmap` 字段 | 不需要（运行时使用 Mojang 官方名） |

Fabric API 0.92 既没有生物生成事件，也没有鼠标滚轮事件，因此这两项由 Mixin 实现；
这是「具体实现差异」，判定逻辑仍然复用 `common` 的 `SnowRules`。

Fabric 的 `merrysnow.mixins.json` **不写** `refmap` 字段：Loom 会在 remapJar 阶段自动注入，
手写反而容易与 `loom.mixin.defaultRefmapName` 不一致。

NeoForge 的 `merrysnow.mixins.json` **不写** `refmap` 字段，也不声明注解处理器：运行时使用
Mojang 官方名，`net.fabricmc:sponge-mixin` 已由 userdev 放在编译 classpath 上。

## 版本差异

| 项 | 1.20.1（forge / fabric） | 1.21.1（neoforge） | 26.1（neoforge） |
| --- | --- | --- | --- |
| `pack.mcmeta` | `pack_format: 15`（与原项目一致） | 不提供（NeoForge 在缺失时按 COMPATIBLE 处理） | 不提供（格式已改为 `min_format` / `max_format`） |
| 自然生成枚举 | `MobSpawnType.NATURAL` | `MobSpawnType.NATURAL` | `EntitySpawnReason.NATURAL` |
| 温度判定 | `coldEnoughToSnow(BlockPos)`、`warmEnoughToRain(BlockPos)` | 同 1.20.1 | 增加 `int` 参数：`(BlockPos, int)`，取值 `level.getSeaLevel()` |
| 积雪判定注入点 | `shouldSnow` 内的 `warmEnoughToRain` | 同 1.20.1 | `shouldSnow` 改为经 `getPrecipitationAt(BlockPos, int)` 判定，重定向该方法并返回 `SNOW`/`RAIN` |
| 结冰判定注入点 | `shouldFreeze(...,Z)` 内的 `warmEnoughToRain` | 同 1.20.1 | 同 1.20.1，但带 `int` 参数 |
| `Level.isClientSide` | 字段 | 字段 | 方法 `isClientSide()` |
| 生物类别 | `Entity#getClassification(false)` | 同 1.20.1 | `EntityType#getCategory()`（vanilla API） |
| 生物群系 ID | `ResourceLocation` + `registryOrThrow` | `ResourceLocation` + `registryOrThrow` | `Identifier` + `lookupOrThrow` |
| `Holder` 取值 | `get()`（Forge 补丁） | `value()` | `value()` |
| 指令权限 | `hasPermission(0)` | `hasPermission(0)` | 无 `hasPermission(int)`，去掉 `requires`（`hasPermission(0)` 恒真，行为等价） |
| 按键分类 | `String`（`key.categories.merrysnow`） | 同 1.20.1 | `KeyMapping.Category`（lang key `key.category.merrysnow.main`） |
| 点击/悬停事件 | `new ClickEvent(Action, String)` / `new HoverEvent(Action, Component)` | 同 1.20.1 | `ClickEvent.CopyToClipboard(String)` / `HoverEvent.ShowText(Component)` 记录 |
| 行动栏消息 | `Player#displayClientMessage(Component, true)` | 同 1.20.1 | `Player#sendOverlayMessage(Component)` |
| GLFW 窗口句柄 | `Window#getWindow()` | 同 1.20.1 | `Window#handle()` |
| 雨量重定向目标 | `updateSkyBrightness` + `isRaining` 内的 `getRainLevel` | 同 1.20.1 | 仅 `isRaining`：26.1 的 `updateSkyBrightness` 改用 `EnvironmentAttributeSystem`，不再直接读雨量 |
| `ClientLevelMixin` / `FogRendererMixin` / `LevelRendererMixin` | 保留（**未注册**，见下） | 保留（**未注册**） | 不提供：目标方法（`ClientLevel#getSkyColor`、`FogRenderer#setupColor`、`LevelRenderer#renderSky` 等）已被移除 |

## 迁移中发现的既有问题（未改变行为）

以下问题在原项目中已经存在，本次重构**保持原行为**，仅记录：

1. `ClientLevelMixin`、`FogRendererMixin`、`LevelRendererMixin` 三个类没有被 `merrysnow.mixins.json`
   列出，因此在任何版本下都不会生效。`LevelMixin#getRainLevel` 的 RETURN 注入已经覆盖了这些
   重定向想做的事（再注册会重复相乘），所以保留「未注册」状态最接近原行为。
2. `enforceSnowCover` 默认值是 `"false"`，而 `SnowRules.shouldSnowCover` 在该分支返回
   `!Boolean.parseBoolean("false")`，即恒为 `true`，于是 `shouldSnow` 恒返回 `false`。
   也就是说默认配置下原版积雪是**被关闭**的。这是原项目的既有语义，已按原样迁移。
3. 原 `merrysnow.mixins.json` 的 `compatibilityLevel` 是 `JAVA_8`，但项目用 Java 17 编译，
   Mixin 会拒绝加载版本高于兼容等级的 mixin 类。本次按编译目标改为 `JAVA_17`（1.20.1）/
   `JAVA_21`（NeoForge）。
4. `common/src/main/resources/assets/merrysnow/lang/zh_cn.json` 的内容是其它模组
   （SmartKeyPrompts、DiligentStalker、SlashBlade）的翻译，不含 MerrySnow 自己的键。
   已按原样迁移，未做改动。
