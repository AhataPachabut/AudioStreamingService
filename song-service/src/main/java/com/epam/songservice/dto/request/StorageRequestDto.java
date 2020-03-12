package com.epam.songservice.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class StorageRequestDto {

    @NotNull
    @NotEmpty
    private String name;

}