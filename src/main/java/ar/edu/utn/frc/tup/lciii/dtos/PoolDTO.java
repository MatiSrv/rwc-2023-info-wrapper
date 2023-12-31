package ar.edu.utn.frc.tup.lciii.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"pool_Id", "teams"})
public class PoolDTO {
    
    
    @JsonProperty("pool_Id")
    private String poolId;
    private List<TeamDTO> teams;
}
