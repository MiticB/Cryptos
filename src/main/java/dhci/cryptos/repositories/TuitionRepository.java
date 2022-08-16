package dhci.cryptos.repositories;

import dhci.cryptos.auth.User;
import dhci.cryptos.model.Course;
import dhci.cryptos.model.Tuition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TuitionRepository extends JpaRepository<Tuition, Long> {

    List<Tuition> findAllByCourse(Course course);
    List<Tuition> findAllByUser(User user);
    Tuition findByCourseAndUser(Course course, User user);
}
