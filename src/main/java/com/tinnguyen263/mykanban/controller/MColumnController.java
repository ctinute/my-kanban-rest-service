package com.tinnguyen263.mykanban.controller;

import com.tinnguyen263.mykanban.controller.dtos.MColumnDto;
import com.tinnguyen263.mykanban.exceptions.NoAccessPermissionException;
import com.tinnguyen263.mykanban.model.MColumn;
import com.tinnguyen263.mykanban.service.AuthorizationService;
import com.tinnguyen263.mykanban.service.MColumnService;
import com.tinnguyen263.mykanban.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/projects/{projectId}/columns")
public class MColumnController {

    private final MColumnService mColumnService;
    private final ProjectService projectService;
    private final AuthorizationService authorizationService;

    @Autowired
    public MColumnController(MColumnService mColumnService, ProjectService projectService, AuthorizationService authorizationService) {
        this.mColumnService = mColumnService;
        this.projectService = projectService;
        this.authorizationService = authorizationService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<MColumnDto> getColumns(@PathVariable Integer projectId,
                                             Principal principal) throws NoAccessPermissionException {
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), projectId))
            throw new NoAccessPermissionException();

//        Collection<MColumn> mColumns = projectService.findByKey(projectId).getmColumns();
        Collection<MColumn> mColumns = mColumnService.getColumnsOfProject(projectId);
        Collection<MColumnDto> mColumnDtos = new ArrayList<>();
        for (MColumn MColumn : mColumns) {
            mColumnDtos.add(new MColumnDto(MColumn));
        }

        return mColumnDtos;
    }

    // get column
    @RequestMapping(value = "/{columnId}", method = RequestMethod.GET)
    public MColumnDto getColumns(@PathVariable Integer projectId,
                                 @PathVariable Integer columnId,
                                 Principal principal) throws NoAccessPermissionException {
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), projectId))
            throw new NoAccessPermissionException();

        return new MColumnDto(mColumnService.findByKey(columnId));
    }

    // add MColumn
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addColumn(@PathVariable Integer projectId,
                          @RequestBody MColumnDto mColumnDto,
                          Principal principal) throws NoAccessPermissionException {
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), projectId))
            throw new NoAccessPermissionException();
        MColumn column = toEntity(mColumnDto);
        column.setProject(projectService.findByKey(projectId));
        mColumnService.saveOrUpdate(column);
    }

    // edit MColumn
    @RequestMapping(value = "/{columnId}", method = RequestMethod.PUT)
    public void editMColumn(@PathVariable Integer projectId,
                            @PathVariable Integer columnId,
                            @RequestBody MColumnDto mColumnDto,
                            Principal principal) throws NoAccessPermissionException {
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), projectId))
            throw new NoAccessPermissionException();

        MColumn oMColumn = mColumnService.findByKey(columnId);
        oMColumn.setName(mColumnDto.getName());
        oMColumn.setDescription(mColumnDto.getDescription());
        oMColumn.setCardLimit(mColumnDto.getCardLimit());
        mColumnService.saveOrUpdate(oMColumn);

        // change column order
        if (oMColumn.getDisplayOrder() != mColumnDto.getDisplayOrder()) {
            int src = oMColumn.getDisplayOrder();
            int des = mColumnDto.getDisplayOrder();

            ArrayList<MColumn> mColumns = (ArrayList<MColumn>) mColumnService.getColumnsOfProject(projectId);
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
    public void deleteMColumn(@PathVariable Integer projectId,
                              @PathVariable Integer columnId,
                              Principal principal) throws NoAccessPermissionException {
        if (!authorizationService.userCanAccessProject(Utils.getUsernameFromPrincipal(principal), projectId))
            throw new NoAccessPermissionException();
        mColumnService.deleteByKey(columnId);
    }

    private MColumn toEntity(MColumnDto mColumnDto) {
        return new MColumn(mColumnDto.getName(), mColumnDto.getDescription(), mColumnDto.getDisplayOrder(), mColumnDto.getCardLimit());
    }


}
