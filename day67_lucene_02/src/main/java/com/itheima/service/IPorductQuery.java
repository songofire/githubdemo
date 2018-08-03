package com.itheima.service;

import com.itheima.domain.Result;
import org.springframework.web.bind.annotation.RequestParam;

public interface IPorductQuery {
    public Result listQuery(String queryString, String catalog_name, String price, String page, String sort);

}
