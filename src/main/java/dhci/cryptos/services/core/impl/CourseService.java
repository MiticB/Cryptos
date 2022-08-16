package dhci.cryptos.services.core.impl;

import dhci.cryptos.dto.CourseDto;
import dhci.cryptos.model.Course;
import dhci.cryptos.model.Tutor;
import dhci.cryptos.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void create(CourseDto courseDto) throws Exception{
        if (null != courseRepository.findByName(courseDto.getCourse_name())) {
            throw new Exception("A course with the name already exists " + courseDto.getCourse_name());
        }
        Course course = new Course(courseDto.getCourse_name(),courseDto.getCourse_description(),courseDto.getDetails(),courseDto.getDifficulty(),courseDto.getUrl(),courseDto.getImgurl(),courseDto.getTutor());

        courseRepository.save(course);
    }

    public void update(Course course, Long course_id) {
        Course current_course = courseRepository.findById(course_id).orElseThrow(IllegalArgumentException::new);

        current_course.setName(course.getName());
        current_course.setDescription(course.getDescription());
        current_course.setDetails(course.getDetails());
        current_course.setDifficulty(course.getDifficulty());
        current_course.setUrl(course.getUrl());
        current_course.setImgurl(course.getImgurl());
        current_course.setTutor(course.getTutor());

        courseRepository.save(current_course);
    }

    public void delete(Course course) { courseRepository.delete(course); }


    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course getById(Long course_id){
        return this.courseRepository.findById(course_id).get();
    }

    public List<Course> getAllByTutor(Tutor tutor){
        return this.courseRepository.findAllByTutor(tutor);
    }

}
