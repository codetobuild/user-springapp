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
    private long total;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PageInfo pageInfo = (PageInfo) obj;
        return hasNextPage == pageInfo.hasNextPage &&
                hasPreviousPage == pageInfo.hasPreviousPage &&
                total == pageInfo.total;
    }

}
