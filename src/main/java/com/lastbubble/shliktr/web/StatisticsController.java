package com.lastbubble.shliktr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatisticsController {

	@RequestMapping("/stats")
	public String stats() { return "pages/stats"; }
}
