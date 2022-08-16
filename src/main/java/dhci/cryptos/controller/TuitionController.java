package dhci.cryptos.controller;

import dhci.cryptos.auth.User;
import dhci.cryptos.model.Course;
import dhci.cryptos.services.core.impl.CourseService;
import dhci.cryptos.services.core.impl.TuitionService;
import dhci.cryptos.services.core.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tuition")
@PreAuthorize("hasRole('ROLE_USER')")
public class TuitionController {

    private  TuitionService tuitionService;
    private  UserService userService;
    private  CourseService courseService;

    @Autowired
    public TuitionController(TuitionService tuitionService, UserService userService, CourseService courseService) {
        this.tuitionService = tuitionService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/save/{course_id}")
    public String saveCourse(@PathVariable Long course_id, Authentication authentication, Model model) {
        try {
            String username = authentication.getName();
            tuitionService.createTuition(course_id,username);
            User user = userService.getByUsername(username);
            Course course = courseService.getById(course_id);
            model.addAttribute("course", course);
            model.addAttribute("user", user);
            return "tuition";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }
}
