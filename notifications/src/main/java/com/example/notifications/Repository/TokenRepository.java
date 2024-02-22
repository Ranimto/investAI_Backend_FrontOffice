package com.example.notifications.Repository;

import com.example.notifications.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
      select t from Token t inner join AppUser e \s
      on t.user.id = e.id\s
      where e.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);


   //THE Transactional annotation is used to indicate that the method must be executed in the context of a transaction
    @Transactional
   // the Modifying annotation guarantees that the method modifies the state of the database.
    @Modifying
    @Query("DELETE FROM Token t WHERE t.user.id = :id")
    void deleteAllTokenByUserId(Long id);
}

