package com.sparta.week02homework.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.week02homework.dto.FavoriteDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Favorite extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAVORITE_ID", nullable = false)
    private Long id;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users user;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;


    // 좋아요인지 아닌지의 상태
    @Enumerated(EnumType.STRING)
    private FavoriteStatus status = FavoriteStatus.Dislike;

    public Favorite(FavoriteStatus status) {
        this.status = status;
    }

    public void setUserAndBoard(Users user, Board board) {
        this.user = user;
        this.board = board;
        user.getFavorites().add(this);
        board.getFavorites().add(this);
    }




}
