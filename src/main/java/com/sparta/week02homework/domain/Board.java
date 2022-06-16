package com.sparta.week02homework.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.week02homework.dto.BoardRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Board extends Timestamped{

    @Id
    @Column(name = "BOARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // UserId
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users user;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private int viewCount = 0;

    @Column(nullable = false)
    private int template;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column
    private String img_url;


    // Favorite 연관관계
    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Favorite> favorites = new ArrayList<>();


    public Board(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.body = boardRequestDto.getBody();
        this.viewCount = boardRequestDto.getViewCount();
        this.template = boardRequestDto.getTemplate();
    }

    // @RequestBody BoardDto로 업데이트
    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.body = boardRequestDto.getBody();
        this.viewCount = boardRequestDto.getViewCount();
        this.template = boardRequestDto.getTemplate();
        this.img_url = boardRequestDto.getImg_url();
    }

    // FavoriteService에서 likeCount로 업데이트
    public void updateLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    // ViewCount ++
    public void updateViewCount() {
        this.viewCount = this.viewCount + 1;
    }

    /**
     * User 연관관계 Method
     * @param user
     */
    public void setUser(Users user) {
        this.user = user;
        user.getBoardList().add(this);
    }
}
