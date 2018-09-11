package me.postill.pollsapi.controller;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.postill.pollsapi.data.QuestionRepository;
import me.postill.pollsapi.domain.Choice;
import me.postill.pollsapi.domain.Question;
import me.postill.pollsapi.dto.PollQuestionDto;
import me.postill.pollsapi.service.PollService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class QuestionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuestionRepository questionRepositoryMock;

	@InjectMocks
	private PollService pollService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldGetQuestions() throws Exception {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		final List<Question> questions = Arrays.<Question>asList(
				new Question()
						.withId(1L)
						.withPublishedAt(dateFormat.parse("2018-08-31 23:59:59"))
						.withQuestion("Favourite version control?")
						.withChoices(Arrays.<Choice>asList(
								new Choice().withChoice("CVS").withVotes(5),
								new Choice().withChoice("SVN").withVotes(10),
								new Choice().withChoice("GIT").withVotes(20),
								new Choice().withChoice("Mercurial").withVotes(15))),
				new Question()
						.withId(2L)
						.withPublishedAt(dateFormat.parse("2018-09-10 01:02:03"))
						.withQuestion("Favourite JS framework?")
						.withChoices(Arrays.<Choice>asList(
								new Choice().withChoice("AngularJS").withVotes(6),
								new Choice().withChoice("React.js").withVotes(9),
								new Choice().withChoice("Ember.js").withVotes(3))));

		when(questionRepositoryMock.findAll())
				.thenReturn(questions);

		mockMvc.perform(get("/questions"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].question", is("Favourite version control?")))
				.andExpect(jsonPath("$[0].publishedAt", is("2018-08-31T23:59:59.000Z")))
				.andExpect(jsonPath("$[0].choices[0].choice", is("CVS")))
				.andExpect(jsonPath("$[0].choices[0].votes", is(5)))
				.andExpect(jsonPath("$[0].choices[1].choice", is("SVN")))
				.andExpect(jsonPath("$[0].choices[1].votes", is(10)))
				.andExpect(jsonPath("$[0].choices[2].choice", is("GIT")))
				.andExpect(jsonPath("$[0].choices[2].votes", is(20)))
				.andExpect(jsonPath("$[0].choices[3].choice", is("Mercurial")))
				.andExpect(jsonPath("$[0].choices[3].votes", is(15)))
				.andExpect(jsonPath("$[1].question", is("Favourite JS framework?")))
				.andExpect(jsonPath("$[1].publishedAt", is("2018-09-10T01:02:03.000Z")))
				.andExpect(jsonPath("$[1].choices[0].choice", is("AngularJS")))
				.andExpect(jsonPath("$[1].choices[0].votes", is(6)))
				.andExpect(jsonPath("$[1].choices[1].choice", is("React.js")))
				.andExpect(jsonPath("$[1].choices[1].votes", is(9)))
				.andExpect(jsonPath("$[1].choices[2].choice", is("Ember.js")))
				.andExpect(jsonPath("$[1].choices[2].votes", is(3)));

		verify(questionRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(questionRepositoryMock);
	}

	@Test
	public void shouldCreateQuestion() throws Exception {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		final ArgumentCaptor<Question> questionCaptor = ArgumentCaptor.forClass(Question.class);
		final PollQuestionDto question = new PollQuestionDto()
				.withQuestion("Favourite version control?")
				.withChoices(Arrays.<String>asList(
						"CVS",
						"SVN",
						"GIT",
						"Mercurial"));

		final Question saveResponse = new Question()
				.withQuestion("Favourite version control?")
				.withPublishedAt(dateFormat.parse("2018-08-31 23:59:59"))
				.withChoices(Arrays.<Choice>asList(
						new Choice().withChoice("CVS").withVotes(0),
						new Choice().withChoice("SVN").withVotes(0),
						new Choice().withChoice("GIT").withVotes(0),
						new Choice().withChoice("Mercurial").withVotes(0)));

		when(questionRepositoryMock.save(questionCaptor.capture()))
				.thenReturn(saveResponse);

		mockMvc.perform(post("/questions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(question)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(header().string("Location", endsWith("/questions/0")))
				.andExpect(jsonPath("$.question", is("Favourite version control?")))
				.andExpect(jsonPath("$.publishedAt", is("2018-08-31T23:59:59.000Z")))
				.andExpect(jsonPath("$.choices[0].choice", is("CVS")))
				.andExpect(jsonPath("$.choices[0].votes", is(0)))
				.andExpect(jsonPath("$.choices[1].choice", is("SVN")))
				.andExpect(jsonPath("$.choices[1].votes", is(0)))
				.andExpect(jsonPath("$.choices[2].choice", is("GIT")))
				.andExpect(jsonPath("$.choices[2].votes", is(0)))
				.andExpect(jsonPath("$.choices[3].choice", is("Mercurial")))
				.andExpect(jsonPath("$.choices[3].votes", is(0)));

		final Question saveRequest = questionCaptor.getValue();

		assertThat(saveRequest.getQuestion(), is("Favourite version control?"));
		assertThat(saveRequest.getId(), is(0L));
		assertNotNull(saveRequest.getPublishedAt());
		assertThat(saveRequest.getChoices().get(0).getChoice(), is("CVS"));
		assertThat(saveRequest.getChoices().get(0).getVotes(), is(0));
		assertThat(saveRequest.getChoices().get(1).getChoice(), is("SVN"));
		assertThat(saveRequest.getChoices().get(1).getVotes(), is(0));
		assertThat(saveRequest.getChoices().get(2).getChoice(), is("GIT"));
		assertThat(saveRequest.getChoices().get(2).getVotes(), is(0));
		assertThat(saveRequest.getChoices().get(3).getChoice(), is("Mercurial"));
		assertThat(saveRequest.getChoices().get(3).getVotes(), is(0));

		verify(questionRepositoryMock, times(1)).save(any(Question.class));
		verifyNoMoreInteractions(questionRepositoryMock);
	}

	@Test
	public void shouldNotCreateQuestionWhenNoChoices() throws Exception {
		final PollQuestionDto question = new PollQuestionDto()
				.withQuestion("Favourite version control?")
				.withChoices(new ArrayList<>());

		mockMvc.perform(post("/questions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(question)))
				.andExpect(status().isBadRequest());

		verify(questionRepositoryMock, times(0)).save(any(Question.class));
	}

	@Test
	public void shouldNotCreateQuestionWhenNoQuestion() throws Exception {
		final PollQuestionDto question = new PollQuestionDto()
				.withQuestion(null)
				.withChoices(Arrays.<String>asList("Test"));

		mockMvc.perform(post("/questions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(question)))
				.andExpect(status().isBadRequest());

		verify(questionRepositoryMock, times(0)).save(any(Question.class));
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper()
					.writerWithDefaultPrettyPrinter()
					.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
