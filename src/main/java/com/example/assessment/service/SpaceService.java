package com.example.assessment.service;

import com.example.assessment.dto.model.SpaceDto;
import com.example.assessment.dto.request.SpaceRequest;
import com.example.assessment.model.Item;
import com.example.assessment.model.ItemType;
import com.example.assessment.model.PermissionGroup;
import com.example.assessment.repository.ItemRepository;
import com.example.assessment.repository.PermissionGroupRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceService {

    private final  PermissionGroupRepository permissionGroupRepository;
    private final ItemRepository itemRepository;

    public Item createSpace(SpaceRequest request) {

        PermissionGroup adminGroup = permissionGroupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Permission group not found with id: " + request.getGroupId()));

        var space = itemRepository.findByName(request.getName());

        if (space.isPresent())
            throw new EntityExistsException("Space with the same name already exists.");

        var spaceItem = new Item();
        spaceItem.setPermissionGroup(adminGroup);
        spaceItem.setName(request.getName());
        spaceItem.setType(ItemType.SPACE);

        itemRepository.save(spaceItem);
        return spaceItem;
    }

    public SpaceDto mapItemToDto(Item spaceItem) {

        var space = new SpaceDto();
        space.setId(spaceItem.getId());
        space.setName(spaceItem.getName());
       // space.setPermissionGroupId(spaceItem.getPermissionGroup().getId());

        return space;
    }

}
