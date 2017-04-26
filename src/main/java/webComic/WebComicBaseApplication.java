package webComic;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import webComic.storage.StorageService;

/**
 *  Startet Webapplikation und bindet die lokale Datenbank ein.
 *  Datenbank wird beim Start der Applikation gelöscht.
 */
@SpringBootApplication
public class WebComicBaseApplication implements CommandLineRunner {

	@Resource
	StorageService storageService;


	public static void main(String[] args) {
		SpringApplication.run(WebComicBaseApplication.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
		storageService.init();
	}
}
