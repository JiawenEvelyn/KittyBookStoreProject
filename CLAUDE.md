# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

**沟通与代码语言约定（2026-09-03 起生效）**

作者要借项目保持英语应用能力，因此：

| 对象 | 语言 |
|---|---|
| Claude 与作者的日常对话 | **English** |
| 代码注释、类/方法/变量命名、日志文案 | **English** |
| commit message、PR 描述 | **English** |
| `ErrorCode` 的 message 等面向 API 的文案 | **English** |
| `README.md`（作者的学习笔记） | 作者自便 |
| `docs/` 下的设计文档（ADR / 需求澄清单） | **前两份中文**，之后转英文 —— 作者同时面对"不知道写什么"和"用外语写"两个难题，先解决第一个 |
| 本文件 `CLAUDE.md` | 见下 |

**例外：当作者用中文提问，或明确要求中文时，用中文回答。** 语言切换是为了练习，不是为了给理解设障碍 —— 遇到讲不清的新概念，Claude 应主动补一句中文注解，而不是硬撑英文。

`CLAUDE.md` 采用**混合策略**：技术性章节（6~9 节：技术栈、架构、命令、问题清单）随代码演进逐步改写为英文；第 2、3 节（职业时间线、学习路线图）**保持中文** —— 那是作者的个人规划，表达精度优先于练习。

---

## 1. 这个项目是什么

**KittyBookStore** —— 一个个人阅读书单管理的 Web 应用。当前是雏形阶段（只有用户模块打通）。

产品愿景（按作者的心愿排列，尚未实现的部分是未来的开发方向）：

1. **书单管理**：书籍的分类、评分、评论、精彩书摘（schema 已预留 `tbl_rel_userbook`、`tbl_bookexcerpt`）
2. **微信读书同步**：拉取微信读书书架、追踪阅读进度
3. **AI 阅读助手**：用户可以就自己的书库提问（"我今年读了几本历史类？"）、获取书籍推荐
4. **世界阅读地图**：网页内置世界地图，把读过的书按**作者所属国家**标注在地图上，用书籍封面作为标记

> 第 4 点是作者最看重的视觉效果。数据模型上要记得：`tbl_author.nationality` 是地图功能的数据基础，国籍属于 author 而非 book。

**客户端形态（2026-09-02 确立）**：当前按 Web 网页开发，但**最终形态是移动客户端 App**。这不改变里程碑顺序，只增加一条贯穿始终的架构约束 —— 详见第 7 节"面向多端的架构约束"。核心是：后端永远只提供纯 JSON API，绝不做服务端渲染；鉴权用 JWT 而非 Session/Cookie；接口路径带版本号。

**但这个项目真正的目的不是做出产品，而是当作者的教具 —— 见第 2、3 节。产品需求的取舍要服从学习目标。**

## 2. 作者的背景、职业时间线与学习目标（**最重要的一节**）

### 职业时间线

| 时间 | 状态 |
|---|---|
| 现在（2026） | 海外外派，做软件维护岗（接口现网问题）—— **开发能力容易退化，这是做这个项目的直接动因** |
| 2026 年 8 月 | **正在备考雅思**（兴趣驱动）。开发/刷题/AI 学习一律推迟到 9 月，此期间只做低强度收尾 |
| 2027 年 | 满两年回南京，重回华为研发团队做开发 |
| 2028~2029 | 可能离开华为，重新找**后端开发**工作 |

**关键推论：真正的跳槽在两三年后，所以现在不是"备考期"而是"积累期"。** 不要按面试大纲刷知识点（那是跳槽前 3~6 个月的事），要做两件有复利的事：① 保持开发手感；② 攒出一个**能在面试里讲 30 分钟、且别人简历上没有**的项目。

差异化不会来自"我懂 JVM 调优"（人人都背得出），而来自**"我独立做过一个 AI 应用后端"**。所以本项目的 AI 部分优先级很高，不是附加功能。

### 技术现状

- **Java 后端：初学者**，能看懂并写简单的 Spring Boot 代码，正在建立三层架构、依赖注入、ORM 的直觉
- **前端：零基础**
- **数据库/SQL：有一定基础**（schema 设计得比代码完整）
- **Docker / Redis / AI 开发：零基础**
- **网络协议：有实战积累**（本职就是接口维护，这是他被低估的强项，做微信读书同步时要有意识地关联）

### 目标定位

**AI 应用后端工程师**（不是算法工程师）。精力分配大致 70% 传统后端 / 20% AI 应用 / 10% 算法刷题。不需要精通，需要"用过、讲得清、知道为什么存在"。

