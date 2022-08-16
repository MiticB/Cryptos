package dhci.cryptos.controller;

import dhci.cryptos.auth.User;
import dhci.cryptos.dto.CourseDto;
import dhci.cryptos.model.Course;
import dhci.cryptos.model.Tutor;
import dhci.cryptos.services.core.impl.CourseService;
import dhci.cryptos.services.core.impl.TuitionService;
import dhci.cryptos.services.core.impl.TutorService;
import dhci.cryptos.services.core.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    private  CourseService courseService;
    private  TutorService tutorService;
    private  UserService userService;
    private  TuitionService tuitionService;

    @Autowired
    public CoursesController(CourseService courseService, TutorService tutorService, UserService userService, TuitionService tuitionService) {
        super();
        this.courseService = courseService;
        this.tutorService = tutorService;
        this.userService = userService;
        this.tuitionService = tuitionService;
    }


    @GetMapping("/add/{id_tutor}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCourse(@PathVariable Long id_tutor, Model model) {
        try {
            Tutor current = tutorService.getById(id_tutor);
            model.addAttribute("course", new CourseDto());
            model.addAttribute("tutor", current);
            return "courses/add-course";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/add/{id_tutor}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveCourse(@PathVariable Long id_tutor, CourseDto courseDto, Model model) {
        try {
            Tutor current = tutorService.getById(id_tutor);
            courseDto.setTutor(current);
            courseService.create(courseDto);
            return "redirect:/courses";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }

    }

    @GetMapping("/edit/{id_course}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getCourseForUpdate(@PathVariable Long id_course, Model model) {
        try {
            Course current = courseService.getById(id_course);
            model.addAttribute("course", current);
            return "courses/edit-course";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/edit/{id_tutor}/{id_course}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateCourse(@PathVariable Long id_tutor, @PathVariable Long id_course, Course course, Model model, RedirectAttributes attributes) {

        try {
            Tutor current_tutor = tutorService.getById(id_tutor);
            course.setTutor(current_tutor);
            courseService.update(course,id_course);
            attributes.addAttribute("course_id", id_course);

            return "redirect:/courses/{id_course}";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String getCourses(Model model) {
        List<Course> courses = courseService.getAll();
        model.addAttribute("courses", courses);
        return "courses/courses";
    }

    @GetMapping("/delete/{id_course}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCourse(@PathVariable Long id_course, Model model) {
        try {
            Course current_course = courseService.getById(id_course);
            courseService.delete(current_course);

            return "redirect:/courses";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping("/{id_course}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String getCourseDetails(@PathVariable Long id_course, Authentication authentication, Model model) {
        String username = authentication.getName();
        Boolean isRegistered = false;
        try {
            Course course = this.courseService.getById(id_course);
            User user = userService.getByUsername(username);
            if (null != tuitionService.getByCourseAndUser(course,user)) {
                isRegistered = true;
            }
            model.addAttribute("course", course);
            model.addAttribute("isRegistered", isRegistered);
            return "courses/course-details";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }
}
