package F12.newsfeedproject.domain.board.service;

import F12.newsfeedproject.domain.board.entity.Board;
import F12.newsfeedproject.domain.board.repository.BoardRepository;
import F12.newsfeedproject.global.exception.board.BoardNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  @Transactional
  public Board saveBoard(Board board) {
    return boardRepository.save(board);
  }

  public Board findByBoardId(Long boardId) {
    return boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
  }

  public List<Board> getBoards() {
    log.info(("---------------------------------------------------------"));
    log.info(("싸그리 전체 조회"));
    return boardRepository.findAllByOrderByCreatedDateDesc();
  }

  @Transactional
  public void updateBoard(Board updateBoard, Board board) {
    updateBoard.update(board);
  }

  public void deleteBoard(Long boardId) {
    boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
    boardRepository.deleteById(boardId);
  }

  public Long getAuthorIdByBoardId(Long boardId) {
    log.info(("---------------------------------------------------------"));
    log.info(("유저랑 조회"));
    Board board = boardRepository.findByBoardIdWithUser(boardId).orElseThrow(BoardNotFoundException::new);
    return board.getUser().getUserId();
  }

  public List<Board> findAllUserFollowerBoard(Long userId) {
    log.info(("---------------------------------------------------------"));
    log.info(("팔로워 전체 조회"));
    return boardRepository.findAllUserFollowerBoard(userId);
  }
  public List<Board> findAllLikeBoards(Long userId) {
    log.info(("---------------------------------------------------------"));
    log.info(("좋아요 전체 조회"));
    return boardRepository.findAllLikeBoards(userId);
  }

  public Board findByBoardIdWithUser(Long boardId) {
    log.info(("---------------------------------------------------------"));
    log.info(("유저랑 조회"));
    return boardRepository.findByBoardIdWithUser(boardId).orElseThrow(BoardNotFoundException::new);
  }
}

