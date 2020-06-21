package com.jw22.community.service;

import com.jw22.community.dto.PaginationDTO;
import com.jw22.community.dto.QuestionDTO;
import com.jw22.community.mapper.QuestionMapper;
import com.jw22.community.mapper.UserMapper;
import com.jw22.community.model.QuestionModel;
import com.jw22.community.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        Integer offset = size * (page - 1);

        List<QuestionModel> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (QuestionModel question: questions) {
            UserModel userModel = userMapper.findById(question.getCreatorId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUserModel(userModel);
            questionDTOs.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOs);
        double totalQuestions = questionMapper.count();
        int totalPages = (int) Math.ceil(totalQuestions / (double) size);
        paginationDTO.setTotalPages(totalPages);
        paginationDTO.setShowPrev(page > 1);
        paginationDTO.setShowNext(page < totalPages);
        paginationDTO.setShowFirst(page > 3 && totalPages > 5);
        paginationDTO.setShowEnd(page < totalPages - 2 && totalPages > 5);
        paginationDTO.setCurrPage(page);
        int start, end;
        if (totalPages <= 5) {
            start = 1;
            end = totalPages;
        } else {
            if (page - 2 < 1) {
                start = 1;
                end = 5;
            } else if (page + 2 > totalPages) {
                start = totalPages - 4;
                end = totalPages;
            } else {
                start = page - 2;
                end = page + 2;
            }
        }
        List<Integer> pages = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            pages.add(i);
        }
        paginationDTO.setPages(pages);

        return paginationDTO;
    }
}
