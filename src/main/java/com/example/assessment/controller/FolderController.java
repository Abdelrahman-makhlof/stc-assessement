package com.example.assessment.controller;

import com.example.assessment.dto.model.FolderDto;
import com.example.assessment.dto.request.FolderRequest;
import com.example.assessment.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folders")
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<FolderDto> createFolder(@RequestBody FolderRequest folderRequest) throws Exception {
        var folderItem = folderService.createFolder(folderRequest);

        return new ResponseEntity<>(folderService.mapItemToDto(folderItem), HttpStatus.CREATED);
    }

}