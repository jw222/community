package com.jw22.community.service;

import com.jw22.community.dto.PaginationDTO;
import com.jw22.community.dto.QuestionDTO;
import com.jw22.community.exception.CustomizedErrorCode;
import com.jw22.community.exception.CustomizedException;
import com.jw22.community.mapper.QuestionMapper;
import com.jw22.community.mapper.QuestionMapperExt;
import com.jw22.community.mapper.UserMapper;
import com.jw22.community.model.Question;
import com.jw22.community.model.QuestionExample;
import com.jw22.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    private QuestionMapperExt questionMapperExt;

    @Autowired
    private UserMapper userMapper;

    private PaginationDTO _setPagination(List<Question> questions,
                                        Integer page,
                                        Integer totalPages) {
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question: questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreatorId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOs);
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

    public PaginationDTO list(Integer page, Integer size) {
        int offset = size * (page - 1);
        int totalQuestions = (int) questionMapper.countByExample(new QuestionExample());
        int totalPages = (int) Math.ceil((double) totalQuestions / (double) size);

        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        return _setPagination(questions, page, totalPages);
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        int offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorIdEqualTo(userId);
        int totalQuestions = (int) questionMapper.countByExample(questionExample);
        int totalPages = (int) Math.ceil((double) totalQuestions / (double) size);

        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
        return _setPagination(questions, page, totalPages);
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        return questionDTO;
    }

    public void incrementView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        int success = questionMapperExt.incrementView(question);
        if (success != 1) {
            throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
        }
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            questionMapper.insertSelective(question);
        } else {
            question.setModifyTime(System.currentTimeMillis());
            int success = questionMapper.updateByPrimaryKeySelective(question);
            if (success != 1) {
                throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }
}