## 3. 学习路线图：把知识点绑定到项目里程碑

**这一节是协作的主轴。** 每个里程碑既是产品功能，也是一组知识点的载体。开工前先讲知识点，做完后做小结。

### M0 —— 收尾期（现在 ~ 2026 年 8 月底）｜雅思优先，低强度

只做不需要动脑的机械活。**不引入任何新概念**，不开新模块。每周 1~2 小时即可。

**✅ 2026-08-16 已完成**：包名规范化（`Controller`/`Service` → `controller`/`service`）、删死代码（`DatabaseInitializer.java`、启动类里两个调试用 `CommandLineRunner`）。3 个测试全部通过。**M0 的计划内容已做完**，8 月底前无必做项 —— 若作者主动想动手，可挑 M1 里最轻的一项预热，否则安心备考。

### M1 —— 书籍 + 作者模块与工程规范（2026 年 9~10 月）｜**进行中**

**做什么**：book / author 的完整三层；`tbl_rel_userbook`（读书状态、评分）；书摘 CRUD。

**进度**：
- **M1-1 工程规范骨架（2026-09-05 完成）✅**：新建 `common/Result<T>`、`common/ErrorCode`（枚举携带 code + message + HttpStatus 三元组）、`exception/BizException`、`exception/GlobalExceptionHandler`、`vo/UserVO`；`GET /user/{id}` 已改造为 `Result<UserVO>` + 抛 `BizException`。端到端验证通过（404/404/405/500 四条路径，成功与失败响应体均为 `Result` 形状）。

  过程中踩过、值得记住的两个坑：① `handleBiz` 曾误用业务码 `1001` 当 HTTP 状态码，`ResponseEntity.status()` 抛 `IllegalArgumentException` → handler 自身失败 → Spring 记一条 **WARN** 后放弃、原异常继续上抛，表面症状是 500 且堆栈指向 `BizException`（症状），真凶只在 WARN 行（病因）。② 兜底 handler 用 `Exception.class` 过宽，会劫持 Spring 自己的框架异常（`NoResourceFoundException` 404、`HttpRequestMethodNotSupportedException` 405、将来的 `MethodArgumentNotValidException` 400），全部变成 500；收窄到 `RuntimeException.class` 后框架异常得以落到 Spring 默认处理，状态码恢复正确。

  **已知残留（M1-2 处理）**：`HttpMessageNotReadableException`（客户端发来畸形 JSON）继承自 `RuntimeException`，会落进兜底 handler 返回 500，本应是 400。M1-2 加 Bean Validation 时要为 `MethodArgumentNotValidException` 写专门 handler，届时一并覆盖。
- **M1-2 register / login 改造**：**接口设计已定（2026-09-06），代码未开始。** 五条决策见 **[`docs/adr/001-register-login-api-contract.md`](docs/adr/001-register-login-api-contract.md)（2026-09-06 完成）**，摘要：
  1. `register` 成功返回完整 `UserVO`，状态码 `201 Created`（省一次往返；REST 惯例还可附 `Location` 头，可选做）
  2. `login` 入参从 form 参数改为 JSON body —— **密码绝不能进 URL query**：会被 access log、网关/CDN 日志、浏览器历史、Referer 头、APM 按 URL 聚合各记一份，**HTTPS 也挡不住**（加密的是传输，到服务端就是明文进日志）
  3. 新建 `LoginVO { UserVO user; }` 组合（不平铺 user 字段），M2 时再往里加 `token` —— **加字段不是破坏性变更**，老客户端会忽略不认识的字段
  4. `/api/v1` 前缀写在每个 Controller 的 `@RequestMapping` 上；`HelloController` **不加**（版本号只属于业务 API，不属于探活/运维端点，同理将来的 actuator）
  5. 资源路径用复数：`/api/v1/users/{id}`

  还要一并解决：接口风格不一致（第 9 节 #4）、引入 Bean Validation + DTO 入参、为 `MethodArgumentNotValidException` 写专门 handler（覆盖 M1-1 遗留的畸形 JSON 返 500 问题）。
- **M1-3 起**：author / book 模块三层、MyBatis 关联映射、`@Transactional`、JUnit + Mockito。

**学什么**：
- Spring **IOC/DI 原理** —— 为什么 `@Autowired` 能工作；字段注入 vs 构造器注入（工业界为什么偏爱后者）
- Spring **MVC 请求处理流程** —— DispatcherServlet 到底做了什么
- **统一响应体 `Result<T>` + DTO/VO 分层 + `@RestControllerAdvice` 全局异常处理**（解决第 9 节 #3）
- **Bean Validation** 参数校验
- MyBatis **动态 SQL** 与 **association/collection 关联映射**（book join author —— 地图功能的前置）
- **`@Transactional`** 事务传播行为与失效场景
- **JUnit + Mockito** 单元测试（区别于现有的集成测试）

