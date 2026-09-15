package in.tajdar.service;

import java.util.List;

import in.tajdar.request.SearchRequest;
import in.tajdar.response.SearchResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {
	List<String> getUniquePlanNames();
	
	List<String> getUniquePlanStatuses();
	
	void generateExcel(HttpServletResponse resp) throws Exception;

	void generatePdf(HttpServletResponse resp) throws Exception;
	
	List<SearchResponse> search(SearchRequest request);
}
