package com.atguigu.handlers;

import com.atguigu.dao.EmployeeDao;
import com.atguigu.po.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

@Controller
public class SpringMvcTest {
    @Autowired
    private EmployeeDao employeeDao;

    /**
     * 自定义类型转换器:
     *      ConversionService 是spring类型转换器体系的核心接口.
     *      可以利用ConversionServiceFactoryBean在spring的IOC容器中定义一个ConversionService.Spring将自动识别出IOC容器中的
     *      ConversionService,并在Bean属性配置及springmvc处理方法入参绑定等场合使用它进行数据的转换
     *      可通过ConversionServiceFactoryBean的converters属性注册自定义的类型转换器
     */
    @RequestMapping(value = "/testConversionServiceConverer")
    public String testConversionServiceConverer(Employee employee){
        System.out.println(employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    /**
     * HttpMessageConverter<T>,负责将请求信息转换为一个对象(类型为T),将对象(类型为T)输出位响应信息
     * 工作原理:请求报文->HttpInputMessage->HttpMessageConverter->java对象->springmvc-Java对象->HttpMessageConverter
     * ->HttpOutputMessage->响应报文
     * DispatcherServlet默认装配 RequestMappingHandlerAdapter,而RequestMappingHandlerAdapter默认装配了如下的HttpMessageConverter
     */
    @ResponseBody
    @RequestMapping(value = "/testJson")
    public Collection<Employee> testJson(){
        return employeeDao.getAll();
    }

    @ResponseBody
    @RequestMapping(value = "/testHttpMessageConverter")
    public String testHttpMessageConverter(@RequestBody String body){
        System.out.println(body);
        return "hello world! "+new Date();
    }

    @RequestMapping("/testResponseEntity")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        ServletContext servletContext = session.getServletContext();
        InputStream in = servletContext.getResourceAsStream("/index.jsp");
        byte[] body=new byte[in.available()];
        in.read(body);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition","attachment;filename=abc.txt");
        HttpStatus status=HttpStatus.OK;
        ResponseEntity<byte[]> responseEntity=new ResponseEntity<>(body,httpHeaders,status);
        return responseEntity;
    }

    @Autowired
    private ResourceBundleMessageSource messageSource;
    @RequestMapping("/i18n")
    public String testI18n(Locale locale){
        String val=messageSource.getMessage("i18n.user",null,locale);
        System.out.println(val);
        return "i18n";
    }

    @RequestMapping(value = "/testFileUpload")
    public String testFileUpload(@RequestParam("desc")String desc, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("desc:"+desc);
        System.out.println("getOriginalFilename"+file.getOriginalFilename());
        System.out.println("InputStream:"+file.getInputStream());
        return "success";
    }

    /**
     * springmvc通过 HandlerExceptionResolver 处理程序的异常,包括Handler映射,数据绑定及目标方法执行时发生的异常:
     * 1.在@ExceptionHandler方法的入参中可以加入Exception类型的参数,该参数即为对应发生异常的对象
     * 2.@ExceptionHandler的方法入参中不能传入map.若希望把异常信息传到页面上,需要使用ModelAndView作为返回值
     * 3.@ExceptionHandler方法标记的异常有优先级的问题.
     * 4.@ControllerAdvice:如果在当前Handler中找不到 @ExceptionHandler 方法来处理当前方法出现的异常,则将去
     * @ControllerAdvice 标记的类中查找@ExceptionHandler标记的方法来处理异常
     **/
    @ExceptionHandler({ArithmeticException.class})
    public ModelAndView handleArithmeticException(Exception ex){
        System.out.println("出异常了:"+ex);
        ModelAndView mv=new ModelAndView("error");
        mv.addObject("exception",ex);
        return mv;
    }
    @ExceptionHandler({RuntimeException.class})
    public ModelAndView handleArithmeticException2(Exception ex){
        System.out.println("[出异常了]:"+ex);
        ModelAndView mv=new ModelAndView("error");
        mv.addObject("exception",ex);
        return mv;
    }
    @RequestMapping(value = "/testExceptionHandlerExceptionResolver")
    public String testExceptionHandlerExceptionResolver(@RequestParam("i")int i){
        System.out.println("result:"+(10/i));
        return "success";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "测试")
    @RequestMapping(value = "/testResponseStatusExceptionResolver")
    public String testResponseStatusExceptionResolver(@RequestParam("i")int i){
        if (i==13){
            throw new UsernameNotMatchPasswordException();
        }
        System.out.println("testResponseStatusExceptionResolver...");
        return "success";
    }

    @RequestMapping(value = "/testSimpleMappingExceptionResolver")
    public String testSimpleMappingExceptionResolver(@RequestParam("i")int i){
        String[] vals=new String[10];
        System.out.println(vals[i]);
        return "success";
    }
}
