package com.liberymutual.goforcode.wimp.api;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liberymutual.goforcode.wimp.models.Actor;
import com.liberymutual.goforcode.wimp.models.Movie;
import com.liberymutual.goforcode.wimp.repositories.ActorRepository;
import com.liberymutual.goforcode.wimp.repositories.MovieRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController

@RequestMapping("/api/movies")
@Api(description="Use this to get and create movies and add actors to movies.")
public class MovieApiController {

	private MovieRepository movieRepo;
	private ActorRepository actorRepo;

	public MovieApiController(MovieRepository movieRepo, ActorRepository actorRepo) {
		this.movieRepo = movieRepo;
		this.actorRepo = actorRepo;

		this.movieRepo = movieRepo;
		Movie movie = new Movie();
		movie.setTitle("Blade Runner");
		movie.setDistributor("Warner Bros");
		movie.setActors(actorRepo.findAll());
		movieRepo.save(movie);
	}
	
	@ApiOperation(value="Find actors by movie ID.", notes="You only need to add into the browser /ID.")
	@PostMapping("{movieId}/actors")
	public Movie accociateAnActor(@PathVariable long movieId, @RequestBody Actor actor) {
		Movie movie = movieRepo.findOne(movieId);
		actor = actorRepo.findOne(actor.getId());
		
		movie.addActor(actor);
		movieRepo.save(movie);
		
		return movie;
		
	}
	
	@ApiOperation(value="Returns a list of movies.")
	@GetMapping("")
	public List<Movie> getAll() {
		return movieRepo.findAll();
	}
	
	@ApiOperation(value="Selects a movie by ID.")
	@GetMapping("{id}")
	public Movie getOne(@PathVariable long id) throws StuffNotFoundException{
		Movie movie = movieRepo.findOne(id);
		if (movie == null) {
			throw new StuffNotFoundException();
		}
		return movie;
	}

	@ApiOperation(value="Delete a movie by ID.")
	@DeleteMapping("{id}")
	public Movie delete(@PathVariable long id) {
		try {
			Movie movie = movieRepo.findOne(id);
			movieRepo.delete(id);
			return movie;
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	@ApiOperation(value="Creates a movie.")
	@PostMapping("")
	public Movie create(@RequestBody Movie movie) {
		return movieRepo.save(movie);
	}

	@ApiOperation(value="Updates a movie.")
	@PutMapping("{id}")
	public Movie update(@RequestBody Movie movie, @PathVariable long id) {
		movie.setId(id);
		return movieRepo.save(movie);
	}
}
