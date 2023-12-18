package com.dataspin.dataspinacademy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ReceptionCounterData {
    private Integer totalCount;
    private Integer activeCount;
    private Integer inactiveCount;
}
