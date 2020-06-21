package com.jw22.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrev;
    private boolean showFirst;
    private boolean showNext;
    private boolean showEnd;
    private Integer currPage;
    private Integer totalPages;
    private List<Integer> pages;
}