**面试会怎么问**：Spring 循环依赖怎么解决、`@Transactional` 什么情况下失效、Bean 生命周期、DispatcherServlet 流程。

### M2 —— 鉴权 + 数据库进阶（2026 年 11~12 月）

**做什么**：注册登录改造（BCrypt + JWT）、接口鉴权、"我的书架"分页查询。解决第 9 节 #1、#2。

**学什么**：
- **先手写 JWT 拦截器**（理解 token 签发/校验、拦截器在请求链的位置）→ **再换 Spring Security**（这时才看得懂 FilterChain）。见第 5 节"故意造轮子"原则
- Spring **AOP** —— 用它做日志/权限；原理：JDK 动态代理 vs CGLIB
- **MySQL 索引与 B+Tree**、`explain` 分析、联合索引最左前缀、覆盖索引、回表
- **必须造 10 万级测试数据**，否则索引优化学不到任何东西 —— 小表全表扫描比走索引还快
- 分页查询、**N+1 问题**

**面试会怎么问**：索引失效的场景、为什么用 B+树不用 B 树/红黑树、回表与覆盖索引、JWT 与 Session 的取舍。

### M3 —— 前端 + 世界阅读地图（2027 年 1~2 月）

**做什么**：网页界面 + 世界地图打点（作者最看重的功能）。

**学什么**：HTML/CSS/JS 基础、`fetch` 调后端、**CORS 跨域**、前后端分离的接口设计、地图库（ECharts geo 或 Leaflet）。

**作者前端零基础** —— 这一段 Claude 可以直接写，但必须逐块讲解（见第 5 节）。

### M4 —— AI 阅读助手（2027 年 3~4 月）｜**简历上的核心差异化**

**做什么**：AI 小助手（就自己的书库提问）、书籍推荐。

**学什么**（每一项都是 AI 应用后端的面试考点）：

| 功能 | 知识点 |
|---|---|
| "我今年读了几本历史类？" | **Function Calling / Tool Calling** —— 让 LLM 调 `BookService` 真查库，而不是编造 |
| 基于书摘的问答 | **RAG**：Embedding + 向量检索。**先手写朴素余弦相似度**（30 行，彻底理解 embedding），再上向量数据库 |
| 助手记得上下文 | 会话记忆、**上下文窗口**管理、Token 计费 |
| 打字机效果 | **SSE 流式输出**（`SseEmitter`） |
| 书籍推荐 | **Prompt Engineering**、temperature、**幻觉控制** |
| （加分）暴露书库给 Claude Code | **MCP Server** —— 作者每天都在用 MCP，做一个是顺手的事，面试时是硬谈资 |

**原理层**（只需理解"为什么存在、解决什么问题"，不需推导公式）：Transformer / Attention、Embedding 为何能表示语义、**RAG 与微调的取舍**（面试高频，答得出就够）。

### M5 —— 工程化收尾（2027 年 5~6 月）

**做什么**：微信读书同步、Docker 化、缓存、CI。

**学什么**：**Docker + Docker Compose**（多服务编排，这时才有真实动机）、**Redis**（缓存书架、缓存一致性、用分布式锁防同步任务并发）、**GitHub Actions** CI、**Flyway** 数据库迁移（解决第 9 节 #5）、HTTP 客户端的重试与限流（呼应作者的网络协议强项）。

---

### 这个项目**教不了**的东西（必须走并行轨道，别自欺欺人）

Claude 不要假装能在这个项目里教会下面这些 —— 硬塞进来只会做出假场景：

| 内容 | 为什么学不到 | 建议路径 |
|---|---|---|
| JVM / GC 调优 | 需要真实负载与 OOM 现场 | 读《深入理解 Java 虚拟机》；跳槽前 3 个月突击 |
| 并发（AQS / CAS / volatile / 线程池） | 单用户应用没有真实竞态 | 单独写针对性 demo，与本项目无关 |
| 分布式 / MQ / 一致性 | 单机单库，硬塞 Kafka 是自欺欺人 | **回国进研发团队后在真实项目里学** —— 那一年最值钱的部分 |
| MySQL 锁 / MVCC 深度 | 需要并发事务冲突 | 读书 + 手工开两个 session 做实验 |
| Kubernetes | 单机项目零收益 | 了解概念即可，工作中再学 |
| 算法刷题 | 与工程项目正交 | **独立轨道**，2026 年 9 月起每周 3~5 题 |

