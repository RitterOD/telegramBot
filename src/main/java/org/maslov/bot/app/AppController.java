package org.maslov.bot.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class AppController {

    public static final String INDEX_VIEW = "index";

    @GetMapping
    public ModelAndView getIndex() {
        return new ModelAndView(INDEX_VIEW);
    }
}
