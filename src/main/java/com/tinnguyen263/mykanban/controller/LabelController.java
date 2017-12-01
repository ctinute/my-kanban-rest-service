package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.LabelDto;
import com.tinnguyen263.mykanban.exceptions.NoAccessPermissionException;
import com.tinnguyen263.mykanban.model.Label;
import com.tinnguyen263.mykanban.service.AuthorizationService;
import com.tinnguyen263.mykanban.service.LabelService;
import com.tinnguyen263.mykanban.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(value = "/api/projects/{projectId}/labels")
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

    // get labels of project
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<LabelDto> getLabels(@PathVariable Integer projectId,
                                          Principal principal) throws NoAccessPermissionException {
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), projectId))
            throw new NoAccessPermissionException();

        Collection<Label> labels = projectService.findByKey(projectId).getLabels();
        Collection<LabelDto> labelDtos = new ArrayList<>();
        for (Label label : labels) {
            labelDtos.add(new LabelDto(label));
        }

        return labelDtos;
    }

    // get label
    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public LabelDto getLabel(@PathVariable Integer projectId,
                             @PathVariable Integer labelId,
                             Principal principal) throws NoAccessPermissionException {
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), projectId))
            throw new NoAccessPermissionException();

        return new LabelDto(labelService.findByKey(labelId));
    }

    // add label
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addLabel(@PathVariable Integer projectId,
                         @RequestBody LabelDto labelDto,
                         Principal principal) throws NoAccessPermissionException {
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), projectId))
            throw new NoAccessPermissionException();
        Label label = toEntity(labelDto);
        label.setProject(projectService.findByKey(projectId));
        labelService.saveOrUpdate(label);
    }

    // edit label
    @RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
    public void editLabel(@PathVariable Integer projectId,
                          @PathVariable Integer labelId,
                          @RequestBody LabelDto labelDto,
                          Principal principal) throws NoAccessPermissionException {
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), projectId))
            throw new NoAccessPermissionException();

        Label oLabel = labelService.findByKey(labelId);
        oLabel.setName(labelDto.getName());
        oLabel.setColor(labelDto.getColor());
        labelService.saveOrUpdate(oLabel);
    }

    // delete label
    @RequestMapping(value = "/{labelId}", method = RequestMethod.DELETE)
    public void deleteLabel(@PathVariable Integer projectId,
                            @PathVariable Integer labelId,
                            Principal principal) throws NoAccessPermissionException {
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), projectId))
            throw new NoAccessPermissionException();
        labelService.deleteByKey(labelId);
    }

    private Label toEntity(LabelDto labelDto) {
        return new Label(labelDto.getName(), labelDto.getColor());
    }

}
