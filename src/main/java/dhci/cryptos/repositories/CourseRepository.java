package dhci.cryptos.repositories;

import dhci.cryptos.model.Course;
import dhci.cryptos.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByName(String name);
    List<Course> findAllByTutor(Tutor tutor);
}
