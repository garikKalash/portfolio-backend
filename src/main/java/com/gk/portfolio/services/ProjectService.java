package com.gk.portfolio.services;

import com.gk.portfolio.entities.Project;
import com.gk.portfolio.models.ProjectModel;
import com.gk.portfolio.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

import static java.lang.String.format;

@Service public class ProjectService {
    @Autowired ProjectRepository projectRepository;


    public List<Project> getAll(){
        return projectRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Project> getByType(String type, boolean noTechPerson){
        if (noTechPerson) {
            return projectRepository.findNonTechByType(type);
        } else {
            return projectRepository.findFullByType(type);
        }
    }

    public String getTechDescription(String name) {
        Project project = projectRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(format("Project with name %s not found.", name)));
        return project.getTechDescription();
    }

    public Project save(ProjectModel projectModel) {
        Project project = new Project();
        project.setId(projectModel.id);
        project.setName(projectModel.name);
        project.setDescription(projectModel.description);
        project.setTechDescription(projectModel.techDescription);
        project.setProjectType(projectModel.projectType);

        return projectRepository.save(project);
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
}
