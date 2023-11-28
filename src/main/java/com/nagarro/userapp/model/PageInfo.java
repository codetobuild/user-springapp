package com.nagarro.userapp.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PageInfo {
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private int total;

}
