package org.date.datecalculation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadExcel {

	String fileName=null, sSheetName=null;
	public static final String BASEPATH = System.getProperty("user.dir");

	private Sheet getSheetName() throws Exception {
		Sheet sheet = null;
		try {		
			Workbook workbook = WorkbookFactory.create(new File(fileName));
			sheet = workbook.getSheet(sSheetName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sheet;
	}

	
	public String getDateForID(String sID) throws Exception {
		String sExcelName = BASEPATH+File.separator+"TestFiles"+File.separator+"SourceCSV.xlsx";
		String sSheetName = "SourceCSV";
		String sDate = "";
		List<ArrayList<String>> lstExcelData = getExcelDataInList(sExcelName,sSheetName);
			
		ArrayList<String> arrayList = getRequiredListcontainsID(sID,lstExcelData);		
		
		if(arrayList != null) {
			sDate = arrayList.get(2);
		}
		return sDate;
	}

	private ArrayList<String> getRequiredListcontainsID(String sID, List<ArrayList<String>> lstExcelData) {
		
		for(ArrayList<String> arrayList : lstExcelData) {
			for(String str : arrayList) {
				if(str.equalsIgnoreCase(sID))
					return arrayList;
			}
		}
		
		return null;		
		
	}

	private List<ArrayList<String>> getExcelDataInList(String spExcelName,String spSheetName) throws Exception {
		fileName=spExcelName; sSheetName= spSheetName;
		Sheet sheet = getSheetName();
		List<ArrayList<String>> lstExcelData = new ArrayList<ArrayList<String>>();
		Iterator<Row> rowIterator = sheet.iterator();
		
		while (rowIterator.hasNext()) 
		{
			ArrayList<String> rowData = new ArrayList<String>();
			Row row = rowIterator.next();
			//Every row has columns, get the column iterator and iterate over them
			Iterator<Cell> cellIterator = row.cellIterator();
			
			
			while (cellIterator.hasNext()) 
			{			
				//Get the Cell object
				Cell cell = cellIterator.next();
				
				//check the cell type and process accordingly
				switch(cell.getCellType()){
				case Cell.CELL_TYPE_STRING:
				case Cell.CELL_TYPE_NUMERIC:
					DataFormatter dataFormatter = new DataFormatter();
					String sCellVal = dataFormatter.formatCellValue(cell);
					rowData.add(sCellVal);
					break;
				}
			}
			lstExcelData.add(rowData);
		}
		
		return lstExcelData;
	}

	
}
