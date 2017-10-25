package com.lastbubble.shliktr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PredictionsController {

	@RequestMapping("/predictions")
	public String predictions() { return "pages/predictions"; }
}
