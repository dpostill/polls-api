package me.postill.pollsapi.data;

import org.springframework.data.jpa.repository.JpaRepository;

import me.postill.pollsapi.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
