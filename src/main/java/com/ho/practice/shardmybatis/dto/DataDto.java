package com.ho.practice.shardmybatis.dto;

import lombok.Builder;

@Builder
public class DataDto {
    private Integer seq;
    private Integer mainSeq;
    private String txt;
    private String tid;
    private int isDelete;
    private int isReport;
}
