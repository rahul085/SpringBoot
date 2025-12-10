package com.rahul.JavaMail.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedEmail{

    private String from;
    private String subject;
    private String body;
    private Date sentDate;
    private List<String> attachmentFileNames;
}
