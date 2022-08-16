package dhci.cryptos.config;

import dhci.cryptos.auth.AuthGroup;
import dhci.cryptos.auth.User;
import dhci.cryptos.dto.CourseDto;
import dhci.cryptos.dto.TutorDto;
import dhci.cryptos.dto.UserDto;
import dhci.cryptos.model.Tutor;
import dhci.cryptos.repositories.AuthUserGroupRepository;
import dhci.cryptos.services.core.impl.CourseService;
import dhci.cryptos.services.core.impl.TuitionService;
import dhci.cryptos.services.core.impl.TutorService;
import dhci.cryptos.services.core.impl.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class DataInitializer {

    private final UserService userService;
    private final CourseService courseService;
    private final TuitionService tuitionService;
    private final TutorService tutorService;
    private final AuthUserGroupRepository authUserGroupRepository;

    public DataInitializer(UserService userService, CourseService courseService, TuitionService tuitionService, TutorService tutorService, AuthUserGroupRepository authUserGroupRepository) {
        this.userService = userService;
        this.courseService = courseService;
        this.tuitionService = tuitionService;
        this.tutorService = tutorService;
        this.authUserGroupRepository = authUserGroupRepository;
    }

    @PostConstruct
    public void initData() throws Exception {

//        UserDto userDto1 = new UserDto("UserDefault","User-123!","User","Test","user@gmail.com","https://i.imgur.com/T0n0PH9.jpg");
//        userService.createUser(userDto1);

        UserDto userDto2 = new UserDto("Administrator","Admin-123!","Test","User","admin@gmail.com","https://i.imgur.com/k4k1fOM.png");
        userService.createUser(userDto2);
        AuthGroup user = authUserGroupRepository.findById(1L).get();
        user.setAuthgroup("ADMIN");
        authUserGroupRepository.save(user);

       // authUserGroupRepository.save(new AuthGroup("UserDefault","USER"));
//       authUserGroupRepository.save(new AuthGroup("Administrator","ADMIN"));

        TutorDto tutorDto1 = new TutorDto("John","Doe","johndoe@gmail.com","Bitcoin","https://i.imgur.com/hhuQ4yc.jpg");
        tutorService.create(tutorDto1);
        Tutor tutor1 = tutorService.getById(1L);
        tutor1.setDetails("Bitcoin tutor graduated on Harvard university, expertise blockchain");
        tutorService.update(tutor1,1L);

        TutorDto tutorDto2 = new TutorDto("Vitalik","Buterin","vitaliketherium@gmail.com","Etherium","https://i.imgur.com/Olu2PuN.jpg");
        tutorService.create(tutorDto2);
        Tutor tutor2 = tutorService.getById(2L);
        tutor1.setDetails("Etherium tutor, expertise blockchain");
        tutorService.update(tutor2,1L);

        CourseDto courseDto1 = new CourseDto("Bitcoin course","Bitcoin tutorial for begginer. From a begginer to expert","Easy","The tutorial begins by introducing what bitcoins are, then proceeds with the installation of the bitcoin client software and wallets to make bitcoins transactions possible.","https://www.udemy.com/course/bitcoin-or-how-i-learned-to-stop-worrying-and-love-crypto/","https://static.dw.com/image/60727809_101.jpg",tutor1);
        CourseDto courseDto2 = new CourseDto("Etherium bootcamp","Etherium bootcamp for blockchain lovers","Intermediate","Looking at the advantages offered by Bitcoin − a digital currency, people wanted to use the concept of Blockchain in their own applications.","https://www.udemy.com/course/ethereum-and-solidity-the-complete-developers-guide/","https://cloudfront-us-east-1.images.arcpublishing.com/coindesk/L424HUS65FGRVFPTYYPG24CIYI.jpg",tutor2);

        courseService.create(courseDto1);
        courseService.create(courseDto2);

//        tuitionService.createTuition(courseService.getById(1L).getCourse_id(),"UserDefault");
//        tuitionService.createTuition(courseService.getById(2L).getCourse_id(),"UserDefault");

    }
}