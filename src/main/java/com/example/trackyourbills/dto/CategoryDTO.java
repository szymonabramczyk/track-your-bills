package com.example.trackyourbills.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private Long userId;
//    private List<Long> transactionIds; // Lista identyfikatorów transakcji, nie cała encja
}
