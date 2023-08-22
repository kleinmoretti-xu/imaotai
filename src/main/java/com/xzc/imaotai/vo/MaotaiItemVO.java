package com.xzc.imaotai.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaotaiItemVO implements Serializable {

    private String itemCode;

    private String picture;

    private String pictureV2;

    private String title;

    private String content;

    private String jumpUrl;
}
