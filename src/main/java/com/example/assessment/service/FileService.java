package com.example.assessment.service;

import com.example.assessment.dto.model.FileDto;
import com.example.assessment.dto.model.FileMetadataDto;
import com.example.assessment.dto.request.FileRequest;
import com.example.assessment.model.File;
import com.example.assessment.model.Item;
import com.example.assessment.model.ItemType;
import com.example.assessment.repository.FileRepository;
import com.example.assessment.repository.ItemRepository;
import com.example.assessment.repository.PermissionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class FileService {

    private final ItemRepository itemRepository;
    private final FileRepository fileRepository;
    private final PermissionRepository permissionRepository;
    private final EntityManager entityManager;

    public File createFile(FileRequest fileRequest) {

        var folder = itemRepository.findByName(fileRequest.getFolderName())
                .orElseThrow(() -> new EntityNotFoundException("Folder not found with name: " + fileRequest.getFolderName()));

        var currentFile = itemRepository.findByNameAndParentId(fileRequest.getName(), folder.getId());

        if (currentFile.isPresent())
            throw new EntityNotFoundException("File with the same name already exists in this folder");

        var userPermission = permissionRepository.findByUserEmail(fileRequest.getUserEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + fileRequest.getUserEmail()));

        var fileItem = new Item();
        fileItem.setType(ItemType.FILE);
        fileItem.setName(fileRequest.getName());
        fileItem.setPermissionGroup(userPermission.getGroup());
        fileItem.setParentId(folder.getId());

        var file = new File();
        file.setItem(fileItem);
        if (fileRequest.getContent() != null)
            file.setBinary(fileRequest.getContent().getBytes());
        fileItem = itemRepository.save(fileItem);
        file.setId(fileItem.getId());
        fileRepository.save(file);

        return file;

    }

    public FileDto mapItemToDto(File file) {
        var fileDto = new FileDto();
        fileDto.setName(file.getItem().getName());
        fileDto.setFolderId(file.getItem().getParentId());

        return fileDto;
    }

    public FileMetadataDto getFileMetadata(Long fileId, String userEmail) throws Exception {

        var file = fileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("File not found with id: " + fileId));

        var userPermission = permissionRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));

        if (!userPermission.getGroup().equals(file.getItem().getPermissionGroup()))
            throw new AccessDeniedException("User does not have permission to this file.");

        Query query = entityManager.createNativeQuery("SELECT F.file_id, I.name , it.name as folder, LENGTH(F.BINARY_DATA) as size FROM FILE F Join Item I on F.ITEM_ID=I.ITEM_ID join Item it on i.parent_id=it.item_id WHERE F.FILE_ID = :fileId");
        query.setParameter("fileId", fileId);

        try {
            Object[] result = (Object[]) query.getSingleResult();
            FileMetadataDto metadata = new FileMetadataDto();
            metadata.setId(((Integer) result[0]).longValue());
            metadata.setName((String) result[1]);
            metadata.setFolder((String) result[2]);
            metadata.setSize(((Integer) result[3]).longValue());

            return metadata;

        } catch (NoResultException e) {
            throw new Exception("File not found.");
        }
    }

    public File getFile(Long fileId, String userEmail) throws Exception {
        var file = fileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("File not found with id: " + fileId));

        var userPermission = permissionRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));

        if (!userPermission.getGroup().equals(file.getItem().getPermissionGroup()))
            throw new AccessDeniedException("User does not have permission to this file.");

        return file;
    }

}
