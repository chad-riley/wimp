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
import com.liberymutual.goforcode.wimp.models.Movie;
import com.liberymutual.goforcode.wimp.repositories.ActorRepository;
import com.liberymutual.goforcode.wimp.repositories.MovieRepository;

public class MoviesApiControllerTests {

	private ActorRepository actorRepo;
	private MovieRepository movieRepo;
	private MovieApiController controller;

	@Before
	public void setUp() {
		movieRepo = mock(MovieRepository.class);
		actorRepo = mock(ActorRepository.class);
		controller = new MovieApiController(movieRepo, actorRepo);

	}

	@Test
	public void test_all_gets_and_sets() {
		new BeanTester().testBean(Movie.class);
	}

	@Test
	public void test_getAllArrayList_all_movies_in_the_report() {
		// Arrange
		ArrayList<Movie> movies = new ArrayList<Movie>();
		ArrayList<Actor> actors = new ArrayList<Actor>();
		movies.add(new Movie());
		movies.add(new Movie());

		when(movieRepo.findAll()).thenReturn(movies);

		// Act
		List<Movie> actualMovie = controller.getAll();

		// Assert
		assertThat(actualMovie.size()).isEqualTo(2);
		assertThat(actualMovie.get(0)).isSameAs(movies.get(0));
		verify(movieRepo).findAll();

	}

	@Test
	public void test_associate_actor_with_a_movie() {
		// Arrange
		Actor ridley = new Actor();
		Movie lastJedi = new Movie();
		ridley.setId(8l);
		when(movieRepo.findOne(9l)).thenReturn(lastJedi);
		when(actorRepo.findOne(8l)).thenReturn(ridley);

		// Act
		Movie actualMovie = controller.accociateAnActor(9l, ridley);

		// Assert
		// verify movieRepo.find
		// verify actorRepo.find
		// verify movieRepo.save
		// assert that ridley is an actor in lastJedi
		// assert that actualMovie is lastJedi

		verify(movieRepo).findOne(9l);
		verify(actorRepo).findOne(8l);
		verify(movieRepo).save(lastJedi);
		assertThat(lastJedi.getActors()).contains(ridley);
	}

	@Test
	public void test_getOne_returns_movie_returned_from_report() throws StuffNotFoundException {
		// Arrange
		Movie test = new Movie();
		when(movieRepo.findOne(9l)).thenReturn(test);

		// Act
		Movie actualMovie = controller.getOne(9l);

		// Assert
		assertThat(actualMovie).isSameAs(test);
		verify(movieRepo).findOne(9l);

	}

	@Test
	public void test_getOne_throws_not_found_exception_when_no_actor_returned_from_repo() {
		try {
			controller.getOne(3l);
			fail("The Controller did not throw the excpetion");
		} catch (StuffNotFoundException snfe) {
		}

	}

	@Test
	public void test_delete_returns_movie_and_deletes_when_found() {
		// Arrange
		Movie movie = new Movie();
		when(movieRepo.findOne(5l)).thenReturn(movie);

		// Act
		Movie actual = controller.delete(5l);

		// Assert
		assertThat(actual).isSameAs(movie);
		verify(movieRepo).delete(5l);
		verify(movieRepo).findOne(5l);

	}

	@Test
	public void test_null_is_returned_when_FindOne_throws_StuffNotFoundException() {
		// Arrange
		when(movieRepo.findOne(6l)).thenThrow(new EmptyResultDataAccessException(0));
		// Act
		Movie actual = controller.delete(6l);

		// Assert
		assertThat(actual).isNull();
		verify(movieRepo).findOne(6l);
	}

	@Test
	public void test_create_method_adds_a_movie_record() {
		// Arrange
		Movie movie = new Movie();
		when(movieRepo.save(movie)).thenReturn(movie);

		// Act
		Movie actual = controller.create(movie);

		// Assert
		assertThat(actual).isSameAs(movie);
		verify(movieRepo).save(movie);
	}

	@Test
	public void test_update_method_modifies_a_movie() {
		// Arrange
		Movie movie = new Movie();
		movie.setId(3l);
		when(movieRepo.save(movie)).thenReturn(movie);

		// Act
		Movie actual = controller.update(movie, 3l);

		// Assert
		assertThat(actual).isSameAs(movie);
		assertThat(actual.getId()).isSameAs(movie.getId());
		verify(movieRepo).save(movie);

	}
}
