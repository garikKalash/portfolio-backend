package com.gk.portfolio.repositories;

import com.gk.portfolio.entities.Project;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query( value = "SELECT * FROM project u WHERE u.project_type = ?1 ORDER BY id DESC",
            nativeQuery = true)
    List<Project> findFullByType(String type);

    @Query( value = "SELECT u.id, name, description, null as tech_description, project_type " +
            "FROM project u WHERE u.project_type = ?1 ORDER BY id DESC ",
            nativeQuery = true)
    List<Project> findNonTechByType(String type);

    Optional<Project> findByName(String name);
}
