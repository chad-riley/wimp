package com.liberymutual.goforcode.wimp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liberymutual.goforcode.wimp.models.Actor;

@Repository

public interface ActorRepository extends JpaRepository<Actor, Long>{

}
