package com.example.demo.resource;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.model.CatalogItem;
import com.example.demo.model.Movie;
import com.example.demo.model.Rating;
import com.example.demo.model.UserRating;
import com.example.demo.services.MovieInfo;
import com.example.demo.services.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;
    
    @Autowired
    MovieInfo movieInfo;
    
    @Autowired
    UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")
    
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating userRating = userRatingInfo.getUserRating(userId);

        return userRating.getRatings().stream()
                .map(rating -> movieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());
    }
    
    
    /*public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId){
    	return Arrays.asList(new CatalogItem("No movie", "", 0));
    }*/
    

}
/*Movie movie = webClientBuilder.build()
.get()
.uri("http://localhost:8082/movies/"+rating.getMovieId())
.retrieve()
.bodyToMono(Movie.class)
.block();*/