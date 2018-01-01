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
@RequestMapping("/api/projects/{projectId}/labels")
public class LabelsOfProjectController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AuthorizationService authorizationService;

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

//    // add label
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public void addLabel(@PathVariable Integer projectId,
//                         @RequestBody LabelDto labelDto,
//                         Principal principal) throws NoAccessPermissionException {
//        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), projectId))
//            throw new NoAccessPermissionException();
//
//        labelDto.setProjectId(projectId);
//        Label label = Converter.toEntity(labelDto);
//        label.setProject(projectService.findByKey(projectId));
//        labelService.saveOrUpdate(label);
//    }
}
