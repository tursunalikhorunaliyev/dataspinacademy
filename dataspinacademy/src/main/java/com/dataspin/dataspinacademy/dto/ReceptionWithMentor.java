package com.dataspin.dataspinacademy.dto;

import com.dataspin.dataspinacademy.projection.MentorInfo;
import com.dataspin.dataspinacademy.projection.ReceptionInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ReceptionWithMentor {
    private ReceptionInfo receptionInfo;
    private MentorInfo mentorInfo;

}
