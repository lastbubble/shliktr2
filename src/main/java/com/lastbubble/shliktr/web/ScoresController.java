package com.lastbubble.shliktr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScoresController {

	@RequestMapping("/scores")
	public String scores() { return "pages/scores"; }
}
