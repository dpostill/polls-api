package me.postill.pollsapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import me.postill.pollsapi.dto.PollQuestionDto;
import me.postill.pollsapi.dto.PollResultDto;
import me.postill.pollsapi.service.PollService;

@RestController
public class QuestionController {

	@Autowired
	private PollService pollService;

	@GetMapping("/questions")
	public ResponseEntity<List<PollResultDto>> getQuestions() {
		final List<PollResultDto> pollResults = pollService.getPollResults();

		return ResponseEntity.ok(pollResults);
	}

	@PostMapping("/questions")
	public ResponseEntity<PollResultDto> createQuestion(
			@Valid @RequestBody final PollQuestionDto createQuestionDto,
			final UriComponentsBuilder uriComponentsBuilder) {
		final PollResultDto questionDto = pollService.createQuestion(createQuestionDto);
		final UriComponents uriComponents = uriComponentsBuilder
				.path("/questions/{id}")
				.buildAndExpand(questionDto.getId());

		return ResponseEntity.created(uriComponents.toUri()).body(questionDto);
	}
}