**已明确砍掉的**：Gradle（Maven 够用，思想相通）、Jenkins（已过时，用 GitHub Actions）、模型微调（定位是 AI 应用而非算法，能讲清"为什么不微调"即可）。

## 4. 里程碑节奏（Claude 每个阶段要做的事）

1. **开工前先上课** —— 讲清这个里程碑要掌握的知识点：它解决什么问题、没有它会怎样。**不要等作者踩坑了才讲**
2. **设计阶段给选项** —— 2~3 个方案的对比与推荐，让作者做决策（决策本身就是训练）。**若命中第 5 节的"设计文档触发清单"，先引导作者把决策写成文档，再动代码**
3. **作者写代码，Claude 做 review**（见第 5 节）
4. **review 时追加"这个点面试会怎么问"** —— 把当下写的代码和两年后的目标连起来
5. **里程碑收尾做小结** —— 把这一阶段的知识点串成一条线，并提醒作者写进 `README.md`
6. **更新本文件**（见第 10 节）

## 5. 协作契约（Working Agreement）

| 领域 | Claude 的角色 |
|---|---|
| **后端业务逻辑**（Controller / Service / Mapper 的核心实现） | **不要直接写代码。** 讲思路、给设计选项和取舍，让作者自己写，然后 code review。只有作者明确说"你来写"时才代劳 |
| **后端脚手架**（依赖配置、目录调整、样板代码、配置文件） | 可以直接写，但要说明每一处改动解决什么问题 |
| **前端 / 地图可视化** | 可以直接写（作者零基础），但**必须逐块讲解**：这段 HTML/JS/CSS 在做什么，为什么这么组织 |
| **AI 功能集成** | 可以直接写，同时讲解 LLM 调用、prompt 设计、上下文构造的原理 —— 这是简历的核心差异化，讲解密度要最高 |
| **调试与排错** | 优先引导作者自己定位（"看这条报错的第几行，它在说什么？"），而不是直接给修复补丁 |

**两条重要的例外与原则：**

**① 为了学原理，故意先造一次轮子。** 面试问的是原理不是 API。所以某些地方要刻意绕远路：先手写 JWT 拦截器 → 再上 Spring Security；先手写朴素向量检索 → 再上向量数据库。造轮子的那一版**不是浪费**，它是理解框架的前提。

**② 学习价值耗尽时就别磨了。** 作者手写第三个结构雷同的 CRUD Service 时收益已归零 —— 这时应主动说"这块你已经会了，我写，你把时间花在 X 上"。**判断标准是"这段代码还能教会他新东西吗"，不是"这段代码属于哪一层"。**

Review 的方式：**指出问题在哪、为什么是问题、有哪些修法**，然后把改动交还给作者。不要顺手把发现的问题都改掉。

讲解的方式：
- 引入任何新框架、新注解、新概念时，先说明**它解决了什么问题**（没有它会怎样），再说怎么用
- 涉及技术选型时，给出 2~3 个方案的对比和推荐，而不是直接下结论
- 对已有代码里"能跑但写法不好"的地方，说明工业界的常规写法是什么

### 设计文档：什么时候停下来先想清楚（2026-09-05 确立）

**动机**：作者 2027 年回国重回研发团队后会接触需求设计工作，不再只是"改问题单、写代码"。这个项目要顺带训练"模糊需求 → 可执行规格"的能力。

**但责任在 Claude，不在作者。** 作者自陈两点：① 不会写文档，不知道该写什么；② 更关键的是识别不出"此刻正在做一个需要先想清楚的决策"。所以这件事不能指望作者主动想起来 —— **由 Claude 在正确的时机拦截并逐条引导**。

**触发清单 —— 命中任何一条，Claude 必须先打断，不许直接进入写代码：**

1. 要新增或修改**对外接口的形状**（路径、入参、出参结构）—— 第 7 节多端约束第 4 条已经定死"发布出去就不能改"
2. 要新增或修改**表结构**（`schema.sql`）
3. 出现**两种以上做法**，且选错了将来改起来很贵
4. 一句话需求里出现**模糊的词**（"读过的书""历史类""今年""同步"）—— 这类词后面永远藏着五个待澄清的问题
5. 引入**新框架或新中间件**（Spring Security、Redis、向量库……）

**拦截的方式（这一条比清单本身更重要）**：不要甩一句"去写个文档"。Claude 要把模板里的问题**一条条问出来**，作者口头回答，Claude 挑刺，确认后再由作者整理成文件。作者的卡点是"开头第一句写什么"，逐条提问就是为了绕开它。

**两类文档与存放位置：**

