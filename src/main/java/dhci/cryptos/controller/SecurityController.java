package dhci.cryptos.controller;

import dhci.cryptos.auth.User;
import dhci.cryptos.model.Tuition;
import dhci.cryptos.services.core.impl.TuitionService;
import dhci.cryptos.services.core.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
@AllArgsConstructor
public class SecurityController {

    private final TuitionService tuitionService;
    private final UserService userService;

    @GetMapping("/profile")
    public String getUserProfile(Authentication authentication, Model model) {
        try {
            String currentUsername = authentication.getName();
            User user = userService.getByUsername(currentUsername);
            List<Tuition> tuitions = tuitionService.getAllByUser(user);
            int courses_size = tuitions.size();
            model.addAttribute("user", user);
            model.addAttribute("tuitions", tuitions);
            model.addAttribute("courses_size", courses_size);
            return "user/profile";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping("/user/edit/{userID}")
    public String getForEdit(@PathVariable Long userID, Authentication authentication, Model model) {

        try {
            String currentusername = authentication.getName();
            User current = userService.getById(userID);
            if (currentusername.equals(current.getUsername())) {
            model.addAttribute(current);
            return "user/user-edit";
            } else {
                throw new Exception("Error de autenticacion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/user/edit/{userID}")
    public String updateUser(@PathVariable Long userID, Authentication authentication, User user, Model model) {

        try {
            User current = userService.getById(userID);
            String currentusername = authentication.getName();
            if (currentusername.equals(current.getUsername())) {
                current.setName(user.getName());
                current.setSurname(user.getSurname());
                current.setEmail(user.getEmail());
                current.setImgurl(user.getImgurl());
                userService.update(current);

                return "redirect:/profile";
            } else {
                throw new Exception("Error de autenticacion");
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/user/patch/{id_user}")
    public String patchUser(@PathVariable Long id_user, Authentication authentication, User user, Model model) {

        try {
            User current = userService.getById(id_user);
            String currentusername = authentication.getName();
            if (currentusername.equals(current.getUsername())) {
                current.setDetails(user.getDetails());
                userService.patch(current);

                return "redirect:/profile";
            } else {
                throw new Exception("Error de autenticacion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }
}
