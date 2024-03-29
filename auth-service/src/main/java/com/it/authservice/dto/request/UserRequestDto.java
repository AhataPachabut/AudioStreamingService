package com.it.authservice.dto.request;

import com.it.authservice.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserRequestDto {

    @NotNull
    @NotEmpty
    private String account;

    @NotNull
    @NotEmpty
    private Set<Role> roles;

}
