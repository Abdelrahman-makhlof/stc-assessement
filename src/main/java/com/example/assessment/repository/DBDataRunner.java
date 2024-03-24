package com.example.assessment.repository;

import com.example.assessment.model.Permission;
import com.example.assessment.model.PermissionGroup;
import com.example.assessment.model.PermissionLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DBDataRunner implements CommandLineRunner {

    private final PermissionGroupRepository permissionGroupRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {

        var permissionGroup = new PermissionGroup();
        permissionGroup.setGroupName("admin");

        permissionGroupRepository.save(permissionGroup);
        permissionRepository.saveAll(Arrays.asList(
                new Permission(1L, "ahmed@stc.com", PermissionLevel.VIEW, permissionGroup),
                new Permission(2L, "ali@stc.com", PermissionLevel.EDIT, permissionGroup)
        ));
    }

}
