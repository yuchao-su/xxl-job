package com.xxl.job.executor.model;

import lombok.Data;

@Data
public class SendMailReq {

    private String personal;
    private String[] sendTo;
    private String subject;
    private String content;


}
