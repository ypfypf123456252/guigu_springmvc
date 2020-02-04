package com.atguigu.controller;

import com.atguigu.po.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
@SessionAttributes(value = {"user"},types = {String.class})
@RequestMapping("/springmvc")
@Controller
public class Main {
    private static final String success="success";

    /**
     * 使用method属性指定请求方式
     */
    @RequestMapping(value = "/testMethod",method = RequestMethod.POST)
    public String testMethod(){
        System.out.println("testMethod");
        return success;
    }

    /**
     * 了解:可以使用params和headers来更加精确的映射请求.params和headers支持简单的表达式.
     */
    @RequestMapping(value = "/testParamsAndHeaders",params = {"username","age!=10"})
    public String testParamsAndHeaders(){
        System.out.println("testParamsAndHeaders");
        return success;
    }

    /**
     *  ? : 匹配文件中的一个字符
     *  * : 匹配文件中的任意个字符
     *  ** : 匹配多层路径
     */
    @RequestMapping("/testAndPath/*/abc")
    public String testAndPath(){
        System.out.println("testAndPath");
        return success;
    }

    /**
     * @PathVariable 可以来映射URL中的占位符到目标方法的参数中
     */
    @RequestMapping("/testPathVariable/{id}")
    public String testPathVariable(@PathVariable("id")Integer id){
        System.out.println("testPathVariable"+id);
        return success;
    }


