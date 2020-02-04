package com.atguigu.handlers;

import com.atguigu.dao.DepartmentDao;
import com.atguigu.dao.EmployeeDao;
import com.atguigu.po.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 1.springmvc 处理静态资源
 * 若将DispatcherServlet 请求映射为/,则springmvc将捕捉WEB容器的所有请求,包括静态资源的请求,
 * springmvc会将他们当成一个普通的请求处理,因找不到对应的处理器将导致错误
 * 2.解决:在springmvc的配置文件中配置<mvc:default-servlet-handler/>
 */
@Controller
public class  EmployeeHandler {
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentDao departmentDao;

    @ModelAttribute
    public void getEmployee(@RequestParam(value = "id",required = false)Integer id,Map<String,Object> map){
        if (id!=null){
            map.put("employee",employeeDao.get(id));
        }
    }
    //修改操作
    @RequestMapping(value = "/emp",method = RequestMethod.PUT)
    public String update(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    public String input(@PathVariable(value = "id")Integer id,Map<String,Object> map){
        map.put("employee",employeeDao.get(id));
        map.put("departments",departmentDao.getDepartments());
        return "input";
    }

    @RequestMapping(value = "/emp/{id}",method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id",required = false) Integer id){
        System.out.println("xxxxx");
        employeeDao.delete(id);
        return "redirect:/emps";
    }

    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    public String save(Employee employee, BindingResult result, Map<String,Object> map){
        System.out.println("save:"+employee);
        if (result.getErrorCount()>0){
            System.out.println("出错了!");
            for (FieldError error:result.getFieldErrors()){
                System.out.println(error.getField()+":"+error.getDefaultMessage());
            }
            //若验证出错,则转向定制的页面
            map.put("departments",departmentDao.getDepartments());
            return "input";
        }
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    @RequestMapping(value = "/emp",method = RequestMethod.GET)
    public String input(Map<String,Object> map){
        System.out.println("cccccccc");
        map.put("departments",departmentDao.getDepartments());
        map.put("employee",new Employee());
        return "input";
    }

    @RequestMapping(value = "/emps")
    public String list(Map<String,Object> map){
        map.put("employees",employeeDao.getAll());
        return "list";
    }

    /**
     * 数据绑定的流程:
     *  1.springmvc主框架将ServletRequest对象及目标方法的入参实例传递给WebDataBinderFactory实例,以创建DataBinder实例对象
     *  2.DataBinder调用装配在springmvc上下文中的ConversionService组件进行数据类型转换,数据格式化工作.将Servlet中的请求信息
     *  填充到入参对象中
     *  3.调用Validator组件对已经绑定了请求消息的入参对象进行数据合法性校验,并最终生成数据绑定结果BindingData对象
     *  4.springmvc抽取BindingResult中的入参对象和校验错误对象,将他们赋值给处理方法的响应入参
     */

    /**
     * 由@InitBinder表示的方法,可以对WebDataBinder对象进行初始化.WebDataBinder是DataBinder的子类,用于完成由表单字段到Javabean属性的绑定
     * @InitBinder 方法不能有返回值,它必须声明为void.
     * @InitBinder 方法的参数通常是WebDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setDisallowedFields("lastName");
    }
    /**
     * 数据格式化:
     *  1.对属性对象的输入输出进行格式化,其本质依然属于"类型转换"的范畴
     *  2.spring在格式化模块中定义了一个实现ConversionService接口的FormattingConversionService实现类,该实现类扩展了GenericConversionService
     *  因此他具有类型转换和格式化的功能
     *  3.FormattingConversionService拥有一个 FormattingConversionServiceFactoryBean 工厂类,后者用于在spring上下文中构造前者
     *      FormattingConversionServiceFactoryBean内部已经注册了:
     *       NumberFormatAnnotationFormatterFactory:支持对数字类型使用@NumberFormat注解
     *       JodaDateTimeFormatAnnotationFormatterFactory:支持日期类型的属性使用@DateTimeFormat注解
     *  4.装配了FormattingConversionServiceFactoryBean后,就可以在springmvc入参绑定及模型数据输出时使用注解驱动了.
     *  5.<mvc:annotation-driven/>默认创建的ConversionService实例即为FormattingConversionServiceFactoryBean.
     */
}