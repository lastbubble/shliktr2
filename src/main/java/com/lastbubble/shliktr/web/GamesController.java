package com.lastbubble.shliktr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GamesController {

	@RequestMapping("/games")
	public String games() { return "pages/games"; }
}
