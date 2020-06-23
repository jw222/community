package com.jw22.community.controller;

import com.jw22.community.dto.QuestionDTO;
import com.jw22.community.model.Question;
import com.jw22.community.model.User;
import com.jw22.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/publish/{questionId}")
    public String edit(@PathVariable(name = "questionId") Long questionId,
                       Model model) {
        QuestionDTO questionModel = questionService.getById(questionId);
        model.addAttribute("title", questionModel.getTitle());
        model.addAttribute("description", questionModel.getDescription());
        model.addAttribute("tag", questionModel.getTag());
        model.addAttribute("id", questionModel.getId());

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam(value="fileUpload", required=false) String fileUpload,
                            @RequestParam(value="anonymous", required=false) String anonymous,
                            @RequestParam(value="id", required=false) Long id,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "User not logged in");
            return "publish";
        }

        if (title.equals("")) {
            model.addAttribute("error", "Title must not be empty");
            return "publish";
        }
        if (description.equals("")) {
            model.addAttribute("error", "Description must not be empty");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreateTime(System.currentTimeMillis());
        question.setModifyTime(question.getCreateTime());
        question.setCreatorId(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
