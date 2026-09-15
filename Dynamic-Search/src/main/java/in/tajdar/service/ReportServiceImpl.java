package in.tajdar.service;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openpdf.text.Document;
import org.openpdf.text.Font;
import org.openpdf.text.FontFactory;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.tajdar.entity.UserEligibilityDetails;
import in.tajdar.repo.EligRepo;
import in.tajdar.request.SearchRequest;
import in.tajdar.response.SearchResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServiceImpl implements ReportService {

	private EligRepo eligRepo;

	public ReportServiceImpl(EligRepo eligRepo) {
		this.eligRepo = eligRepo;
	}

	@Override
	public void generateExcel(HttpServletResponse resp) throws Exception {
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=user_excel_report.xlsx";
		resp.setHeader(headerKey, headerValue);
		List<UserEligibilityDetails> all = eligRepo.findAll();

		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Email");
		headerRow.createCell(2).setCellValue("Mobile");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("SSN");
		// header row finished

		// data rows here
		int rowIndex = 1;
		for (UserEligibilityDetails one : all) {
			Row row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(one.getName() != null ? one.getName() : "");
			row.createCell(1).setCellValue(one.getEmail() != null ? one.getEmail() : "");
			row.createCell(2).setCellValue(one.getMobileNumber() != null ? one.getMobileNumber().toString() : "");
			row.createCell(3).setCellValue(one.getGender() != null ? one.getGender() : "");
			row.createCell(4).setCellValue(one.getSsn() != null ? one.getSsn().toString() : "");

		}
		ServletOutputStream outputStream = resp.getOutputStream();
		wb.write(outputStream);
		wb.close();
		outputStream.close();

	}

	@Override
	public void generatePdf(HttpServletResponse resp) throws Exception {
		List<UserEligibilityDetails> all = eligRepo.findAll();

		resp.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=user_elig_report.pdf";
		resp.setHeader(headerKey, headerValue);

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, resp.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.RED);
		font.setSize(24);
		Paragraph paragraph = new Paragraph("Report\n\n", font);
		
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(5);
		font.setFamily(FontFactory.TIMES);
		font.setColor(Color.WHITE);
		String[] headers = { "Name", "Email", "Mobile", "Gender", "SSN" };
		// header set
		for (String head : headers) {
			PdfPCell cell1 = new PdfPCell(new Phrase(head, font));
			cell1.setBackgroundColor(Color.DARK_GRAY);
			cell1.setPadding(5);
			table.addCell(cell1);

		}

		for (UserEligibilityDetails element : all) {
			table.addCell(element.getName());
			table.addCell(element.getEmail());
			table.addCell(element.getMobileNumber().toString());
			table.addCell(element.getGender());
			table.addCell(element.getSsn().toString());
		}
		document.add(table);
		document.close();
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {
		UserEligibilityDetails exampleEntity = new UserEligibilityDetails();
		String planName = request.getPlanName();
		if (planName != null) {
			exampleEntity.setPlanName(planName);
		}
		String planStatus = request.getPlanStatus();
		if (planStatus != null) {
			exampleEntity.setPlanStatus(planStatus);
		}
		LocalDate planStartDate = request.getPlanStartDate();
		if (planStartDate != null) {
			exampleEntity.setPlanStartDate(planStartDate);
		}
		LocalDate planEndDate = request.getPlanEndDate();
		if (planEndDate != null) {
			exampleEntity.setPlanEndDate(planEndDate);
		}
		Example<UserEligibilityDetails> example = Example.of(exampleEntity);
		List<UserEligibilityDetails> all = eligRepo.findAll(example);
		List<SearchResponse> responses = new ArrayList<>();
		for (UserEligibilityDetails one : all) {
			SearchResponse searchResponse = new SearchResponse();
			BeanUtils.copyProperties(one, searchResponse);
			responses.add(searchResponse);	
		}
		return responses;
	}

	@Override
	public List<String> getUniquePlanNames() {
		List<String> allUniquePlanNames = eligRepo.findAllDistinctPlanNames();
		return allUniquePlanNames;
	}

	@Override
	public List<String> getUniquePlanStatuses() {
		List<String> statuses = eligRepo.findAllDistinctPlanStatuses();
		return statuses;
	}

}