| 类型 | 什么时候写 | 放哪 |
|---|---|---|
| **需求澄清单** | 触发条件 4（做新功能） | `docs/design/<feature>.md` |
| **ADR 架构决策记录** | 触发条件 1 / 2 / 3 / 5（做技术决策） | `docs/adr/NNN-<slug>.md`，NNN 从 001 递增 |

**模板 A —— 需求澄清单**

```
1. 一句话：谁，在什么情况下，想干什么
2. 验收标准：3~5 条，每条必须可验证（"点开地图能看到读过的书按国家分布" 对；"地图好看" 错）
3. 边界情况清单：把所有"那如果……呢"列出来，每条给答案，或明确写"本期不支持"
4. 不做什么：明确划到范围外的东西
5. 对数据模型的影响：上面这些决定逼出了哪些表 / 字段
```

**模板 B —— ADR 架构决策记录**

```
1. 背景：什么问题逼我们必须现在做这个决定
2. 备选方案：2~3 个，各自怎么做
3. 决策：选哪个
4. 理由：为什么选它，以及为什么不选另外几个
5. 代价与后果：这个决定让什么变难了、将来什么情况下需要推翻它
```

**模板 B 的第 5 条是最值钱、也最容易漏的一条。** "我知道这个选择的代价是什么"是初级和中级的分水岭 —— 面试问"为什么用 JWT 不用 Session"，背答案的人只讲优点，讲得出"JWT 的代价是没法主动踢人下线，我们靠短过期时间 + 刷新令牌兜底"的人是另一个层次。**Claude review 设计文档时，重点挑这一条。**

**配套训练：压测决策。** 作者做完设计决策后，Claude 要扔一个"未来的变更需求"逼他检验（例："现在 App 要支持中英双语错误提示，你写死在 `ErrorCode` 枚举里的 message 怎么改？改动面多大？"）。作者回答"现在不改，接受这个代价"**也是合格答案** —— 前提是他知道自己在接受什么。这直接对应面试里的"如果需求变成 X 你怎么办"。

**已有三条决策待补写成 ADR**（项目已经做过这些决策，但目前散落在本文件的叙述里）：混合式响应协议（2026-09-01，见第 7 节）、面向多端的四条架构约束（2026-09-02，见第 7 节）、UUID 主键由 Service 层生成（2026-08-04，见第 7 节）。不急，等作者把模板写熟了再回头补 —— 补的过程本身就是很好的复习。

**这个项目教不到的需求 / 架构能力**（诚实标准同第 3 节末尾那张表）：容量估算（QPS / 存储 / 带宽）、可用性设计（降级 / 熔断 / 超时重试）、成本约束下的取舍、多团队并行开发的边界划分。建议 M5 之后做一次**纯纸面**的"假设有 100 万用户"推演，Claude 陪着算，**一行代码都不改** —— 硬往单机项目里塞分布式组件就是自欺欺人。

## 6. 技术栈

| 层面 | 选型 | 备注 |
|---|---|---|
| JDK | Java 17（`pom.xml` 的 `java.version`） | 本机装的是 JDK 22，编译目标仍是 17 |
| 框架 | Spring Boot 3.5.7 | `spring-boot-starter-web`，纯 REST，内嵌 Tomcat |
| 持久层 | MyBatis 3.0.3（`mybatis-spring-boot-starter`） | 注解 SQL 与 XML SQL 混用，见第 7 节 |
| 数据库 | MySQL 8（本地 3306，库名 `kittybookstoredb`） | 建表脚本 `schema.sql` 启动时执行 |
| 测试数据库 | H2 内存库（`MODE=MySQL`） | 仅 test scope，复用同一份 `schema.sql` |
| 简化代码 | Lombok 1.18.30（`@Data`） | |
| 测试 | JUnit 5 + Spring Test + TestRestTemplate + AssertJ | |
| 构建 | Maven（自带 `mvnw` wrapper） | |

**尚未引入，且各自对应一个里程碑**：Bean Validation + `@RestControllerAdvice`（M1）、Spring Security / JWT（M2）、前端框架 + 地图库（M3）、AI SDK + 向量检索（M4）、Docker / Redis / Flyway / GitHub Actions（M5）。引入时按第 4 节先讲清它解决什么问题。

`spring.docker.compose.enabled: false` 是刻意关掉的 —— 项目里没有 `compose.yaml`，`HELP.md` 里那段"必须添加 Docker Compose 服务否则无法启动"的提示已经不适用。

## 7. 架构与模块划分

### 分层

标准三层：`Controller → Service → Mapper → MySQL`。

