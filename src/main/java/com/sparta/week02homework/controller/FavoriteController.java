package com.sparta.week02homework.controller;


import com.sparta.week02homework.domain.FavoriteStatus;
import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;


    /**
     * @AuthenticationPrincipal Users userDetails
     * @param boardId
     * @return likeCount
     */
    @PostMapping("api/board/{boardId}/like")
    public int like(@AuthenticationPrincipal Users userDetails, @PathVariable Long boardId){
        return favoriteService.like(userDetails, boardId);
    }

    @DeleteMapping("api/board/{boardId}/like")
    public int dislike(@AuthenticationPrincipal Users userDetails, @PathVariable Long boardId) {
        return favoriteService.dislike(userDetails, boardId);
    }


}
