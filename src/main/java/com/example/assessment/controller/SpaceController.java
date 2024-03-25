package com.example.assessment.controller;

import com.example.assessment.dto.model.SpaceDto;
import com.example.assessment.dto.request.SpaceRequest;
import com.example.assessment.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spaces")
public class SpaceController {

    private final SpaceService spaceService;

    @PostMapping
    public ResponseEntity<SpaceDto> createSpace(@RequestBody SpaceRequest spaceRequest) {
        var spaceItem = spaceService.createSpace(spaceRequest);

        return new ResponseEntity<>(spaceService.mapItemToDto(spaceItem), HttpStatus.CREATED);
    }

}