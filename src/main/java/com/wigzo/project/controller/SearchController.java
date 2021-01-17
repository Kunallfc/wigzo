package com.wigzo.project.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wigzo.project.model.SearchResults;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	RestTemplate restTemplate;

	@PostMapping
	public String searchResults(@RequestParam(name = "keyword") String keyword,RedirectAttributes redirectAttributes) {

		HttpHeaders headers = new HttpHeaders();
		String uri = "https://en.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + keyword
				+ "&utf8=&format=json";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("action", "query")
				.queryParam("list", "search").queryParam("srsearch", keyword).queryParam("utf8", "")
				.queryParam("format", "json");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				String.class);

		JsonObject jsonObject = new JsonParser().parse(response.getBody().toString()).getAsJsonObject();
		// model.addAttribute("attribute", "redirectWithRedirectPrefix");
		
		JsonObject query = jsonObject.get("query").getAsJsonObject();//.get("search").getAsJsonObject();
		JsonArray search = (JsonArray) query.get("search");
		
		List<SearchResults> model = new LinkedList<>();
		for(int i=0;i<search.size();i++) {
			JsonObject queryResult = search.get(i).getAsJsonObject();
			String title = queryResult.get("title").toString().replace("\"", "");
			SearchResults SearchResults = new SearchResults(title,queryResult.get("snippet").toString(),"https://en.wikipedia.org/wiki/"+title); 
			model.add(SearchResults);
		}
		redirectAttributes.addFlashAttribute("search", model);
		return "redirect:searchResult";

	}

}
