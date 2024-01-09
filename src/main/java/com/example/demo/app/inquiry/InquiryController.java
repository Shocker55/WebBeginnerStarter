package com.example.demo.app.inquiry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

    @GetMapping("/form")
    public String form(InquiryForm inquiryForm,
                       Model model,
                       // フラッシュスコープの値を受け取るために引数の追加
                       // @ModelAttribute(フラッシュスコープのキーの名前)
                       @ModelAttribute("complete") String complete) {
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
    // BindingResult には Validationの結果が格納
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

    @PostMapping("/complete")
    // RedirectAttributesはフラッシュスコープを使うために設定(ページ間を超えてデータ操作するためにセッションを利用)
    // また、DB保存後の2重クリックを防止するためにリダイレクトする(セッションで保持した値を使って完了させる)役割もある。
    // Udemy5-30, 5-31 に詳細
    public String complete(@Validated InquiryForm inquiryForm,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Inquiry Form");
            return "inquiry/form";
        }

        // dbとの処理 (今は省略)

        // 一度下記で設定したRegisteredが画面に表示されたらセッションの情報は削除されるため、フラッシュスコープと呼ぶ
        redirectAttributes.addFlashAttribute("complete", "Registered");
        // ここのreturnはhtmlではなく、urlを指している
        return "redirect:/inquiry/form";
    }
}
