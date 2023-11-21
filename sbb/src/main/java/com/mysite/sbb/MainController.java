package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/sbb")
    @ResponseBody
    public String index() {
        return "안녕하세요!";
    }

    @GetMapping("/")
    public String root() {
        //forward사용하면 http://localhost:8080/ 로 접속했을 때 주소가 그대로
        //return "forward:/question/list";
        //redirect사용하면 http://localhost:8080/ 로 접속했을 때 주소가 http://localhost:8080/question/list 로 이동
        return "redirect:/question/list";
    }
}
