package com.liberymutual.goforcode.wimp.api;

import static org.assertj.core.api.Assertions.*;
import org.meanbean.test.BeanTester;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import com.liberymutual.goforcode.wimp.models.Actor;
import com.liberymutual.goforcode.wimp.repositories.ActorRepository;

public class ActorsApiControllerTests {

	private ActorRepository actorRepo;
	private ActorApiController controller;

	@Before
	public void setUp() {
		actorRepo = mock(ActorRepository.class);
		controller = new ActorApiController(actorRepo);
	}

	@Test
	public void test_getAllArrayList_all_actors_in_the_report() {
		// Arrange
		ArrayList<Actor> actors = new ArrayList<Actor>();
		actors.add(new Actor());
		actors.add(new Actor());

		when(actorRepo.findAll()).thenReturn(actors);
		
		

		// Act
		List<Actor> actual = controller.getAll();

		// Assert
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(0)).isSameAs(actors.get(0));
		verify(actorRepo).findAll();
	}

	@Test
	public void test_all_gets_and_sets() {
		new BeanTester().testBean(Actor.class);
	}

	@Test
	public void test_getOne_returns_actor_returned_from_report() throws StuffNotFoundException {
		// Arrange
		Actor hauer = new Actor();
		when(actorRepo.findOne(2l)).thenReturn(hauer);

		// Act
		Actor actual = controller.getOne(2l);

		// Assert
		assertThat(actual).isSameAs(hauer);
		verify(actorRepo).findOne(2l);

	}

	@Test
	public void test_getOne_throws_not_found_exception_when_no_actor_returned_from_repo() {
		// Arrange
		try {
			controller.getOne(2);
			fail("The Controller did not throw the excpetion");
		} catch (StuffNotFoundException snfe) {
		}

	}

	@Test
	public void test_delete_returns_actors_and_deletes_when_found() {
		// Arrange
		Actor actor = new Actor();
		when(actorRepo.findOne(5l)).thenReturn(actor);

		// Act
		Actor actual = controller.delete(5l);

		// Assert
		assertThat(actual).isSameAs(actor);
		verify(actorRepo).delete(5l);
		verify(actorRepo).findOne(5l);

	}

	@Test
	public void test_null_is_returned_when_FindOne_throws_StuffNotFoundException() {

		// Arrange
		when(actorRepo.findOne(6l)).thenThrow(new EmptyResultDataAccessException(0));

		// Act
		Actor actual = controller.delete(6l);

		// Assert

		assertThat(actual).isNull();
		verify(actorRepo).findOne(6l);
	}

	@Test
	public void test_create_method_adds_an_actor_record() {
		// Arrange
		Actor robie = new Actor();
		when(actorRepo.save(robie)).thenReturn(robie);

		// Act
		Actor actual = controller.create(robie);

		// Assert
		assertThat(actual).isSameAs(robie);
		verify(actorRepo).save(robie);
	}

	@Test
	public void test_update_method_modifies_an_actor() {
		// Arrange
		Actor actor = new Actor();
		actor.setId(3l);
		when(actorRepo.save(actor)).thenReturn(actor);

		// Act
		Actor actual = controller.update(actor, 3l);

		// Assert
		assertThat(actual).isSameAs(actor);
		assertThat(actual.getId()).isSameAs(actor.getId());
		verify(actorRepo).save(actor);

	}
}
