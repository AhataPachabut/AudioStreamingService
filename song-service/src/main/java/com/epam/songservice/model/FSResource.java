package com.epam.songservice.model;

import com.epam.songservice.annotation.StorageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("FS")
//@Table(name="FSResource")
@StorageType(StorageTypes.FS)
public class FSResource extends Resource {

    @NotNull
    @NotEmpty
    private String path;

}
