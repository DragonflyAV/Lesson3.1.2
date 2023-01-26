package web.controller;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.services.UserService;

@Controller
@RequestMapping("/")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping()
	public String index(ModelMap model) {
		model.addAttribute("users", userService.findAll());
		return "pages/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, ModelMap model) {
		model.addAttribute("user", userService.findOne(id));
		return "pages/show";
	}

	@GetMapping("/new")
	public String newPerson(@ModelAttribute("user") User user) {
		return "pages/new";
	}

	@PostMapping()
	public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "pages/new";
		}
		userService.save(user);
		return "redirect:/";
	}

	@GetMapping("/{id}/edit")
	public String edit(ModelMap model, @PathVariable("id") int id) {
		model.addAttribute("user", userService.findOne(id));
		return "pages/edit";
	}

	@PatchMapping("/{id}")
	public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
						 @PathVariable("id") int id) {
		if (bindingResult.hasErrors()) {
			return "pages/edit";
		}
		userService.update(id, user);
		return "redirect:/";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		userService.delete(id);
		return "redirect:/";
	}
}