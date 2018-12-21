package com.apiserver.model.system.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @author jarvis
 */
@Data
public class SUsersResult {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String imageUrl;
    private String chineseName;
    private String nickname;
    private String email;
    private String mobile;
    private String sex;
    private Date birthday;
    private String nationality;
    private String province;
    private String cities;
    private String address;
    private Integer idType;
    private String idNumber;
    private String education;
    private Integer age;
    private String household;
    private String ethnic;
    private String blood;
    private String career;
}
