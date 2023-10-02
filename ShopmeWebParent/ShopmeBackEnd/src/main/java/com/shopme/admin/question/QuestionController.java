package com.shopme.admin.question;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.paging.PagingAndSortingParam;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.common.entity.Question;
import com.shopme.common.exception.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class QuestionController {
    private String defaultRedirectURL = "redirect:/questions/page/1?sortField=askTime&sortDir=desc";

    @Autowired
    private QuestionService service;

    @GetMapping("/questions")
    public String listFirstPage(Model model) {
        return defaultRedirectURL;
    }

    @GetMapping("/questions/page/{pageNum}")
    public String listByPage(@PagingAndSortingParam(listName = "listQuestions", moduleURL = "/questions") PagingAndSortingHelper helper,
                             @PathVariable(name = "pageNum") int pageNum) {
        service.listByPage(pageNum, helper);

        return "question/questions";
    }

    @GetMapping("/questions/detail/{id}")
    public String viewQuestion(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Question question = service.get(id);
            model.addAttribute("question", question);

            return "question/question_detail_modal";
        } catch (QuestionNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return defaultRedirectURL;
        }
    }

    @GetMapping("/questions/edit/{id}")
    public String editQuestion(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Question question = service.get(id);
            model.addAttribute("question", question);
            model.addAttribute("pageTitle", String.format("Edit Question (ID: %d)", id));

            return "question/question_form";
        } catch (QuestionNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return defaultRedirectURL;
        }
    }

    @PostMapping("/questions/save")
    public String saveQuestion(Question question, RedirectAttributes ra,
                               @AuthenticationPrincipal ShopmeUserDetails userDetails) throws QuestionNotFoundException {
        try {
            service.save(question, userDetails.getUser());
            ra.addFlashAttribute("message", "The question ID " + question.getId() + " has been updated successfully.");
        } catch (QuestionNotFoundException e) {
            ra.addFlashAttribute("message", "Could not find any questions with ID " + question.getId());
        }
        return defaultRedirectURL;
    }

    @GetMapping("/questions/{id}/approve")
    public String approveQuestion(@PathVariable("id") Integer id, RedirectAttributes ra) {
        service.approve(id);
        ra.addFlashAttribute("message", "The question ID " + id + " has been approved.");
        return defaultRedirectURL;
    }

    @GetMapping("/questions/{id}/disapprove")
    public String disapproveQuestion(@PathVariable("id") Integer id, RedirectAttributes ra) {
        service.disapprove(id);
        ra.addFlashAttribute("message", "The question ID " + id + " has been disapproved.");
        return defaultRedirectURL;
    }

    @GetMapping("/questions/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", String.format("The question ID %d has been deleted successfully.", id));
        } catch (QuestionNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return defaultRedirectURL;
    }
}
