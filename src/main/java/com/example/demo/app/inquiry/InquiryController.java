package com.example.demo.app.inquiry;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryNotFoundException;
import com.example.demo.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    @Autowired
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @GetMapping
    public String index(Model model) {
        List<Inquiry> list = inquiryService.getAll();

        // 例外処理用
        // 本来はupdate用のformを作ってupdateをするが今回は簡易的にindexの中で実行
        // id(4)は無いとする
//        Inquiry inquiry = new Inquiry();
//        inquiry.setId(4);
//        inquiry.setName("Jamie");
//        inquiry.setEmail("sample4@example.com");
//        inquiry.setContents("Hello.");

        // 例外処理1
//        try {
//            inquiryService.update(inquiry);
//        } catch (InquiryNotFoundException e) {
//            model.addAttribute("message", e);
//            return "error/CustomPage";
//        }

        // 例外処理2 try-catchで囲わない
//        inquiryService.update(inquiry);

        model.addAttribute("inquiryList", list);
        model.addAttribute("title", "Inquiry Index");

        return "inquiry/index";
    }

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

        // dbとの処理
        // まずはInquiryFormというクラスからInquiryというEntityクラスに値を詰め替える
        Inquiry inquiry = new Inquiry();
        inquiry.setName(inquiryForm.getName());
        inquiry.setEmail(inquiryForm.getEmail());
        inquiry.setContents(inquiryForm.getContents());
        inquiry.setCreated(LocalDateTime.now());

        inquiryService.save(inquiry);
        // 一度下記で設定したRegisteredが画面に表示されたらセッションの情報は削除されるため、フラッシュスコープと呼ぶ
        redirectAttributes.addFlashAttribute("complete", "Registered");
        // ここのreturnはhtmlではなく、urlを指している
        return "redirect:/inquiry/form";
    }

    // コントローラー内のメソッドで例外を捕捉
//    @ExceptionHandler(InquiryNotFoundException.class)
//    public String handleException(InquiryNotFoundException e, Model model) {
//        model.addAttribute("message", e);
//        return "error/CustomPage";
//    }
}
