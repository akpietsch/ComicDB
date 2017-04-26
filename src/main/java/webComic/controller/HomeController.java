package webComic.controller;

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
import webComic.data.Comic;
import webComic.service.JsonImport;

/**
 * Ein Spring MVC Controller ist für die Verarbeitung der Eingaben zuständig und
 * für deren Kommunikation an das Modell.
 */
@Controller
public class HomeController {
	@Autowired
	JsonImport jService;

	/**
	 * HTTP GET Request Handler 
	 * verantwortlich für das Mapping von / auf das
	 * 'home' Template
	 * 
	 * @return
	 */
	@RequestMapping(value = "/")
	public ModelAndView init() {
		return new ModelAndView("home");
	}

	/**
	 * HTTP GET Request Handler 
	 * verantwortlich für das Mapping von "/comic/list/" auf das 'list' Template 
	 * übergibt die Comicliste der TSV datei
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/comic/list/")
	public String getAllComics(Model model) {
		List<Comic> comics = jService.getComicList();
		model.addAttribute("comics", comics);
		return "list";
	}

	/**
	 *  HTTP GET Request Handler 
	 *  verantwortlich für das Mapping von
	 * "/comic/titles/" auf das 'comiclist' Template 
	 * übergibt Comictitel
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/comic/titles/")
	public String getAllTitles(Model model) {
		Set<String> titles = jService.getTitleSet();
		model.addAttribute("titles", titles);
		return "comiclist";
	}

	/**
	 * sortiert Comics anhand des Titels innerhalb einer Liste
	 * @param title
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/comic/{title}/")
	public String getComicsByTitle(@PathVariable("title") String title, Model model) {
		List<Comic> comics = jService.getIssuesByTitle(title);
		model.addAttribute("comics", comics);
		Set<String> titles = jService.getTitleSet();
		model.addAttribute("titles", titles);
		model.addAttribute("URLtitle", title);
		return "titlecontent";
	}

	/**
	 * HTTP GET Request Handler 
	 * ermöglicht aufruf eines einzelnen Comics
	 * @param title
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/comic/{title}/{id}/")
	public String getComicById(@PathVariable("title") String title, @PathVariable("id") int id, Model model) {
		Comic comic = jService.getComicById(id);
		model.addAttribute("thiscomic", comic);
		List<Comic> comics = jService.getIssuesByTitle(title);
		model.addAttribute("comics", comics);
		Set<String> titles = jService.getTitleSet();
		model.addAttribute("titles", titles);
		model.addAttribute("URLtitle", title);
		return "singleComic";
	}

	/**
	 * HTTP GET Request Handler 
	 * Fügt neues Comics zum View hinzu
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/comic/addComic/", method = RequestMethod.GET)
	public String addComicForm(Model model) {
		model.addAttribute("newComic", new Comic());
		return "addComicForm";
	}

	/**
	 * HTTP POST Request Handler 
	 * fügt vom User erstellen Comic zum Index hinzu 
	 * @param newComic
	 * @return
	 */
	@RequestMapping(value = "/comic/addComic/", method = RequestMethod.POST)
	public String addComicSubmit(@ModelAttribute("newComic") Comic newComic) {
		jService.addComic(newComic);
		return "addComicSuccess";
	}

	/**
	 * löscht Comics aus dem View
	 * @param title
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/comic/{title}/{id}/deleteComic/")
	public String deleteComicById(@PathVariable("title") String title, @PathVariable("id") int id, Model model) {
		String outputString = jService.deleteComic(id);
		model.addAttribute("output", outputString);
		model.addAttribute("URLtitle", title);
		return "removeComicStatus";
	}
}