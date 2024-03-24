package com.example.assessment.controller;

import com.example.assessment.dto.model.FileDto;
import com.example.assessment.dto.model.FileMetadataDto;
import com.example.assessment.dto.request.FileRequest;
import com.example.assessment.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<FileDto> createFile(@RequestBody FileRequest fileRequest) throws Exception {
        var fileItem = fileService.createFile(fileRequest);
        return new ResponseEntity<>(fileService.mapItemToDto(fileItem), HttpStatus.CREATED);

    }

    @GetMapping("/{fileId}/metadata")
    public ResponseEntity<FileMetadataDto> getFileMetadata(@PathVariable Long fileId, @RequestParam String userEmail) throws Exception {
        var fileMetadata = fileService.getFileMetadata(fileId, userEmail);
        return new ResponseEntity<>(fileMetadata, HttpStatus.OK);
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId, @RequestParam String userEmail) throws Exception {

        var file = fileService.getFile(fileId, userEmail);

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(file.getBinary().length);
        headers.setContentDispositionFormData("attachment", file.getItem().getName());

        return ResponseEntity.ok().headers(headers).body(file.getBinary());
    }

}