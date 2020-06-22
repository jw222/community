package com.jw22.community.controller;

import com.jw22.community.mapper.QuestionMapper;
import com.jw22.community.model.QuestionModel;
import com.jw22.community.model.UserModel;
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
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/publish/{questionId}")
    public String edit(@PathVariable(name = "questionId") Integer questionId,
                       Model model) {
        QuestionModel questionModel = questionMapper.getById(questionId);
        model.addAttribute("title", questionModel.getTitle());
        model.addAttribute("description", questionModel.getDescription());
        model.addAttribute("tag", questionModel.getTag());

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam(value="fileUpload", required=false) String fileUpload,
                            @RequestParam(value="anonymous", required=false) String anonymous,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        UserModel user = (UserModel) request.getSession().getAttribute("user");
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

        QuestionModel questionModel = new QuestionModel();
        questionModel.setTitle(title);
        questionModel.setDescription(description);
        questionModel.setTag(tag);
        questionModel.setCreateTime(System.currentTimeMillis());
        questionModel.setModifyTime(questionModel.getCreateTime());
        questionModel.setCreatorId(user.getId());
        questionMapper.insert(questionModel);
        return "redirect:/";
    }
}
