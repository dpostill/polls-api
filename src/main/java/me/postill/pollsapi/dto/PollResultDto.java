package me.postill.pollsapi.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PollResultDto {

	@JsonIgnore
	private long id;

	private String question;

	@JsonFormat(locale = "en", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date publishedAt;

	private List<ChoiceDto> choices;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PollResultDto withId(long id) {
		this.id = id;
		return this;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public PollResultDto withQuestion(String question) {
		this.question = question;
		return this;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public PollResultDto withPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
		return this;
	}

	public List<ChoiceDto> getChoices() {
		return choices;
	}

	public void setChoices(List<ChoiceDto> choices) {
		this.choices = choices;
	}

	public PollResultDto withChoices(List<ChoiceDto> choices) {
		this.choices = choices;
		return this;
	}
}
