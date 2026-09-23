###### Welcome to KittyBookStore ######


@Data注解可以自动生成getter/setter/toString/equals/hashCode:
  @Getter
  @Setter
  @ToString
  @EqualsAndHashCode
  @RequiredArgsConstruvtor

MyBatis：
JDBC + SQL执行 + 结果映射 = 全封装

@Service是业务逻辑层 Bean, Spring会自动创建UserService service = new UserService(),
方便Spring管理对象生命周期

@Autowired
表示自动注入对象,等价于UserMapper mapper = Spring容器帮你new好的对象

二者区别：
@Service = 把类交给Spring管理
@Mapper = 从Spring中获取对象

REST API本质：HTTP路径 + JAVA方法映射，而在Spring Boot中，
@getMapping("/user/{id}")
public User getUser() {}
等价于：GET /user/{id} -> 调用getUser()这个方法

2025-11-12 00:28 UTC+2 First project push to github. With empty code and MySQL/H2 Database established!

JWT token的作用：
在使用前端浏览器登录访问后台时，第一次虽然用用户信息登录成功，但是后续的接口访问对后台服务器来说是全新的、
它并不能无法识别某个浏览器前台绑定的是某个已登录的用户；因此需要在未来实现登录鉴权时，让服务器对改次用户登录生成一个
唯一token并返回，后续浏览器上用户进行相关操作时，将token返回后台；后台鉴定成功后才允许浏览器基于该用户进行查询、操作相关数据

2026-09-15

DTO/VO/Entity
DTO 用于外部接口调用时携带的数据模型定义；
VO 用于接口返回响应时携带的数据模型；
Entity 用于服务处理业务逻辑、DB读写时使用的数据对象
通过三种模型将数据用途区分开，防止外部修改DB内部数据、提高安全性

@Valid注解
用在方法参数/字段上，支持对其进行级联校验；而不是用在类上
注解本身不没有实现代码，它只是一张标签，必须有人来读。
@Valid 的读者是处理 @RequestBody 的参数解析器 RequestResponseBodyMethodProcessor：
它先用 Jackson 把 JSON 反序列化成 DTO，然后遍历【方法参数上】的注解，
发现 @Valid 就调用 Hibernate Validator 执行 @NotBlank/@Size/@Email，
有错则抛 MethodArgumentNotValidException，由全局异常处理器转成 400 + 字段明细。
所以 @Valid 必须标在 Controller 的方法参数上 —— 标在 DTO 类声明上编译不报错，
但解析器只看参数注解，校验一条都不会执行

泛型类型推断
在方法返回类型前面用<T\>表示声明一个泛型类型；然后通过入参传入<T>数据，java会在调用的时候，
根据入参的实际类型来推断出<T\>的类型。


2026-09-19
日常git命令行列表：
git status  查看当前所在分支和提交情况
git swtich -c feat/xxx 新建分支并开始编码，switch是新版checkout
#.. coding ..
git diff 提交前确认自己改了什么
git add <具体文件路径> 挑出要提交的文件
git commit -m "feat: xxx" 提交
git push -u origin feat/xxx 推到远端

在结束了一个编码阶段后，回合到main中
git switch main
git pull
git merge --no-ff feat/xxx -m "Merge xxx"
git push
git branch -c feat/xxx

2026-09-22
@Mock和@InjectMocks的区别
@Mock
用于被测试对象的依赖上，是开发者在测试环节中可以控制的对象

@InjectMocks
用于被测试对象，比如service类，Mockito会真正new出一个service对象，内部的代码逻辑真的会
被执行到；
Inject的含义，service创造出来后，被@Mock的替身会塞进它的authorMapper字段