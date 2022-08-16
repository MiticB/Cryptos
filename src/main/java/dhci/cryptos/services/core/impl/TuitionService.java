package dhci.cryptos.services.core.impl;

import dhci.cryptos.auth.User;
import dhci.cryptos.model.Course;
import dhci.cryptos.model.Tuition;
import dhci.cryptos.repositories.CourseRepository;
import dhci.cryptos.repositories.TuitionRepository;
import dhci.cryptos.auth.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TuitionService {

    private final CourseRepository courseRepository;
    private final TuitionRepository tuitionRepository;
    private final UserRepository userRepository;

    public TuitionService(CourseRepository courseRepository, TuitionRepository tuitionRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.tuitionRepository = tuitionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createTuition(Long course_id, String username) throws Exception {
        Course course = courseRepository.findById(course_id).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findByUsername(username);

        if (null != tuitionRepository.findByCourseAndUser(course, user)) {
            throw new Exception("You are already enrolled in this course");
        }
        LocalDate date = LocalDate.now();
        Tuition tuition = new Tuition(date, user, course);
        tuition.setProgress(0.0);
        tuitionRepository.save(tuition);
    }

    public Tuition getByCourseAndUser(Course course,User user){
        return this.tuitionRepository.findByCourseAndUser(course,user);
    }

    public List<Tuition> getAllByUser(User user){
        return this.tuitionRepository.findAllByUser(user);
    }
}
