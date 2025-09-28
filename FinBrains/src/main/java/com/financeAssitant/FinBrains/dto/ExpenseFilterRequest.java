package com.financeAssitant.FinBrains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseFilterRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String categoryId;
    private String paymentType;
    private Double minAmount;
    private Double maxAmount;
    private String searchTerm;
    private List<String> tags;

    @Builder.Default
    private Integer page = 0;

    @Builder.Default
    private Integer size = 20;

    @Builder.Default
    private String sortBy = "date";

    @Builder.Default
    private String sortDirection = "desc"; // desc or asc
}
