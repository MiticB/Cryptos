package dhci.cryptos.controller;

import dhci.cryptos.model.Course;
import dhci.cryptos.model.Tutor;
import dhci.cryptos.services.core.impl.CourseService;
import dhci.cryptos.services.core.impl.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class APIController {

    private final TutorService tutorService;
    private final CourseService courseService;

    public APIController(TutorService tutorService, CourseService courseService) {
        super();
        this.tutorService = tutorService;
        this.courseService = courseService;
    }

    @GetMapping("/tutors")
    public List<Tutor> getAllTutors() {
        return this.tutorService.getAll();
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return this.courseService.getAll();
    }
}
