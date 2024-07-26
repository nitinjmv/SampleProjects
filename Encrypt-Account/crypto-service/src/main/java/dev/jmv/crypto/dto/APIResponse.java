package dev.jmv.crypto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"httpStatusCode", "resultCount", "data"})
public class APIResponse {

    @JsonProperty(value = "status")
    private int httpStatusCode;

    @JsonProperty(value = "count")
    private Integer resultCount;

    @JsonProperty(value = "data")
    private Object data;
}
