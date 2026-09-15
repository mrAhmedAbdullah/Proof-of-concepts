package in.tajdar.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.tajdar.request.SearchRequest;
import in.tajdar.response.SearchResponse;
import in.tajdar.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ReportRestController {


    private ReportService reportService;

	ReportRestController(ReportService reportServiceImpl) {
		this.reportService= reportServiceImpl;
	}

	@GetMapping("/")
	public ResponseEntity<String> welcome(){
		return new ResponseEntity<String>("Welcome to Tajdar Reporting API", HttpStatus.OK);
	}
	@GetMapping("/plans")
	public ResponseEntity<List<String>> getPlans() {
		List<String> uniquePlanNames = reportService.getUniquePlanNames();
		return new ResponseEntity<>(uniquePlanNames, HttpStatus.OK);
	}

	@GetMapping("statuses")
	public ResponseEntity<List<String>> getPlanStatuses() {
		List<String> uniquePlanStatuses = reportService.getUniquePlanStatuses();
		return new ResponseEntity<List<String>>(uniquePlanStatuses, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest req){
		List<SearchResponse> seachResponsesList = reportService.search(req);
		return new ResponseEntity<List<SearchResponse>>(seachResponsesList, HttpStatus.OK);
	}
	@GetMapping("/excel")
	public void downloadExcel(HttpServletResponse httpServletResponse) throws Exception{
		reportService.generateExcel(httpServletResponse);
	}

	@GetMapping("/pdf")
	public void downloadPdf(HttpServletResponse httpServletResponse) throws Exception {
		reportService.generatePdf(httpServletResponse);

	}
}
