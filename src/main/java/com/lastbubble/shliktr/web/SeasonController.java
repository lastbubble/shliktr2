package com.lastbubble.shliktr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SeasonController {

	@RequestMapping("/season")
	public String season() { return "pages/season"; }
}
