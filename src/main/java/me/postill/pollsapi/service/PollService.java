package me.postill.pollsapi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.postill.pollsapi.data.QuestionRepository;
import me.postill.pollsapi.domain.Choice;
import me.postill.pollsapi.domain.Question;
import me.postill.pollsapi.dto.ChoiceDto;
import me.postill.pollsapi.dto.PollQuestionDto;
import me.postill.pollsapi.dto.PollResultDto;

@Service
public class PollService {

	@Autowired
	private QuestionRepository questionRepository;

	public PollResultDto createQuestion(PollQuestionDto createQuestionDto) {
		Question question = new Question()
				.withQuestion(createQuestionDto.getQuestion())
				.withPublishedAt(new Date());

		final List<Choice> choices = new ArrayList<>();

		for (String choice : createQuestionDto.getChoices()) {
			choices.add(new Choice()
					.withChoice(choice)
					.withVotes(0)
					.withQuestion(question));
		}

		question.setChoices(choices);

		question = questionRepository.save(question);

		return new PollResultDto()
				.withId(question.getId())
				.withQuestion(question.getQuestion())
				.withPublishedAt(question.getPublishedAt())
				.withChoices(getChoiceDtoList(question.getChoices()));
	}

	public List<PollQuestionDto> getPollQuestions() {
		final List<Question> questions = questionRepository.findAll();
		final List<PollQuestionDto> pollQuestions = new ArrayList<>();

		for (Question question : questions) {
			pollQuestions.add(new PollQuestionDto()
					.withId(question.getId())
					.withQuestion(question.getQuestion())
					.withChoices(getChoiceStrings(question.getChoices())));
		}

		return pollQuestions;
	}

	public List<PollResultDto> getPollResults() {
		final List<Question> questions = questionRepository.findAll();
		final List<PollResultDto> pollResults = new ArrayList<>();

		for (Question question : questions) {
			pollResults.add(new PollResultDto()
					.withId(question.getId())
					.withQuestion(question.getQuestion())
					.withPublishedAt(question.getPublishedAt())
					.withChoices(getChoiceDtoList(question.getChoices())));
		}

		return pollResults;
	}

	public void incrementChoiceCount(PollQuestionDto pollQuestionDto, String selectedChoice) {
		final Question question = questionRepository.findOne(pollQuestionDto.getId());

		for (Choice choice : question.getChoices()) {
			if (choice.getChoice().equals(selectedChoice)) {
				choice.setVotes(choice.getVotes() + 1);
				break;
			}
		}

		questionRepository.save(question);
	}

	private List<ChoiceDto> getChoiceDtoList(List<Choice> choices) {
		final List<ChoiceDto> choiceDtoList = new ArrayList<>();

		for (Choice choice : choices) {
			choiceDtoList.add(new ChoiceDto()
					.withChoice(choice.getChoice())
					.withVotes(choice.getVotes()));
		}

		return choiceDtoList;
	}

	private List<String> getChoiceStrings(List<Choice> choices) {
		final List<String> choiceList = new ArrayList<>();

		for (Choice choice : choices) {
			choiceList.add(choice.getChoice());
		}

		return choiceList;
	}
}
