package com.example.assessment.dto.request;

import lombok.Data;

@Data
public class FileRequest {

    private String userEmail;

    private String folderName;

    private String name;

    private String binary;


}
