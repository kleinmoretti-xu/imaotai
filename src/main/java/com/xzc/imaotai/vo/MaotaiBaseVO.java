package com.xzc.imaotai.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Klein Moretti
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaotaiBaseVO<T> implements Serializable {

    private Integer code;

    private T data;
}