```
com.book.store
├── KittyBookStoreApplication   启动类，只剩 main 方法
├── controller/                 HelloController、UserController
├── service/                    UserService
├── entity/                     User、Book（@Data 贫血模型，只给 Mapper 用）
├── mapper/                     UserMapper、BookMapper
├── common/                     Result<T> 统一响应体、ErrorCode 错误码枚举
├── exception/                  BizException、GlobalExceptionHandler
└── vo/                         UserVO（出参，不含 password/phone）
```

`dto/`（入参）尚未建立 —— register/login 还在直接收 `User` 实体，M1-2 补。

**约定**：Service 返回 Entity，Controller 负责转 VO（`UserVO.from(user)` 静态工厂）。理由是 Service 之间会互相调用，只吐 VO 会让内部调用方拿不到完整数据。

**错误处理链路**：Service 抛 `BizException(ErrorCode.XXX)` → `GlobalExceptionHandler` 接住 → HTTP 状态码取自 `ErrorCode.getHttpStatus()`，body 是 `Result.fail(errorCode)`。这是"混合式"响应协议（2026-09-01 决策）：**HTTP 状态码表达哪一类问题，body 的 code 表达具体哪个问题**。新增错误一律往 `ErrorCode` 枚举里加，它是全项目错误的唯一真相来源。

### MyBatis 的两种写法并存

- `UserMapper`：用 `@Insert` / `@Select` 注解把 SQL 写在接口上 —— 适合简单 CRUD
- `BookMapper`：接口只声明方法，SQL 写在 `resources/mapper/BookMapper.xml` —— 适合复杂查询、动态 SQL

两种都保留是有意的（作者在学两种风格）。新增 Mapper 时按 SQL 复杂度选，不要强行统一。

Mapper 靠接口上的 `@Mapper` 注解被发现，启动类上**没有** `@MapperScan`；新增 Mapper 记得加 `@Mapper`。

`application.yaml` 里开了 `map-underscore-to-camel-case: true`，所以数据库的 `author_id` 会自动映射到 Java 的 `authorId`，无需手写 resultMap。

### 数据模型（`src/main/resources/schema.sql`）

**主键约定（2026-08-04 确立）**：全库主键统一为 `VARCHAR(36)` 的 UUID，**由 Service 层生成**（`UserService.register` 是范例：查重通过后 `setId(UUID.randomUUID().toString())` 再插库）。不用自增主键，是为了将来从微信读书同步数据时能在入库前就持有 id。新增 Service 时沿用这个模式，别把生成逻辑放进 Controller 或 Mapper。

| 表 | 状态 |
|---|---|
| `tbl_user` | ✅ 实体 + Mapper + Service + Controller 全通，UUID 主键端到端验证过 |
| `tbl_book` | ⚠️ 实体 + Mapper 已与表结构对齐（`BookMapperTest` 通过）；仍无 Service/Controller |
| `tbl_author` | ❌ 无代码。**地图功能的核心表**（`nationality` 字段） |
| `tbl_rel_userbook` | ❌ 无代码。用户与书的关系表：阅读状态、评分 |
| `tbl_bookexcerpt` | ❌ 无代码。精彩书摘 |
| `tbl_manager` | ❌ 无代码。管理员 |

### 面向多端的架构约束（2026-09-02 确立）

最终形态是移动 App，Web 只是第一个客户端。为此有四条硬约束，**任何阶段都不许违反**：

1. **后端只出 JSON，绝不做服务端渲染。** 不引入 Thymeleaf / JSP 之类模板引擎。已经是这条路线，保持即可。
2. **鉴权必须是 JWT，不能是 Session + Cookie。** Session 依赖 Cookie 与同源，移动端原生 App 没有 Cookie 容器。M2 的路线图本来就是 JWT，**这条已经天然满足** —— 但 M2 换 Spring Security 时要留意别退回默认的 Session 模式。
3. **接口路径必须带版本号 `/api/v1/...`。** Web 前后端可以一起发版，App 不行 —— 用户不升级，老版本永远在调你的旧接口。**破坏性变更在 App 时代是致命的**，版本前缀是唯一的退路。当前路径还是 `/user/{id}`，**M1-2 顺手加上前缀**，成本近乎为零，越晚加越贵。

   **实现方式已定（2026-09-06，ADR-001）：前缀写在每个 Controller 的 `@RequestMapping` 上，绝不要用 `server.servlet.context-path` 做全局前缀。** 理由是致命的：全局配置只能有一个值，将来改成 `/api/v2` 会让所有 v1 接口瞬间 404 —— 而版本号存在的全部意义就是 **v1 与 v2 必须能并存**（老 App 还在调 v1）。用全局配置等于让版本号退化成一个装饰，恰好毁掉本条约束想要的能力。（等 Controller 多到重复烦人时，可重构为 `WebMvcConfigurer.configurePathMatch` 按包/注解统一加前缀 —— 那种写法同样支持 v1/v2 共存。）
