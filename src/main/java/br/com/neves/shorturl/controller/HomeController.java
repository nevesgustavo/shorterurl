package br.com.neves.shorturl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeController {
// ------------------------------ FIELDS ------------------------------

    private static final String HOME = "<html><body><label>HOME</body></html>";
    private static final String DASHBOARD = "<html><body><label>DASHBOARD</body></html>";

// -------------------------- OTHER METHODS --------------------------

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        return createModelAndView(DASHBOARD);
    }

    @GetMapping
    public ModelAndView index() {
        return createModelAndView(HOME);
    }

    private ModelAndView createModelAndView(String html) {
        return new ModelAndView(new AbstractView() {
            @Override
            protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
                response.setContentType("text/html");
                response.getWriter().write(html);
            }
        });
    }
}
