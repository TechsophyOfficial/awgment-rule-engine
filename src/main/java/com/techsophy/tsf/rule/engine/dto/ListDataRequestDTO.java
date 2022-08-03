package com.techsophy.tsf.rule.engine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class ListDataRequestDTO
{
    String id;
    Integer version;
    List<Map<String,Object>> variables;
}
