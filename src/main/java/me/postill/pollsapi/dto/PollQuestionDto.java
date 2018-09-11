package me.postill.pollsapi.dto;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PollQuestionDto {
	
	@JsonIgnore
	private long id;

	@NotEmpty
	private String question;

	@Size(min = 1, max = 20)
	private List<String> choices;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PollQuestionDto withId(long id) {
		this.id = id;
		return this;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public PollQuestionDto withQuestion(String question) {
		this.question = question;
		return this;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}

	public PollQuestionDto withChoices(List<String> choices) {
		this.choices = choices;
		return this;
	}
}
