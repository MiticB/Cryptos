package dhci.cryptos.dto;

import dhci.cryptos.auth.User;
import dhci.cryptos.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TuitionDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private User user;
    private Course course;
}
