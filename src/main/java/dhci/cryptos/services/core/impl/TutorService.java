package dhci.cryptos.services.core.impl;

import dhci.cryptos.dto.TutorDto;
import dhci.cryptos.model.Tutor;
import dhci.cryptos.repositories.TutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

private final TutorRepository tutorRepository;

    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }


    public void create(TutorDto tutorDto){
        Tutor tutor = new Tutor(tutorDto.getName(),tutorDto.getSurname(),tutorDto.getEmail(),tutorDto.getDescription(),tutorDto.getImgurl());
        tutorRepository.save(tutor);
    }

    public List<Tutor> getAll() {
        return tutorRepository.findAll();
    }


    public void update(Tutor tutor,Long id_proffesor){
        Tutor current_tutor = tutorRepository.findById(id_proffesor).orElseThrow(IllegalArgumentException::new);
        current_tutor.setName(tutor.getName());
        current_tutor.setSurname(tutor.getSurname());
        current_tutor.setEmail(tutor.getEmail());
        current_tutor.setDescription(tutor.getDescription());
        current_tutor.setImgurl(tutor.getImgurl());

        tutorRepository.save(tutor);
    }

    public void patch(Tutor tutor) {
        Tutor current = tutorRepository.findById(tutor.getId()).orElseThrow(IllegalArgumentException::new);

        current.setDetails(tutor.getDetails());

        tutorRepository.save(current);
    }

    public void delete(Tutor tutor) {
        tutorRepository.delete(tutor);
    }

    public Tutor getById(Long tutor_id){
        return this.tutorRepository.findById(tutor_id).get();
    }

}
