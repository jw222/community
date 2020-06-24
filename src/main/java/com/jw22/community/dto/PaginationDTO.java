package com.jw22.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private List<CommentDTO> comments;
    private boolean showPrev;
    private boolean showFirst;
    private boolean showNext;
    private boolean showEnd;
    private Integer currPage;
    private Integer totalPages;
    private List<Integer> pages;

    public static PaginationDTO setProperties(PaginationDTO paginationDTO,
                                       Integer page,
                                       Integer totalPages) {
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
