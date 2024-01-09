package com.example.demo.app.inquiry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

    @GetMapping("/form")
    public String form(InquiryForm inquiryForm, Model model) {
        model.addAttribute("title", "Inquiry Form");
        return "inquiry/form";
    }

    // confirm画面でgo backした時に表示させるページのルーティング
    @PostMapping("/form")
    public String formGoBack(InquiryForm inquiryForm, Model model) {
        model.addAttribute("title", "Inquiry Form");
        return "inquiry/form";
    }

    @PostMapping("/confirm")
    // @Validated をつけることでアノテーションで@NotNull, @Email等した所に検査がかかるようになる
    // BindingResult には Validationnの結果が格納
    public String confirm(@Validated InquiryForm inquiryForm,
                          BindingResult result,
                          Model model) {
        if(result.hasErrors()) {
           model.addAttribute("title", "Inquiry Form");
           return "inquiry/form";
        }
        model.addAttribute("title", "Confirm Page");
        return "inquiry/confirm";
    }
}
