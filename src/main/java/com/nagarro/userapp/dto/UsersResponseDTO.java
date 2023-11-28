package com.nagarro.userapp.dto;

import com.nagarro.userapp.model.PageInfo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UsersResponseDTO {
    Object data;
    PageInfo pageInfo;
}
