package com.convenant.springbootmysql.model.request;

import lombok.Data;

@Data
public class MemberCreationRequest {
    private String firstName;
    private String lastName;
}
