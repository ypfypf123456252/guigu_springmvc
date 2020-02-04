package com.atguigu.handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SpringmvcTestExceptionHandler {
    @ExceptionHandler({ArithmeticException.class})
    public ModelAndView handleArithmeticException(Exception ex){
        System.out.println("--->出异常了:"+ex);
        ModelAndView mv=new ModelAndView("error");
        mv.addObject("exception",ex);
        return mv;
    }
}
