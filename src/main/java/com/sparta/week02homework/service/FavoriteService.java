package com.sparta.week02homework.service;

import com.sparta.week02homework.domain.Board;
import com.sparta.week02homework.domain.Favorite;
import com.sparta.week02homework.domain.FavoriteStatus;
import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.repository.BoardRepository;
import com.sparta.week02homework.repository.FavoriteRepository;
import com.sparta.week02homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final BoardService boardService;


    public int like(Users userDetails, Long boardId) {
        Users user = userService.findUserByAuthUser(userDetails);
        Board board = boardService.findBoardById(boardId);

        Favorite favorite = new Favorite(FavoriteStatus.Like);
        favorite.setUserAndBoard(user, board);
        favoriteRepository.save(favorite);

        return boardService.updateLikeCount(board, favoriteRepository.countByBoard(board));
    }

    public int dislike(Users userDetails, Long boardId) {
        Users user = userService.findUserByAuthUser(userDetails);
        Board board = boardService.findBoardById(boardId);

        favoriteRepository.deleteByUserAndBoard(user, board);

        return boardService.updateLikeCount(board, favoriteRepository.countByBoard(board));
    }
}
