package com.itheima.control;

import com.itheima.domain.Result;
import com.itheima.service.IPorductQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ProductController {
    @Autowired
    IPorductQuery iPorductQuery;
    @RequestMapping("/productQuery")
    public void productController(){
        System.out.println("Enter productQuery...");
    }

    @RequestMapping("/list")
    public ModelAndView listQuery( String queryString,
                                    String catalog_name,
                                    String price,
                                  @RequestParam(defaultValue = "1")String page,
                                  @RequestParam(defaultValue = "1") String sort){
        Result result = iPorductQuery.listQuery(queryString, catalog_name, price, page, sort);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("itemlist");
        modelAndView.addObject("result",result);
        modelAndView.addObject("queryString",queryString);
        return modelAndView;
    }
}
