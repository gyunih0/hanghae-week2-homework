package com.sparta.week02homework.dto;


import com.sparta.week02homework.domain.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponseDto {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long boardId;
    private Long userId;
    private String title;
    private String body;
    private int viewCount;
    private int template;
    private int likeCount;
    private String img_url;


    public BoardResponseDto(Board board) {
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.boardId = board.getId();
        this.userId = board.getUser().getId();
        this.title = board.getTitle();
        this.body = board.getBody();
        this.viewCount = board.getViewCount();
        this.template = board.getTemplate();
        this.likeCount = board.getLikeCount();
        this.img_url = board.getImg_url();
    }
}
