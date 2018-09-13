package me.postill.pollsapi.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String question;

	private Date publishedAt;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "question")
	private List<Choice> choices;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Question withId(long id) {
		this.id = id;
		return this;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Question withQuestion(String question) {
		this.question = question;
		return this;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public Question withPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
		return this;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public Question withChoices(List<Choice> choices) {
		this.choices = choices;
		return this;
	}
}
