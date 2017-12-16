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

@RestController
@RequestMapping("/api/columns")
public class ColumnController {

    private final MColumnService mColumnService;
    private final ProjectService projectService;
    private final AuthorizationService authorizationService;

    @Autowired
    public ColumnController(MColumnService mColumnService, ProjectService projectService, AuthorizationService authorizationService) {
        this.mColumnService = mColumnService;
        this.projectService = projectService;
        this.authorizationService = authorizationService;
    }

    // get column
    @RequestMapping(value = "/{columnId}", method = RequestMethod.GET)
    public MColumnDto getColumn(@PathVariable Integer columnId,
                                Principal principal) throws NoAccessPermissionException {
        String username = Utils.getUsernameFromPrincipal(principal);
        MColumn column = mColumnService.findByKey(columnId);
        Project project = column.getProject();

        if (!project.getPublic() && !authorizationService.userCanAccessProject(username, project.getId()))
            throw new NoAccessPermissionException();

        return new MColumnDto(column);
    }

    // add column
    @RequestMapping(value = "", method = RequestMethod.POST)
    public MColumnDto addColumn(@RequestBody MColumnDto mColumnDto,
                                Principal principal) throws Exception {
        String username = Utils.getUsernameFromPrincipal(principal);

        // check projectId field
        if (mColumnDto.getProjectId() == null) {
            throw new Exception();  // TODO: no project specified
        }

        // check project
        Project project = projectService.findByKey(mColumnDto.getProjectId());
        if (project == null)
            throw new EntityNotFoundException();    // TODO: adding column to undefined project

        // check permission on project
        if (!authorizationService.userCanAccessProject(username, project.getId()))
            throw new NoAccessPermissionException();

        MColumn column = Converter.toEntity(mColumnDto);
        column.setProject(project);
        column = mColumnService.saveOrUpdate(column);

        return new MColumnDto(column);
    }

    // edit MColumn
    @RequestMapping(value = "/{columnId}", method = RequestMethod.PUT)
    public void editMColumn(@PathVariable Integer columnId,
                            @RequestBody MColumnDto mColumnDto,
                            Principal principal) throws NoAccessPermissionException {
        String username = Utils.getUsernameFromPrincipal(principal);
        MColumn column = mColumnService.findByKey(columnId);
        Project project = column.getProject();

        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), project.getId()))
            throw new NoAccessPermissionException();

        column.setName(mColumnDto.getName());
        column.setDescription(mColumnDto.getDescription());
        column.setCardLimit(mColumnDto.getCardLimit());
        mColumnService.saveOrUpdate(column);

        // change column order
        if (column.getDisplayOrder() != mColumnDto.getDisplayOrder()) {
            int src = column.getDisplayOrder();
            int des = mColumnDto.getDisplayOrder();

            ArrayList<MColumn> mColumns = (ArrayList<MColumn>) mColumnService.getColumnsOfProject(project.getId());
            mColumns.add(des, mColumns.remove(src));

            // re-arrange columns
            for (int i = 0; i < mColumns.size(); i++) {
                mColumns.get(i).setDisplayOrder(i);
                // save
                mColumnService.saveOrUpdate(mColumns.get(i));
            }
        }
    }

    // delete MColumn
    @RequestMapping(value = "/{columnId}", method = RequestMethod.DELETE)
    public void deleteMColumn(@PathVariable Integer columnId,
                              Principal principal) throws NoAccessPermissionException {
        String username = Utils.getUsernameFromPrincipal(principal);
        MColumn column = mColumnService.findByKey(columnId);
        Project project = column.getProject();

        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), project.getId()))
            throw new NoAccessPermissionException();

        mColumnService.deleteByKey(columnId);
    }


}
