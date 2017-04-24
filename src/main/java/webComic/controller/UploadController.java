package webComic.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import webComic.data.Comic;
import webComic.service.JsonImport;
import webComic.storage.StorageService;

@Controller
public class UploadController {

	@Autowired
	StorageService storageService;

	@Autowired
	JsonImport jService;
	
	List<String> files = new ArrayList<String>();

	@GetMapping("/comic/upload")
	public String listUploadedFiles(Model model) {
		return "uploadForm";
	}

	@PostMapping("/comic/{title}/{id}/upload/")
	public String handleFileUpload(@PathVariable("title") String title, @PathVariable("id") int id, @RequestParam("file") MultipartFile file, Model model) {
		try {
			storageService.store(file);
			model.addAttribute("file", file);
			model.addAttribute("title", title);
			model.addAttribute("id", id);
			files.add(file.getOriginalFilename());
			jService.addComicImgById(id, "../../../images/jpgs/"+file.getOriginalFilename());
			
			Comic comic = jService.getComicById(id);
			model.addAttribute("thiscomic", comic);
			
		} catch (Exception e) {
			model.addAttribute("message", "FAIL to upload " + file.getOriginalFilename() + "!");
		}
		return "addImgSuccess";
	}

	@GetMapping("/getallfiles")
	public String getListFiles(Model model) {
		model.addAttribute("files",
				files.stream()
						.map(fileName -> MvcUriComponentsBuilder
								.fromMethodName(UploadController.class, "getFile", fileName).build().toString())
						.collect(Collectors.toList()));
		model.addAttribute("totalFiles", "TotalFiles: " + files.size());
		return "listFiles";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}