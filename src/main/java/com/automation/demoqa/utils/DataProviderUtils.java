package com.automation.demoqa.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.lang.Object;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DataProviderUtils {

	@DataProvider(name = "basicDataProvider")

	public static Object[][] getTestDataFromDP(Method methodName) throws Exception {

		String testCaseName = methodName.getName();
		String excelDataFile = System.getProperty("user.dir") + "//src//test//resources//test-data//TestDataSheet.xlsx";

		Sheet testDataSheet = locateTestDataFile(excelDataFile);
		int totalRows = testDataSheet.getPhysicalNumberOfRows();
		int totalCols = testDataSheet.getRow(0).getPhysicalNumberOfCells();
		List<List<String>> testDataSet = new ArrayList<List<String>>(); // list of list is used to add duplicate sets of
																		// data

		for (int i = 1; i < totalRows; i++) {
			List<String> testDataRows = new ArrayList<String>();// using List here, not array, as size can change
			String testName = testDataSheet.getRow(i).getCell(0).getStringCellValue();
			String testCaseCols = "";
			if (testName.equalsIgnoreCase(testCaseName)) {
				testCaseCols = testDataSheet.getRow(i).getCell(1).getStringCellValue();
				System.out.println("Test Case[" + testName + "] Columns are [" + testCaseCols + "]");

				String[] colArr = testCaseCols.split(",");

				for (int j = 0; j < colArr.length; j++) {
					String column = colArr[j];
					int index = -1; // since at index 0 also there is a col. with test case name, so start with -1
					for (int k = 0; k < totalCols; k++) {

						if (testDataSheet.getRow(0).getCell(k).getStringCellValue().equalsIgnoreCase(column)) {
							index = k;
							break;
						}
					}
					if (index != -1) {

						String columnValue = new DataProviderUtils()
								.fetchFormattedCellValue(testDataSheet.getRow(i).getCell(index));
						System.out.println(column + ": " + columnValue);
						testDataRows.add(columnValue);

					}
				}
			}
			if (testDataRows.size() > 0) {
				testDataSet.add(testDataRows);
			}

			System.out.println(testDataSet);
		}

		Object testSet[][] = new Object[testDataSet.size()][testDataSet.get(0).size()];

		for (int i = 0; i < testDataSet.size(); i++) {
			List<String> testRow = testDataSet.get(i);
			System.out.println(testRow);
			for (int j = 0; j < testRow.size(); j++) {
				testSet[i][j] = testRow.get(j);
			}
		}

		return testSet;

	}

	public String fetchFormattedCellValue(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == CellType.BLANK) {
			return "";
		} else {
			return null;
		}
	}

	public static Sheet locateTestDataFile(String path) throws Exception {
		File excelFile = new File(path);
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet testSheet = workbook.getSheet(WebBrowserActions.Environment.toUpperCase());

		if (testSheet != null) {
			return testSheet;
		}
		return null;

	}

}
