package F12.newsfeedproject.domain.board.repository;

import F12.newsfeedproject.domain.board.entity.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

  @Query("select b from Board b join fetch b.user order by b.createdDate desc")
  List<Board> findAllByOrderByCreatedDateDesc();

  @Query(
      "select b from Board b join fetch b.user u where u in " +
          "(select f.follower from Follow f where f.following.userId = :userId)"
  )
  Page<Board> findAllUserFollowerBoard(@Param("userId") Long userId, Pageable pageable);

  @Query(
      "select b from Board b join fetch b.user where b in " +
          "(select ubl.board from UserBoardLike ubl where ubl.user.userId = :userId)"  )
  List<Board> findAllLikeBoards(@Param("userId") Long userId);

  @Query("select b from Board b join fetch b.user where b.boardId = :boardId")
  Optional<Board> findByBoardIdWithUser(@Param("boardId") Long boardId);
}
