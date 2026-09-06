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


