jpa:

    JPA:Java Persistence API的简称
    	java持久化规范，用于管理javaee和javase环境中的持久化以及对象/关系映射的java API
    
    	常见实现：
    		EclipseLink、Hibernate、Apache OpenJPA
    	核心概念：
    		实体：
    			表示关系数据库中的表
    			每个实体实例对应于表中的行
    			实体类必须用@Entity注解
    			实体类必须有一个public或protected的无参构造函数
    			实体实例被当作值以分离对象方式进行传递(网络传输) 必须实现Serializable接口
    			唯一的对象标识符：简单主键@Id、复合主键@EmbeddedId、@IdClass
    		关系：
    			一对一：@OneToOne
    			多对一：@ManyToOne
    			一对多：@OneToMany 
    			多对多：@ManyToMany 
    			
    EntityManager接口：
    	定义用于与持久性上下文进行交互的方法
    	创建和删除持久实体实例,通过实体的主键查找实体
    	允许在实体上运行查询
    	

Spring data jpa:

    spring data jpa：
    	是spring data 家族的一部分
    	对基于jpa的数据访问层的增强支持
    	更容易构建基于使用spring 数据访问技术栈的应用程序
    
    常用接口：
    	CurdRepository:增删改查接口
    	PagingAndSortingRepository:分页和排序接口
    	
    自定义接口：继承Repository接口（重要）
    	interface PersonRepository extends Repository<User, Long> {
    
      	List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);
    
      	// 启用 distinct 标识
      	List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
      	List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);
    
      	// 给独立的属性启用 ignoring case 
      	List<Person> findByLastnameIgnoreCase(String lastname);
      	// 给所有合适的属性启用 ignoring case 
      	List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);
    
      	// 启用 ORDER BY
      	List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
      	List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
    }

spring boot集成spring data jpa：

    环境准备：
    	mysql数据库
    	spring data jpa
    	hibernate
    	mysql驱动
    	
    	springboot初始项目
    
    集成步骤：
    	1.gradle中添加依赖：
    		compile('org.springframework.boot:spring-boot-starter-data-jpa')
        	compile('mysql:mysql-connector-java:8.0.11')
        	//h2数据源类似与druid、dpcp、c3p0
        	runtime('com.h2database:h2:1.4.193')
    	2.相关配置：application.properties
    		#开启模板缓存（默认值：true）
            spring.thymeleaf.cache=false
            #模板编码
            spring.thymeleaf.encoding=UTF-8
            #使用html5标准
            spring.thymeleaf.mode=HTML5
    
            #数据源
            spring.datasource.url=jdbc:mysql://localhost/blog?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC 
            spring.datasource.username=root
            spring.datasource.password=1995
            spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    
            #JPA
            spring.jpa.show-sql = true
            #每次启动的时候先删除数据库再创建
            spring.jpa.hibernate.ddl-auto=create-drop
    	3.相关页面thymeleaf模板：
    		templates\fragments\footer.html
    		templates/fragments/header.html
    		
    		templates/users/form.html
    		templates/users/list.html
    		templates/users/view.html
    	4.后台编码：
    		实体：User
    			@Entity
                public class User {
                    @Id//主键
                    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增策略
                    private Long id;
                    private  String name;
                    private String email;
    
                    /**
                     * 声明为protected 防止直接使用
                     * @param
                     * @return
                     */
                    protected User() {
                    }
    
                    public User(Long id, String name, String email) {
                        this.id = id;
                        this.name = name;
                        this.email = email;
                    }
    			}
    		资源库：UserRepository
    			public interface UserRepository extends CrudRepository<User,Long> {}
    		控制器：UserController
    			@Controller
                @RequestMapping("/users")
                public class UserController {
    
                    @Autowired
                    private UserRepository userRepository;
    
                    /**
                     * 查询所有用户
                     * @param model
                     * @return org.springframework.web.servlet.ModelAndView
                     */
                    @GetMapping
                    public ModelAndView list(Model model){
                        model.addAttribute("userList", userRepository.findAll());
                        model.addAttribute("title", "用户管理");
                        return new ModelAndView("users/list","userModel",model);
                    }
                    /**
                     * 根据id查询用户
                     * @param id
                     * @param model
                     * @return org.springframework.web.servlet.ModelAndView
                     */
                    @GetMapping("{id}")
                    public ModelAndView view(@PathVariable("id") Long id, Model model){
                        Optional<User> userOptional = userRepository.findById(id);
                        model.addAttribute("user", userOptional.orElseGet(null));
                        model.addAttribute("title", "查看用户");
                        return new ModelAndView("users/view","userModel",model);
                    }
                    /**
                     * 获取创建表单页面
                     * @param model
                     * @return org.springframework.web.servlet.ModelAndView
                     */
                    @GetMapping("/form")
                    public ModelAndView createForm(Model model){
                        model.addAttribute("user", new User(null,null,null));
                        model.addAttribute("title", "创建用户");
                        return new ModelAndView("users/form","userModel",model);
                    }
                    /**
                     * 保存或修改用户
                     * @param user
                     * @return org.springframework.web.servlet.ModelAndView
                     */
                    @PostMapping
                    public ModelAndView saveOrUpdateUser(User user){
                        userRepository.save(user);
                        return new ModelAndView("redirect:/users");
                    }
                    /**
                     * 删除用户
                     * @param id
                     * @return
                     */
                    @GetMapping(value = "delete/{id}")
                    public ModelAndView delete(@PathVariable("id") Long id, Model model) {
                        userRepository.deleteById(id);
                        model.addAttribute("userList", userRepository.findAll());
                        model.addAttribute("title", "删除用户");
                        return new ModelAndView("users/list", "userModel", model);
                    }
    
                    /**
                     * 获取要修改的用户信息 跳转到form页面
                     * @param id
                     * @param model
                     * @return org.springframework.web.servlet.ModelAndView
                     */
                    @GetMapping(value = "modify/{id}")
                    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
                        Optional<User> userOptional = userRepository.findById(id);
    
                        model.addAttribute("user", userOptional.orElseGet(null));
                        model.addAttribute("title", "修改用户");
                        return new ModelAndView("users/form", "userModel", model);
                    }
                }


