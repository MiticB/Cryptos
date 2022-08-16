package dhci.cryptos.dto;

import dhci.cryptos.model.Tutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDto {
    private String course_name;
    private String course_description;
    private String difficulty;
    private String details;
    private String url;
    private String imgurl;
    private Tutor tutor;
}
