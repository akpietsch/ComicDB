package webComic.service;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import webComic.data.Comic;



/**
 * Parses Our Comic List with Jackson or Univocity depending on chosen init
 * 
 * @param comic
 * 
 * @return
 * 
 * @throws JsonMappingException
 * @throws JsonParseException
 * @throws IOException
 */

@Service
public class JsonImport {
	
	private List<Comic> comicList;
	private Set<String> titleSet;
	private int idCounter;
	
	public JsonImport()  throws JsonParseException, JsonMappingException, IOException {
//		initJson();
		initTsv();
	}
	
	public void initJson() throws JsonParseException, JsonMappingException, IOException {
		titleSet = new TreeSet<String>();
		comicList=new ArrayList<Comic>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(new File("src/main/resources/Comics.json"));
		Comic comic = null;
		JsonNode jsonNode = root.path("Comic");

		if (jsonNode.isArray()) {
			
			idCounter = 1;
			
			for (JsonNode node : jsonNode) {
				
				comic = new Comic(idCounter, 
									node.path("Title").asText(), 
									node.path("Issue #").asInt(), 
									node.path("Box #").asInt(), 
									node.path("Publisher").asText(), 
									node.path("Comments").asText());				
				comicList.add(comic);
				
				idCounter++;
				
				
				titleSet.add(node.path("Title").asText());

			}

		}
	}
	
	public void initTsv() throws FileNotFoundException {
		titleSet = new TreeSet<String>();
		comicList = new ArrayList<Comic>();
		
		
		TsvParserSettings settings = new TsvParserSettings();
		settings.getFormat().setLineSeparator("\n");
		settings.setNullValue("-");
	    TsvParser parser = new TsvParser(settings);

	    // call beginParsing to read records one by one, iterator-style.
	    parser.beginParsing(new FileReader(new File("src/main/resources/Comics.txt")));

	    idCounter = 0;
	    Comic comic = null;
	    String[] row;
	    while ((row = parser.parseNext()) != null) {
	    	//um die erste Zeile mit Überschriften zu überspringen
        	if (idCounter!=0) {
        		if (row[1] == "-") row[1] = "0";
        		if (row[2] == "-") row[2] = "0";
        		comic = new Comic(idCounter,
        				row[0],
        				Integer.parseInt(row[1]),
        				Integer.parseInt(row[2]),
        				row[3],
        				row[4]);		
        		comicList.add(comic);
        		titleSet.add(row[0]);
        	}
        	idCounter++;
	    }

	    parser.stopParsing();
	}
	
	public void addComic(Comic comic) {
		Comic tempcomic = new Comic(idCounter,
				comic.title,
				comic.issue,
				comic.box,
				comic.publisher,
				comic.comment);
		idCounter++;
		comicList.add(tempcomic);
		titleSet.add(tempcomic.title);
		//hier könnte man Änderungen in die TSV schreiben
	}
	
	public String deleteComic(int id) {
		String output = "Ein Comic mit ID " + id + " konnte nicht gefunden werden.";
		for (int i = 0; i < comicList.size(); i++) {
			if (comicList.get(i).id == id) {
				output = comicList.get(i).title + " #"+comicList.get(i).issue + " wurde erfolgreich gelöscht.";
				comicList.remove(i);
				return output;
			}
		}
		return output;
	}
	
	public Comic getComicById(int id) {
		for (Comic comic : comicList) {
		    if (comic.id==id)
		    	return comic;
		}
		return new Comic();
	}
	
	public List<Comic> getIssuesByTitle(String searchTitle) {
		String searchTitleSanitized = searchTitle.replaceAll("%20", " ");
		List<Comic> searchResults = new ArrayList<Comic>();
		for (Comic comic : comicList) {
		    if (comic.title.equals(searchTitleSanitized))
		    	searchResults.add(comic);
		}
		return searchResults;
	}
	
	public List<Comic> getComicList() {
		return comicList;
	}
	
	public Set<String> getTitleSet() {
		return titleSet;
	}

	public void addComicImgById(int id, String path) {
		for (Comic comic : comicList) {
		    if (comic.id==id) {
		    	comic.setImgurl(path);
		    	break;
		    }
		}
	}
}
