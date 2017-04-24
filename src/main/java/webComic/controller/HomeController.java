package webComic.controller;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import webComic.data.Comic;
import webComic.service.JsonImport;


@Controller
public class HomeController {

	@Autowired
	JsonImport jService;
	
	@RequestMapping(value = "/")
	public ModelAndView init() {
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/comic/list/")
	public String getAllComics(Model model) {
		List<Comic> comics=jService.getComicList();
	
		model.addAttribute("comics", comics);
		return "list";

	}

	@RequestMapping(value = "/comic/titles/")
	public String getAllTitles(Model model) {
		Set<String> titles=jService.getTitleSet();
	
		model.addAttribute("titles", titles);
		return "comiclist";

	}
	
	@RequestMapping(value = "/comic/{title}/")
	public String getComicsByTitle(@PathVariable("title") String title, Model model) {
		List<Comic> comics = jService.getIssuesByTitle(title);
		model.addAttribute("comics", comics);
		
		Set<String> titles=jService.getTitleSet();
		model.addAttribute("titles", titles);
		
		model.addAttribute("URLtitle", title);
		
		return "titlecontent";
	}
	
	@RequestMapping(value = "/comic/{title}/{id}/")
	public String getComicById(@PathVariable("title") String title, @PathVariable("id") int id, Model model) {
		Comic comic = jService.getComicById(id);
		model.addAttribute("thiscomic", comic);
		
		List<Comic> comics = jService.getIssuesByTitle(title);
		model.addAttribute("comics", comics);
		
		Set<String> titles=jService.getTitleSet();
		model.addAttribute("titles", titles);
		
		model.addAttribute("URLtitle", title);
		
		return "singleComic";
	}
	
	@RequestMapping(value = "/comic/addComic/", method=RequestMethod.GET)
    public String addComicForm(Model model) {
        model.addAttribute("newComic", new Comic());
        return "addComicForm";
    }

	@RequestMapping(value = "/comic/addComic/", method=RequestMethod.POST)
    public String addComicSubmit(@ModelAttribute("newComic") Comic newComic) {
		jService.addComic(newComic);
        return "addComicSuccess";
    }
	
	@RequestMapping(value = "/comic/{title}/{id}/deleteComic/")
	public String deleteComicById(@PathVariable("title") String title, @PathVariable("id") int id, Model model) {
		String outputString = jService.deleteComic(id);
		model.addAttribute("output", outputString);
		
		model.addAttribute("URLtitle", title);
		
		return "removeComicStatus";
	}
}