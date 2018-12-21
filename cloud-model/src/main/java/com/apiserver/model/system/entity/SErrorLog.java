package com.apiserver.model.system.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
/**
 * @author jarvis
 * @date 2018-12-05
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SErrorLog implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="主键id")
    private Long id;
    @ApiModelProperty(value="操作时间")
    private Date logTime;
    @ApiModelProperty(value="类型")
    private String errorType;
    @ApiModelProperty(value="描述")
    private String errorDesc;
    @ApiModelProperty(value="状态")
    private Integer stauts;

   
}