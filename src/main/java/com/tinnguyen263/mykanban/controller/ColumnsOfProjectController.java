package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.MColumnDto;
import com.tinnguyen263.mykanban.exceptions.NoAccessPermissionException;
import com.tinnguyen263.mykanban.model.MColumn;
import com.tinnguyen263.mykanban.model.Project;
import com.tinnguyen263.mykanban.service.AuthorizationService;
import com.tinnguyen263.mykanban.service.MColumnService;
import com.tinnguyen263.mykanban.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/projects/{projectId}/columns")
public class ColumnsOfProjectController {

    @Autowired
    private MColumnService mColumnService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private AuthorizationService authorizationService;


    // get column list of project
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<MColumnDto> getColumns(@PathVariable Integer projectId,
                                             Principal principal) throws NoAccessPermissionException {
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), projectId))
            throw new NoAccessPermissionException();

        Collection<MColumn> mColumns = mColumnService.getColumnsOfProject(projectId);   // get sorted columns
        Collection<MColumnDto> mColumnDtos = new ArrayList<>();
        for (MColumn MColumn : mColumns) {
            mColumnDtos.add(new MColumnDto(MColumn));
        }

        return mColumnDtos;
    }

    // add column to project
    @RequestMapping(value = "", method = RequestMethod.POST)
    public MColumnDto addColumn(@PathVariable int projectId,
                                @RequestBody MColumnDto mColumnDto,
                                Principal principal) throws NoAccessPermissionException {
        String username = Utils.getUsernameFromPrincipal(principal);

        Project project = projectService.findByKey(mColumnDto.getProjectId());
        if (project == null)
            throw new EntityNotFoundException();    // TODO: ERROR: adding column to undefined project

        if (!authorizationService.userCanAccessProject(username, project.getId()))
            throw new NoAccessPermissionException();

        mColumnDto.setProjectId(projectId); // override request body projectId
        MColumn column = Converter.toEntity(mColumnDto);
        column.setProject(project);
        column = mColumnService.saveOrUpdate(column);

        return new MColumnDto(column);
    }
}
