package com.example.assessment.service;

import com.example.assessment.dto.model.FolderDto;
import com.example.assessment.dto.request.FolderRequest;
import com.example.assessment.model.Item;
import com.example.assessment.model.ItemType;
import com.example.assessment.model.PermissionLevel;
import com.example.assessment.repository.ItemRepository;
import com.example.assessment.repository.PermissionRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final ItemRepository itemRepository;
    private final PermissionRepository permissionRepository;

    public Item createFolder(FolderRequest folderRequest) throws Exception {

        var userPermission = permissionRepository.findByUserEmail(folderRequest.getUserEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + folderRequest.getUserEmail()));

        var space = itemRepository.findByName(folderRequest.getSpaceName())
                .orElseThrow(() -> new EntityNotFoundException("Space not found with name: " + folderRequest.getSpaceName()));

        if (!space.getPermissionGroup().getId().equals(userPermission.getGroup().getId())) {
            throw new AccessDeniedException("User does not have permission to this space.");
        }

        if (!userPermission.getPermissionLevel().equals(PermissionLevel.EDIT)) {
            throw new AccessDeniedException("User does not have permission to create folders in this space.");
        }

        var folder = itemRepository.findByNameAndParentId(folderRequest.getName(), space.getId());

        if (folder.isPresent())
            throw new EntityExistsException("Folder with the same name already exists in this space.");

        var folderItem = new Item();
        folderItem.setType(ItemType.FOLDER);
        folderItem.setName(folderRequest.getName());
        folderItem.setParentId(space.getId());
        folderItem.setPermissionGroup(userPermission.getGroup());
        itemRepository.save(folderItem);

        return folderItem;
    }

    public FolderDto mapItemToDto(Item folderItem) {
        var folder = new FolderDto();
        folder.setName(folderItem.getName());
        folder.setSpaceId(folderItem.getParentId());
        return folder;
    }

}
