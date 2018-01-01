package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.LabelDto;
import com.tinnguyen263.mykanban.exceptions.DataMissingException;
import com.tinnguyen263.mykanban.exceptions.NoAccessPermissionException;
import com.tinnguyen263.mykanban.model.Label;
import com.tinnguyen263.mykanban.model.Project;
import com.tinnguyen263.mykanban.service.AuthorizationService;
import com.tinnguyen263.mykanban.service.LabelService;
import com.tinnguyen263.mykanban.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/labels")
public class LabelController {

    private final LabelService labelService;
    private final ProjectService projectService;
    private final AuthorizationService authorizationService;

    @Autowired
    public LabelController(LabelService labelService, ProjectService projectService, AuthorizationService authorizationService) {
        this.labelService = labelService;
        this.projectService = projectService;
        this.authorizationService = authorizationService;
    }

    // get label
    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public LabelDto getLabel(@PathVariable Integer labelId,
                             Principal principal) throws NoAccessPermissionException {
        Label label = labelService.findByKey(labelId);
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), label.getProject().getId()))
            throw new NoAccessPermissionException();

        return new LabelDto(labelService.findByKey(labelId));
    }

    // add label
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addLabel(@RequestBody LabelDto labelDto,
                         Principal principal) throws Exception {
        if (labelDto.getProjectId() == null) {
            throw new DataMissingException("No Project ID specified !");
        }
        Project project = projectService.findByKey(labelDto.getProjectId());
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), project.getId()))
            throw new NoAccessPermissionException();

        Label label = Converter.toEntity(labelDto);
        label.setProject(project);

        labelService.saveOrUpdate(label);
    }

    // edit label
    @RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
    public void editLabel(@PathVariable Integer labelId,
                          @RequestBody LabelDto labelDto,
                          Principal principal) throws Exception {
        Label label = labelService.findByKey(labelId);

        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), label.getProject().getId()))
            throw new NoAccessPermissionException();

        // only 2 filed can be changed
        label.setName(labelDto.getName());
        label.setColor(labelDto.getColor());

        labelService.saveOrUpdate(label);
    }

    // delete label
    @RequestMapping(value = "/{labelId}", method = RequestMethod.DELETE)
    public void deleteLabel(@PathVariable Integer labelId,
                            Principal principal) throws NoAccessPermissionException {
        Label label = labelService.findByKey(labelId);

        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), label.getProject().getId()))
            throw new NoAccessPermissionException();

        labelService.deleteByKey(labelId);
    }
}
