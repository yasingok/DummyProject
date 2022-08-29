package com.example.order_adapter.common;

import com.example.order_adapter.resources.CommonBase;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(onlyExplicitlyIncluded = true)
@Data
public class RestErrorResource extends CommonBase {
    @JsonProperty("description")
    @ToString.Include
    protected String description;

    @JsonProperty("field")
    @ToString.Include
    protected String field;
}
