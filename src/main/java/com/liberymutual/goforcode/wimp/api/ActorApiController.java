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
import com.liberymutual.goforcode.wimp.models.ActorWithMovies;
import com.liberymutual.goforcode.wimp.repositories.ActorRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/actors")
public class ActorApiController {
	
	private ActorRepository actorRepo;
	

	public ActorApiController(ActorRepository actorRepo) {
		this.actorRepo = actorRepo;
		Actor actor = new Actor();
		actor.setFirstName("Rutger");
		actor.setLastName("Hauer");
		actorRepo.save(actor);
		}
	
	@ApiOperation(value="Returns a list of actors.")
	@GetMapping("")
	public List<Actor> getAll(){
		return actorRepo.findAll();
	}
	
	@ApiOperation(value="Selects an actor by ID.")
	@GetMapping("{id}")
    public Actor getOne(@PathVariable long id) throws StuffNotFoundException{
        Actor actor = actorRepo.findOne(id);
        if (actor == null) {
            throw new StuffNotFoundException();
        }
//        ActorWithMovies newActor = new ActorWithMovies();
//        newActor.setId(actor.getId());
//        newActor.setActiveSinceYear(actor.getActiveSinceYear());
//        newActor.setBirthDate(actor.getBirthDate());
//        newActor.setFirstName(actor.getFirstName());
//        newActor.setLastName(actor.getLastName());
//        newActor.setMovies(actor.getMovies());
//        return newActor;
        return actor;
    }
	
	@ApiOperation(value="Delete an actor by ID.")
	@DeleteMapping("{id}")
	public Actor delete(@PathVariable long id) {
		try{
			Actor actor = actorRepo.findOne(id);
			actorRepo.delete(id);
			return actor;
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	
	@ApiOperation(value="Creates an actor.")
	@PostMapping("")
	public Actor create(@RequestBody Actor actor) {
		Actor savedActor = actorRepo.save(actor);
		return savedActor;
	}
	
	@ApiOperation(value="Updates an actor.")
	@PutMapping("{id}")
	public Actor update(@RequestBody Actor actor, @PathVariable long id) {
		actor.setId(id);
		return actorRepo.save(actor);
	}
}

