package br.edu.uepb.diario.repositories;

import br.edu.uepb.diario.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    
}