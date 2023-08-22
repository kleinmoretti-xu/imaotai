package com.xzc.imaotai.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaotaiSessionVO implements Serializable {

    private Integer sessionId;

    private List<MaotaiItemVO> itemList;
}
