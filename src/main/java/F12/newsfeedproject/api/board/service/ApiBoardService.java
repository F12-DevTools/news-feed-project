package F12.newsfeedproject.api.board.service;

import F12.newsfeedproject.api.board.dto.request.BoardRequestDto;
import F12.newsfeedproject.api.board.dto.request.BoardUpdateRequestDto;
import F12.newsfeedproject.api.board.dto.response.BoardResponseDto;
import F12.newsfeedproject.api.board.dto.response.BoardViewResponseDto;
import F12.newsfeedproject.domain.board.entity.Board;
import F12.newsfeedproject.domain.board.service.BoardService;
import F12.newsfeedproject.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApiBoardService {

  private final BoardService boardService;

  // 게시글 작성
  @Transactional
  public BoardResponseDto saveBoard(BoardRequestDto requestDto, User loginUser) {
    Board savedBoard = boardService.saveBoard(requestDto.toEntity(loginUser));
    return BoardResponseDto.from(savedBoard);
  }

  // 게시글 단건 조회
  public BoardResponseDto getBoard(Long boardId) {
    Board getBoard = boardService.findByBoardIdWithUser(boardId);
    return BoardResponseDto.from(getBoard);
  }

  // 게시글 전체 목록 조회
  public List<BoardResponseDto> getBoards() {
    List<Board> boards = boardService.getBoards();
    return boards.stream().map(BoardResponseDto::from).collect(Collectors.toList());
  }

  // 게시글 수정
  @Transactional
  public BoardResponseDto updateBoard(Long boardId, BoardUpdateRequestDto requestDto) {
    Board updateBoard = boardService.findByBoardIdWithUser(boardId);
    boardService.updateBoard(updateBoard, requestDto.toEntity());

    return BoardResponseDto.from(updateBoard);
  }

  // 게시글 삭제
  public void deleteBoard(Long boardId) {
    boardService.deleteBoard(boardId);
  }

  public Long getAuthorIdByBoardId(Long boardId) {
    return boardService.getAuthorIdByBoardId(boardId);
  }

  public List<BoardViewResponseDto> getFollowersBoards(Long userId, Pageable pageable) {
    return boardService.findAllUserFollowerBoard(userId, pageable).stream()
        .map(BoardViewResponseDto::from).collect(Collectors.toList());
  }

  public List<BoardViewResponseDto> getLikeBoards(Long userId) {
    List<Board> boards = boardService.findAllLikeBoards(userId);
    List<BoardViewResponseDto> boardViewResponseDto = new ArrayList<>();
    for (Board board : boards) {
      boardViewResponseDto.add(BoardViewResponseDto.from(board));
    }
    return boardViewResponseDto;
  }

}
