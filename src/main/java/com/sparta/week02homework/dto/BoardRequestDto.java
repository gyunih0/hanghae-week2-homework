package com.sparta.week02homework.dto;

import com.sparta.week02homework.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {

    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;
    @NotEmpty(message = "내용을 입력해주세요.")
    private String body;


    private int template;
    private int viewCount;

    private String img_url;



    /**
     * 게시글 조회할 때
     * 조회수 증가를 위한 Dto Constructor
     */
    public BoardRequestDto(Board board) {
        this.title = board.getTitle();
        this.body = board.getBody();
        this.viewCount = board.getViewCount() + 1;
    }

}