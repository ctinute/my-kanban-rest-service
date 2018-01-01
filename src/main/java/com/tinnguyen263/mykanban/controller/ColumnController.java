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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/columns")
public class ColumnController {

    private final MColumnService columnService;
    private final ProjectService projectService;
    private final AuthorizationService authorizationService;

    @Autowired
    public ColumnController(MColumnService columnService, ProjectService projectService, AuthorizationService authorizationService) {
        this.columnService = columnService;
        this.projectService = projectService;
        this.authorizationService = authorizationService;
    }

    // get column
    @RequestMapping(value = "/{columnId}", method = RequestMethod.GET)
    public MColumnDto getColumn(@PathVariable Integer columnId,
                                Principal principal) throws NoAccessPermissionException {
        String username = Utils.getUsernameFromPrincipal(principal);
        MColumn column = columnService.findByKey(columnId);
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
        Project project = projectService.findByKey(mColumnDto.getProjectId());
        if (!authorizationService.userCanAccessProject(username, project.getId()))
            throw new NoAccessPermissionException();

        MColumn column = Converter.toEntity(mColumnDto);
        column.setProject(project);

        // adjust other columns
        List<MColumn> columns = new ArrayList<>(project.getmColumns());
        for (MColumn c : columns) {
            if (c.getDisplayOrder() >= column.getDisplayOrder())
                c.setDisplayOrder(c.getDisplayOrder() + 1);
            columnService.saveOrUpdate(c);
        }

        return new MColumnDto(columnService.saveOrUpdate(column));
    }

    // edit MColumn
    @RequestMapping(value = "/{columnId}", method = RequestMethod.PUT)
    public MColumnDto editMColumn(@PathVariable Integer columnId,
                                  @RequestBody MColumnDto columnDto,
                                  Principal principal) throws NoAccessPermissionException {
        String username = Utils.getUsernameFromPrincipal(principal);
        MColumn column = columnService.findByKey(columnId);
        Project project = column.getProject();
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), project.getId()))
            throw new NoAccessPermissionException();

        column.setName(columnDto.getName());
        column.setDescription(columnDto.getDescription());
        column.setCardLimit(columnDto.getCardLimit());

        int src = column.getDisplayOrder();
        int des = columnDto.getDisplayOrder();
        if (des != src) {

            List<MColumn> columns = new ArrayList<>(project.getmColumns());

            if (des < 0) {
                des = 0;
            } else if (des > columns.size() - 1) {
                des = columns.size();
            }

            // update other columns
            for (MColumn c : columns) {
                int index = c.getDisplayOrder();
                if (index < src && index >= des) { // move back, move others forward
                    c.setDisplayOrder(index + 1);
                    columnService.saveOrUpdate(c);
                }
                if (index > src && index <= des) { // // move forward, move others back
                    c.setDisplayOrder(index - 1);
                    columnService.saveOrUpdate(c);
                }
            }

            column.setDisplayOrder(columnDto.getDisplayOrder());
        }

        return new MColumnDto(columnService.saveOrUpdate(column));
    }

    // delete MColumn
    @RequestMapping(value = "/{columnId}", method = RequestMethod.DELETE)
    public void deleteMColumn(@PathVariable Integer columnId,
                              Principal principal) throws NoAccessPermissionException {
        String username = Utils.getUsernameFromPrincipal(principal);
        MColumn column = columnService.findByKey(columnId);
        Project project = column.getProject();
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), project.getId()))
            throw new NoAccessPermissionException();

        // adjust other columns display order
        List<MColumn> columnList = new ArrayList<>(project.getmColumns());
        for (MColumn col : columnList) {
            if (col.getDisplayOrder() > column.getDisplayOrder()) {
                col.setDisplayOrder(col.getDisplayOrder() - 1);
                columnService.saveOrUpdate(col);
            }
        }

        columnService.deleteByKey(columnId);
    }


}