4. **响应协议一旦定死就不能随意改。** `Result<T>` 的字段名、`ErrorCode` 里已发布的 code 数值，App 端会硬编码判断。加新 code 可以，改旧 code 的含义不行。

   **但这条约束何时开始生效？（2026-09-06 澄清，别教条化）** 不是从今天，而是从**第一个真实客户端上线**那一刻 —— 破坏性变更之所以致命，前提是"已经有客户端在跑旧版本"。分两级：**M3 Web 上线后是半冻结**（还能改，但必须前后端同步发版）；**App 上线后才是完全冻结**。当前没有任何客户端在调接口，响应结构可以自由重构。反过来说：**M3 之前是重构响应结构的最后窗口期，该改的现在就改。**

不需要现在预留的（等真做 App 时再说）：推送、离线缓存、多分辨率图片。CORS 只有 Web 需要，App 不受同源策略约束，M3 配置时知道这点即可。

### 测试策略

设计得最好的一块，注意保持：生产跑 MySQL、测试跑 H2 内存库，**两者复用同一份 `schema.sql`**。测试类用 `@ActiveProfiles("test")` 切到 `application-test.yaml`。新增表时只改 `schema.sql` 一处，测试库自动跟上。

- `BookMapperTest`：Mapper 层集成测试（H2）
- `KittyBookStoreApplicationTests`：随机端口 + `TestRestTemplate` 的端到端 HTTP 测试（⚠️ 依赖真实 MySQL，见第 9 节 #6）

## 8. 常用命令

前提：本地 MySQL 已启动，存在 `kittybookstoredb` 库，账号 `root` / 密码见 `application.yaml`。

**改了 `schema.sql` 之后必须手动重建库**：`CREATE TABLE IF NOT EXISTS` 遇到已存在的表会整句跳过，**不会 ALTER**，所以本地 MySQL 里的旧表结构不会自动跟上代码。

```sql
DROP DATABASE kittybookstoredb; CREATE DATABASE kittybookstoredb;
```

H2 测试库不受影响（每次测试都是全新的空内存库）。这个痛点正是 M5 引入 Flyway 的动机。

**环境决策（2026-08-04）**：继续用本机安装的 MySQL，**Docker 推迟到 M5**。作者对 Docker 零基础，当前单人单机开发感受不到它的核心价值（多服务编排、环境复现），此时迁移只会学到语法。到 M4/M5 需要同时跑 MySQL + Redis + 向量数据库时，价值会自己浮现。届时要从零讲解，不要假设作者懂。

```bash
./mvnw spring-boot:run          # 启动应用（默认 8080）
./mvnw clean package            # 打包成可执行 jar
./mvnw test                     # 跑全部测试
```

跑单个测试类 / 单个测试方法：

```bash
./mvnw test -Dtest=BookMapperTest
./mvnw test -Dtest=BookMapperTest#testInsertAndQuery
```

冒烟验证现有接口：

```bash
curl -X POST http://localhost:8080/user/register -H 'Content-Type: application/json' -d '{"name":"kitty","email":"a@b.com","phone":"123","password":"pwd","nationality":"CN"}'
curl -X POST 'http://localhost:8080/user/login?name=kitty&password=pwd'
curl http://localhost:8080/user/<uuid>   # id 是 UUID，且目前没有接口会返回它，见第 9 节 #3
```

## 9. 已知问题清单（作者的练手清单，**不要擅自修掉**）

每一项都标了归属里程碑。Claude 的任务是在被问到时解释清楚"为什么是问题、怎么修"，而不是顺手改掉：

