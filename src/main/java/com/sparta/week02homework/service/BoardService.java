package com.sparta.week02homework.service;


import com.sparta.week02homework.domain.Board;
import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.dto.BoardDto;
import com.sparta.week02homework.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserService userService;



    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
        );
    }



    /**
     * Board CRUD 관련 Methods
     */

    // Board 전체 GET
    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    // Board 한개 GET
    @Transactional
    public BoardDto findOneBoard(Long boardId) {
        Board board = findBoardById(boardId);

        BoardDto boardDto = new BoardDto(board); // viewCount ++
        board.update(boardDto);

        return boardDto;
    }

    // POST new Board
    public String createBoard(Users userDetails, BoardDto boardDto) {
        Users user = userService.findUserByAuthUser(userDetails);
        Board board = new Board(boardDto);

        board.setUser(user);
        boardRepository.save(board);

        return "생성 완료";
    }

    // PUT Board update
    @Transactional
    public String update(Long boardId, BoardDto boardDto) {
        Board board = findBoardById(boardId);

        boardDto.setViewCount(board.getViewCount());
        board.update(boardDto);

        return "수정 완료";
    }

    // DELETE Board
    public String deleteBoardById(Long boardId) {
        findBoardById(boardId); //있나 없나 확인
        boardRepository.deleteById(boardId);

        return "삭제 완료";
    }


    /**
     * like 관련 method
     */

    @Transactional
    public int updateLikeCount(Board board, int likeCount) {
        board.update(likeCount);
        return likeCount;
    }


}
