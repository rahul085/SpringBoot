package com.example.Registration.service;

import com.example.Registration.entity.District;
import com.example.Registration.entity.State;
import com.example.Registration.repositories.StateRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WriteToExcelService {
    private final StateRepository stateRepository;

    public String generateExcelToClassPath() {
        List<State> stateList = stateRepository.findAll();

        Map<Integer, Object[]> data = new TreeMap<>();

        data.put(0, new Object[]{"STATE_ID", "STATE_NAME", "DISTRICTS"});

        int index = 1;

        for (State state : stateList) {
            String districtNames = state.getDistrictSet().stream()
                    .map(District::getDistrictName)
                    .collect(Collectors.joining(", "));
            data.put(index, new Object[]{
                    state.getStateId(),
                    state.getStateName(),
                    districtNames
            });
            index++;
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("States_Data");
            int rowNum = 0;
            for (Integer key : data.keySet()) {
                Row row = sheet.createRow(rowNum++);
                Object[] objectArr = data.get(key);
                int cellNum = 0;

                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellNum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Long) {
                        cell.setCellValue((Long) obj);
                    } else if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    }

                }
            }

                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);

                String projectPath = System.getProperty("user.dir");
                File file = new File(projectPath + "/src/main/resources/StateData.xlsx");

                FileOutputStream out = new FileOutputStream(file);
                workbook.write(out);
                out.close();
                return "Success! File generated at: " + file.getAbsolutePath();


        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to generate file: " + e.getMessage();
        }
    }
}


