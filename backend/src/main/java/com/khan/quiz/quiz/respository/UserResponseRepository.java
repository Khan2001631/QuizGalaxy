package com.khan.quiz.quiz.respository;

import com.khan.quiz.quiz.model.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResponseRepository extends JpaRepository<UserResponse,Long> {
}
