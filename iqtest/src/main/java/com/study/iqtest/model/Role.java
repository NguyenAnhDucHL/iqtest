package com.study.iqtest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {

    @Id
    @JsonProperty("_id")
    private String id;
    @JsonProperty("roleName")
    private String roleName;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("createdAt")
    private String updatedAt;

    // Getters and setters
}