    /**
     * 如何发送PUT或DELETE请求呢?
     *  1.需要配置HiddenHttpMethodFilter
     *  2.需要发送post请求
     *  3.需要在发送post请求时携带一个name="_method"的隐藏域,值为DELETE或PUT
     * 在使用springmvc的目标方法中如何得到id呢?
     *  使用@PathVariable注解
     */
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.GET)
    public String testRestGet(@PathVariable("id")Integer id){
        System.out.println("testRest get"+"id");
        return success;
    }
    @RequestMapping(value = "/testRest",method = RequestMethod.POST)
    public String testRestPost(){
        System.out.println("testRest post");
        return success;
    }
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.DELETE)
    public String testRestDelete(@PathVariable Integer id){
        System.out.println("testRest delete"+id);
        return success;
    }
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.PUT)
    public String testRestPut(@PathVariable Integer id){
        System.out.println("testRest put"+id);
        return success;
    }


    /**
     * @RequestParam:来映射请求参数
     * value:值即为请求参数的值
     * required:该参数是否必须,默认true
     * defaultValue:请求参数的默认值
     */
    @RequestMapping(value = "/testRequestParam")
    public String testRequestParam(@RequestParam(value = "username")String un,
                                   @RequestParam(value = "age",required = false,defaultValue = "0")Integer age){
        System.out.println("testRequestParam,username="+un+",age="+age);
        return success;
    }
    /**
     * 了解即可
     * 映射请求头
     * @RequestHeader:用法同@RequestParam
     */
    @RequestMapping(value = "/testRequestHeader")
    public String testRequestHeader(@RequestHeader(value = "Accept-Language")String al){
        System.out.println("testRequestHeader,Accept-Language:"+al);
        return success;
    }
    /**
     * 了解即可
     * @CookieValue:映射一个cookie值,属性同@RequestParam
     */
    @RequestMapping(value = "/testCookieValue")
    public String testCookieValue(@CookieValue(value = "JSESSIONID")String sessionId){
        System.out.println("testCookieValue,sessionId="+sessionId);
        return success;
    }
    /**
     * spring mvc 会按请求参数名和pojo属性名进行自动匹配
     * 自动为该对象填充属性值.支持级联属性.
     * 如:dept.deptId,dept.address.tel等
     */
    @RequestMapping(value = "/testPojo")
    public String testPojo(User user){
        System.out.println("testPojo,user:"+user);
        return success;
    }

    /**
     * 可以使用Servlet的原生API作为目标方法的参数 具体支持以下类型
     *
     * HttpServletRequest
     * HttpServletResponse
     * HttpSession
     * java.security.Principal
     * Locale InputStream
     * OutputStream
     * Reader
     * Writer
     */
    @RequestMapping(value = "/testServletApi")
    public String testServletApi(HttpServletRequest request, HttpServletResponse response){
        System.out.println(""+request+response);
        return success;
    }

    /**
     * 目标方法的返回值可以是ModelAndView类型.
     * 其中可以包涵模型和视图信息
     * springmvc会把ModelAndView的model中数据放入到request域对象中.
     */
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){
        String viewName=success;
        ModelAndView modelAndView=new ModelAndView(viewName);

        //添加模型数据到ModelAndView中
        modelAndView.addObject("time",new Date());

        return modelAndView;
    }

    /**
     * 目标方法可以添加map类型的参数(实际上也可以是Model类型或者ModelMap类型的参数)
     * @param map
     * @return
     */
    @RequestMapping(value = "/testMap")
    public String testMap(Map<String,Object> map){
        System.out.println(map.getClass().getName());
        map.put("names", Arrays.asList("tom","jerry","mike"));
        return success;
    }
    @RequestMapping(value = "/testSessionAttributes")
    public String testSessionAttributes(Map<String,Object> map){
        User user = new User("tom", "123", "tom@qq.com", 15,null);
        map.put("user",user);
        return success;
    }

    /**
     * 运行流程:
     *  1.执行@ModelAttribute注解修饰的方法:从数据库中取出对象,把对象放入到map中.键为:user
     *  2.springmvc从map中取出User对象,并把表单的请求参数赋值给该User对象的对应属性值
     *  3.springmvc把上述对象传入目标方法的参数.
     *
     * 由@ModelAttribute 标记的方法,会在每个目标方法执行之前被springmvc调用!
     * 注意:在@ModelAttribute修饰的方法中,放入到map时的键需要和目标方法入参类型的第一个字母小写的字符串一致!
     *
     * springmvc确定目标方法pojo类型的入参过程
     * 1.确定一个key:
     *  1).若目标方法的pojo类型的参数没有使用@ModelAttribute作为修饰,则key为pojo类名第一个字母的小写
     *  2).若使用了@ModelAttribute来修饰,则key为@ModelAttribute注解的value属性值
     * 2.在implicitModel中查找key对应的对象,若存在,则作为入参传入
     *  1).若在@ModelAttribute标记的方法中在Map中保存过,且key和1确定的key一致,则会获取到
     * 3.若implicitModel中不存在key对应的对象,则检查当前的handler是否使用@ModelAttribute注解修饰,若使用了该注解,
     * 且@ModelAttribute注解的value属性值包含了key,则会从HttpSession中来获取key所对应的value值,若存在则直接传入
     * 到目标方法的入参中.若不存在则抛出异常
     * 4.若Handler没有标识@ModelAttribute注解或@ModelAttribute注解的value值不包含key,则会通过反射来创建pojo类
     * 型的参数,传入为目标方法的参数
     * 5.springmvc会把key和pojo类型的对象保存到implicitModel中,进而保存到request中
     *
     * 源代码分析的流程:
     *  1.调用@ModelAttribute注解修饰的方法.实际上把@ModelAttribute方法中的Map中的数据放在了implicitModel中.
     *  2.解析请求处理器的目标参数,实际上该目标参数来自于WebDataBinder对象的target属性
     *      1).创建WebDataBinder对象:
     *       ①.确定objectName属性:若传入的attrName属性值为"",则objectName为类名第一个字母小写.
     *        注意:attrName.若目标方法的pojo属性使用了@ModelAttribute 来修饰,则attrName值即为@ModelAttribute的value属性值
     *       ②.确定target属性:
     *        >在implicitModel中查找attrName对应的属性值.若存在 OK
     *        >*若不存在:则验证当前handler是否使用了@SessionAttributes进行修饰,若使用了,则尝试从session中获取attrName所对应
     *        的属性值.若session中没有对应的的属性值,则抛出异常.
     *        >若handler没有使用SessionAttributes进行修饰,或@SessionAttributes中没有使用value值指定的key和attrName想匹配,
     *        则通过反射创建pojo对象
 *          2).springmvc把表单的请求参数赋给了WebDataBinder的target对应的属性.
     *      3).*springmvc会把WebDataBinder的attrName和target给到implicitModel.进而传到request域对象中.
     *      4).把WebDataBinder的target作为参数传递给目标方法的入参.
     */
    @ModelAttribute
    public void getUser(@RequestParam(value = "id",required = false)Integer id,Map<String,Object> map){
        if (id!=null){
            //模拟从数据库中获取对象
            User user = new User(1, "tom", "123", "tom@atguigu.com", 12);
            System.out.println("从数据库中获取一个对象:"+user);
            map.put("user",user);
        }
    }
    @RequestMapping(value = "/testModelAttribute")
    public String testModelAttribute(@ModelAttribute(value = "user") User user){
        System.out.println("修改:"+user);
        return success;
    }

    /**
     * 1.对于那些返回String,View或ModeMap等类型的处理方法,springmvc也会在内部把他们封装成一个ModelAndView对象,它
     * 包含了逻辑名和模型对象的视图
     * 2.springmvc借助视图解析器(ViewResolver)得到最终的视图对象(View),最终的视图可以是jsp,Excel等各种视图
     * 3.视图的作用是渲染模型数据,将模型里的数据以某种形式呈现给用户.
     * 4.视图对象由视图解析器负责实例化.由于视图是无状态的,所以线程安全
     */
    @RequestMapping("/testViewAndViewResolver")
    public String testViewAndViewResolver(){
        System.out.println("testViewAndViewResolver");
        return success;
    }
    @RequestMapping("/testView")
    public String testView(){
        System.out.println("testView");
        return "helloView";
    }

    @RequestMapping("/testRedirect")
    public String testRedirect(){
        System.out.println("testRedirect");
        return "redirect:/index.jsp";
    }
}
