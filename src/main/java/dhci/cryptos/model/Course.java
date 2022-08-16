package dhci.cryptos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long course_id;

    @Column(name = "course_name", nullable = false, unique = true)
    private String name;

    private String description;

    private String details;

    private String difficulty;

    private String url;

    private String imgurl;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tutor tutor;

    public Course(String name, String description, String details, String difficulty, String url, String image_url, Tutor tutor) {
        this.name = name;
        this.description = description;
        this.details = details;
        this.difficulty = difficulty;
        this.url = url;
        this.imgurl = image_url;
        this.tutor = tutor;
    }
}