package dhci.cryptos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tutor")
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tutor_name")
    private String name;

    private String surname;

    private String email;

    private String description;

    private String details;

    private String imgurl;


    public Tutor(String name, String surname, String email, String description, String imgurl) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.description = description;
        this.imgurl = imgurl;
    }

    public String getFullName(){
        return this.name + " "+ this.surname;
    }

    public Tutor(String details) {
        this.details = details;
    }
}
