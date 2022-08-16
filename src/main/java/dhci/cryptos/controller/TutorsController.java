package dhci.cryptos.controller;

import dhci.cryptos.dto.TutorDto;
import dhci.cryptos.model.Course;
import dhci.cryptos.model.Tutor;
import dhci.cryptos.services.core.impl.CourseService;
import dhci.cryptos.services.core.impl.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tutors")
public class TutorsController {

    private TutorService tutorService;
    private CourseService courseService;

    @Autowired
    public TutorsController(TutorService tutorService, CourseService courseService) {
        this.tutorService = tutorService;
        this.courseService = courseService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addTutor(Model model) {
        model.addAttribute("tutor", new TutorDto());
        return "tutors/tutors-add";
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveTutor(TutorDto tutorDto) {
        tutorService.create(tutorDto);

        return "redirect:/tutors";
    }

    @GetMapping("/edit/{id_profesor}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getProfesorForUpdate(@PathVariable Long id_profesor,
                                       Model model) {
        try {
            Tutor current = tutorService.getById(id_profesor);
            model.addAttribute("tutor", current);
            return "tutors/tutor-edit";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/update/{id_profesor}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateTutor(@PathVariable Long id_profesor,
                                 Tutor tutor, RedirectAttributes attributes, Model model){

        try {

            //Tutor current = tutorService.getById(id_profesor);
            tutorService.update(tutor,id_profesor);
            attributes.addAttribute("tutor_id", id_profesor);
            return "redirect:/tutors/{id_profesor}";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/patch/{id_profesor}")
    public String patchTutor(@PathVariable Long id_profesor, Tutor tutor, RedirectAttributes attributes, Model model) {

        try {
//            Tutor current_tutor = tutorService.getById(id_profesor);
            tutor.setId(id_profesor);
            tutorService.patch(tutor);

            attributes.addAttribute("tutor_id", id_profesor);
            return "redirect:/tutors/{id_profesor}";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String getTutorList(Model model) {
        List<Tutor> tutors = tutorService.getAll();
        model.addAttribute("tutors", tutors);
        return "tutors/tutors";
    }

    @GetMapping("/delete/{id_profesor}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteTutor(@PathVariable Long id_profesor, Model model) {
        try {
            Tutor tutor = tutorService.getById(id_profesor);
            tutorService.delete(tutor);

            return "redirect:/tutors";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping("/{id_profesor}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String getTutorDetails(@PathVariable Long id_profesor, Model model) {
        try {
            Tutor tutor = tutorService.getById(id_profesor);
            model.addAttribute("tutor", tutor);
            List<Course> courses = courseService.getAllByTutor(tutor);
            model.addAttribute("courses", courses);
            return "tutors/tutor-details";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }
}
