package me.postill.pollsapi.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class ChoiceDto {
	@NotEmpty
	private String choice;
	private Integer votes;

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public ChoiceDto withChoice(String choice) {
		this.choice = choice;
		return this;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}

	public ChoiceDto withVotes(Integer votes) {
		this.votes = votes;
		return this;
	}
}
