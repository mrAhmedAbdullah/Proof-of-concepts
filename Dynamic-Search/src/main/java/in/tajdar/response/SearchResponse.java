
package in.tajdar.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class SearchResponse {
	private Integer userId;
	private String name;
	private String email;
	private String gender;
	private Integer mobile;
	private Long ssn;
	private String planName;
	private String planStatus;
	
}
