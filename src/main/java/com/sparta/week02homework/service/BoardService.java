package com.sparta.week02homework.service;


import com.sparta.week02homework.domain.Board;
import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.dto.BoardRequestDto;
import com.sparta.week02homework.dto.BoardResponseDto;
import com.sparta.week02homework.repository.BoardRepository;
import com.sparta.week02homework.s3uploader.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserService userService;
    private final AwsS3Service s3Service;

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
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
        for (Board board : boards) {
            boardResponseDto.add(new BoardResponseDto(board));
        }
        return boardResponseDto;
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
    public String createBoard(Users userDetails, BoardRequestDto boardRequestDto, MultipartFile file) {
        Users user = userService.findUserByAuthUser(userDetails);

        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .body(boardRequestDto.getBody())
                .template(boardRequestDto.getTemplate())
                .img_url(s3Service.uploadFile(file))
                .build();

        board.setUser(user);
        boardRepository.save(board);

        return "생성 완료";
    }

    // PUT Board update
    @Transactional
    public String update(Long boardId, BoardRequestDto boardRequestDto, MultipartFile file) {
        Board board = findBoardById(boardId);
        // s3에서 기존 이미지 삭제
        s3Service.deleteFile(board.getImg_url());

        // 기존 viewCount 이전
        boardRequestDto.setViewCount(board.getViewCount());

        // 새로운 file upload
        boardRequestDto.setImg_url(s3Service.uploadFile(file));

        board.update(boardRequestDto);

        return "수정 완료";
    }

    // DELETE Board
    public String deleteBoardById(Long boardId) {
        Board board = findBoardById(boardId);
        // s3에서 이미지 삭제
        s3Service.deleteFile(board.getImg_url());

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