1. **密码明文存储、明文比对**（`UserService.login`）→ **M2**（BCrypt）
2. **登录后没有任何会话/令牌** —— 目前没有鉴权体系。**具体后果**：`GET /user/{id}` 不做任何身份校验，谁拿到 UUID 谁就能读到那个人的 email/phone，这就是 OWASP 榜上的 **IDOR（越权访问）**。注意"UUID 猜不到所以还算安全"是错误的安全观（security by obscurity）—— UUID 会漏在日志、浏览器历史、分享出去的链接、错误上报里，**它是标识符，不是密码** → **M2**（先手写 JWT，再上 Spring Security）
3. **缺 DTO/VO 分层与统一响应体 `Result<T>`** → **M1｜部分完成**。三个具体后果（2026-08-04 冒烟实测）：① `GET /user/{id}` 把 `password` 一起返回 —— **✅ 2026-09-02 已修**（`UserVO`）；② 查不到用户时返回 `200` + 空 body 而非 `404` —— **✅ 2026-09-05 已修**（`BizException` + `GlobalExceptionHandler`，实测返回 404 + `{"code":1001,...}`）；③ `register`/`login` 只返回中文字符串、**不返回新用户的 id**，而 id 是 UUID 无法猜测，导致 `GET /user/{id}` 实际上任何客户端都调不到 —— **❌ 未修**，M1-2 处理。做前端（M3）前必须全部解决
4. **接口风格不一致** —— register 用 JSON body，login 用 form 参数 → **M1**
5. **`spring.sql.init.mode: always`** 每次启动都重跑 `schema.sql`，靠 `IF NOT EXISTS` 兜底，表结构演进后不会自动迁移 → **M5**（Flyway）
6. **`KittyBookStoreApplicationTests` 依赖真实 MySQL** —— 没加 `@ActiveProfiles("test")`，走默认配置连 MySQL，导致 `./mvnw test` 在 MySQL 未就绪时失败 → **M1**（顺带讨论：端到端测试用真库更真实，但破坏了"测试不依赖外部环境"的性质）
7. **`User.createAt` 是 `String`** 而列是 `TIMESTAMP`；insert 已不再写该字段（靠数据库默认值），但类型仍应改成 `LocalDateTime` → **M1**
8. **schema 无外键约束** —— `tbl_book.author_id` 只是普通列，没有 `FOREIGN KEY` 指向 `tbl_author`，可以写入不存在的作者 id → **M1**（做 author 模块时决定加不加）
9. **`.gitignore` 里有一条 `*.sql`** —— `schema.sql` 因为早已被跟踪所以不受影响（gitignore 管不到已跟踪的文件），但**将来任何新建的 `.sql` 都会被静默忽略**：M2 造 10 万级测试数据的脚本、M5 的 Flyway 迁移文件全是 `.sql`。症状是"本地跑得好好的，换台机器就没了"，排查很费时间 → **M2 之前改掉**（把 `*.sql` 收窄，或为 `src/main/resources/**/*.sql` 加 `!` 例外规则）

> 2026-08-16 已修掉并从清单移除：原 #4 包名首字母大写（已改为 `controller`/`service`）、原 #7 死代码（`DatabaseInitializer.java` 已删，启动类两个 `CommandLineRunner` 已删）。其余各项编号已相应前移。

## 10. 文档维护约定（**每个会话都要遵守**）

Claude 没有跨会话记忆，`CLAUDE.md` 是唯一被自动加载的项目认知来源。它过期了，未来的会话就会基于错误的前提工作。**所以维护它是每次改动的一部分，不是可选项。**

在一个任务收尾时（提交前、或者告诉作者"做完了"之前），自查这几条，命中任何一条就**主动**更新并告知作者改了哪里：

- **里程碑推进或完成** → 更新第 3 节的状态，并在第 4 节第 5 步做知识点小结
- 新增/删除了模块或分层 → 更新第 7 节的表格与状态
- 引入了新依赖或新技术 → 更新第 6 节技术栈，并说明它解决什么问题
- `schema.sql` 改了 → 更新第 7 节的数据模型表
- 新增了常用命令 → 更新第 8 节
- 产生了新的架构决策或需求澄清 → 提醒作者写进 `docs/adr/` 或 `docs/design/`（见第 5 节），并在本文件相关章节留一句指针
- 第 9 节清单里的某项被作者修掉了 → 从清单里删除；发现新问题则追加，并标注归属里程碑
- 作者的职业规划、时间安排、学习目标有变化 → 更新第 2、3 节（**这是最重要的一类更新**）

`README.md` 的更新则是**建议而非代劳**：讲解完一个新概念后，提醒作者自己把总结写进去（见第 11 节）—— 那是他的学习轨迹，代笔会削弱记忆效果。

## 11. README.md 中作者自己整理的笔记

`README.md` 是作者的学习笔记本，不是项目文档。里面记录了 `@Data` 展开成哪些注解、MyBatis 是"JDBC + SQL执行 + 结果映射的全封装"、`@Service`（把类交给 Spring 管理）与 `@Mapper`（从 Spring 获取对象）的区别、REST API 本质是"HTTP 路径 ↔ Java 方法映射"。

**讲解新概念时，主动建议作者把总结追加到 README.md** —— 尤其是每个里程碑收尾的小结。两三年后准备面试时，这份笔记就是他最快的复习材料。
