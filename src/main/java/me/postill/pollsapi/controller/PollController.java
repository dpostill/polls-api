package me.postill.pollsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import me.postill.pollsapi.dto.PollQuestionDto;
import me.postill.pollsapi.dto.PollResultDto;
import me.postill.pollsapi.dto.Selection;
import me.postill.pollsapi.service.PollService;

@Controller
@SessionAttributes({ "questions", "questionIndex" })
public class PollController {

	@Autowired
	private PollService pollService;

	@RequestMapping(value = "/poll.html")
	private ModelAndView pollQuestionPage() {
		final List<PollQuestionDto> pollQuestions = pollService.getPollQuestions();

		if (pollQuestions.size() > 0) {
			return pollQuestionForm(pollQuestions, 0);
		} else {
			return new ModelAndView("poll-empty");
		}
	}

	@RequestMapping(value = "/results.html")
	private ModelAndView pollResultPage() {
		final List<PollResultDto> pollResults = pollService.getPollResults();

		if (pollResults.size() > 0) {
			return pollResultForm(pollResults);
		} else {
			return new ModelAndView("poll-empty");
		}
	}

	@RequestMapping(value = "/poll-submit")
	private ModelAndView pollSubmit(
			@SessionAttribute("questions") final List<PollQuestionDto> pollQuestions,
			@SessionAttribute("questionIndex") Integer questionIndex,
			@ModelAttribute("selection") Selection selection) {
		pollService.incrementChoiceCount(pollQuestions.get(questionIndex), selection.getSelected());

		if (++questionIndex < pollQuestions.size()) {
			return pollQuestionForm(pollQuestions, questionIndex);
		} else {
			return pollResultPage();
		}
	}

	private ModelAndView pollQuestionForm(final List<PollQuestionDto> pollQuestions, int questionIndex) {
		final ModelAndView mav = new ModelAndView("poll-form");

		mav.addObject("questions", pollQuestions);
		mav.addObject("question", pollQuestions.get(questionIndex).getQuestion());
		mav.addObject("choices", pollQuestions.get(questionIndex).getChoices());
		mav.addObject("selection", new Selection());
		mav.addObject("questionIndex", questionIndex);
		mav.addObject("questionCount", pollQuestions.size());

		return mav;
	}

	private ModelAndView pollResultForm(final List<PollResultDto> pollResults) {
		final ModelAndView mav = new ModelAndView("poll-result");

		mav.addObject("results", pollResults);

		return mav;
	}
}
