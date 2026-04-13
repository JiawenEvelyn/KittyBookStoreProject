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


