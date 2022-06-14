package com.sparta.week02homework.service;


import com.sparta.week02homework.domain.Board;
import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.dto.BoardRequestDto;
import com.sparta.week02homework.dto.BoardResponseDto;
import com.sparta.week02homework.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
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
    public List<BoardResponseDto> findBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtos = new ArrayList<>();
        for (Board board : boards) {
            boardResponseDtos.add(new BoardResponseDto(board));
        }
        return boardResponseDtos;
    }

    // Board 한개 GET
    @Transactional
    public BoardResponseDto findOneBoard(Long boardId) {
        Board board = findBoardById(boardId);

        BoardRequestDto boardRequestDto = new BoardRequestDto(board); // viewCount ++
        board.update(boardRequestDto);

        return new BoardResponseDto(board);
    }

    // POST new Board
    public String createBoard(@AuthenticationPrincipal Users userDetails, @RequestBody BoardRequestDto boardRequestDto) {
        Users user = userService.findUserByAuthUser(userDetails);
        Board board = new Board(boardRequestDto);

        board.setUser(user);
        boardRepository.save(board);

        return "생성 완료";
    }

    // PUT Board update
    @Transactional
    public String update(Long boardId, BoardRequestDto boardRequestDto) {
        Board board = findBoardById(boardId);

        boardRequestDto.setViewCount(board.getViewCount());
        board.update(boardRequestDto);

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
