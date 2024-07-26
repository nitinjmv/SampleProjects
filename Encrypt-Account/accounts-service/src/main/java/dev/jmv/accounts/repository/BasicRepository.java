package dev.jmv.basic.repository;

import dev.jmv.basic.entity.Basic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicRepository extends JpaRepository<Basic, Integer> {
}
