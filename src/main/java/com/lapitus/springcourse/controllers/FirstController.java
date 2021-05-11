package com.lapitus.springcourse.controllers;

import com.lapitus.springcourse.CalculatorActions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {

//    @GetMapping("/hello")
//    public String helloPage(HttpServletRequest request) {
//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");
//
//        System.out.println("Hello " + name + " " + surname);
//        return "first/hello";
//    }

//    @GetMapping("/bye")
//    public String goodByePage(@RequestParam(value = "name", required = false) String name,
//                              @RequestParam(value = "surname", required = false) String surname) {
//
//        System.out.println("Bye " + name + " " + surname);
//
//        return "first/bye";
//    }

    @GetMapping("/hello")
    public String helloPage(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        //System.out.println("Hello " + name + " " + surname);
        model.addAttribute("message", "Hello " + name + " " + surname);
        return "first/hello";
    }

    @GetMapping("/bye")
    public String goodByePage(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "surname", required = false) String surname) {

        System.out.println("Bye " + name + " " + surname);

        return "first/bye";
    }

    @GetMapping("/calculator")
    public String calculate(@RequestParam(value = "firstNumber", required = false) int firstNumber,
                            @RequestParam(value = "secondNumber", required = false) int secondNumber,
                            @RequestParam(value = "action") CalculatorActions action, Model model) {

        double result;

        switch (action.toString()) {

            case "MULTIPLICATION":
                result = firstNumber * secondNumber;
                break;

            case "ADDITIONAL":
                result = firstNumber + secondNumber;
                break;

            case "SUBTRACTION":
                result = firstNumber - secondNumber;
                break;

            case "DIVISION":
                result = firstNumber / (double)secondNumber;
                break;

            default:
                result = 0;
        }

        System.out.println("firstNumber:" + firstNumber + " " +
                           "secondNumber:" + secondNumber +
                           "action:" + action   );

        model.addAttribute("firstNumber", firstNumber);
        model.addAttribute("secondNumber", secondNumber);
        model.addAttribute("action", action);
        model.addAttribute("result", result);

        model.addAttribute("calculateMessage", String.format("You first number is %s. You second number is %s" +
                                                      "You action is %s and result is %s", firstNumber,secondNumber,action,result));

        return "first/calculator";
    }
}
