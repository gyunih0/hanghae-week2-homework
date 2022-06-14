package com.sparta.week02homework.dto;

import com.sparta.week02homework.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private String title;
    private String body;

    private int template;
    private int viewCount;




    /**
     * 게시글 조회할 때
     * 조회수 증가를 위한 Dto Constructor
     */
    public BoardDto(Board board) {
        this.title = board.getTitle();
        this.body = board.getBody();
        this.viewCount = board.getViewCount() + 1;
    }

}