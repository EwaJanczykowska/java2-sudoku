package pl.edu.agh.mwo.java;

import org.apache.poi.ss.usermodel.*;

import java.util.HashSet;

public class SudokuBoardChecker {
    private final Workbook workbook;

    public SudokuBoardChecker(Workbook workbook) {
        this.workbook = workbook;
    }

    public boolean verifyBoardStructure(int sheetIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        for (Row row : sheet) {
            for (Cell cell : row) {
                String cellString = getStringValue(cell);
                if (!isCellValid(cellString)) {
                    System.out.println("Plansza nr " + (sheetIndex + 1) + " jest niepoprawna syntaktycznie (niepoprawna jest wartość: " + cellString + ")");
                    return false;
                }
            }
        }
        System.out.println("Plansza nr " + (sheetIndex + 1) + " jest poprawna syntaktycznie");
        return true;
    }

    private String getStringValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return "" + (int) cell.getNumericCellValue();
            case BLANK:
                return null;
            default:
                return "Invalid value";
        }
    }

    private Integer getNumericValue(Cell cell) {
        String value = getStringValue(cell);
        if (value == null) {
            return null;
        } else return Integer.parseInt(value);
    }

    private boolean isCellValid(String string) {
        if (string == null) {
            return true;
        }
        try {
            int value = Integer.parseInt(string);
            if (value > 0 && value < 10) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean verifyBoard(int sheetIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);

        boolean valid = true;

        for (int rowIndex = 0; rowIndex < 9; rowIndex++) {
            if (!verifyRow(rowIndex, sheet)) {
                System.out.println("Nieprawidłowo wypełniony wiersz nr " + (rowIndex + 1));
                valid = false;
            }
        }

        for (int cellIndex = 0; cellIndex < 9; cellIndex++) {
            if (!verifyColumn(cellIndex, sheet)) {
                System.out.println("Nieprawidłowo wypełniona kolumna nr " + (cellIndex + 1));
                valid = false;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!verifySquare(i, j, sheet)) {
                    System.out.println("Nieprawidłowo wypełniony kwadrat (" + (i+1)  + " - w poziomie, " + (j+1) + " - w pionie)");
                    valid = false;
                }
            }
        }

        return valid;
    }

    private boolean verifyRow(int rowIndex, Sheet sheet) {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        Row row = sheet.getRow(rowIndex);
        for (Cell cell : row) {
            Integer cellValue = getNumericValue(cell);
            if (cellValue != null) {
                if (hashSet.contains(cellValue)) {
                    return false;
                } else {
                    hashSet.add(cellValue);
                }
            }
        }
        return true;
    }

    private boolean verifyColumn(int cellIndex, Sheet sheet) {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        for (Row row : sheet) {
            Cell cell = row.getCell(cellIndex);
            Integer cellValue = getNumericValue(cell);
            if (cellValue != null) {
                if (hashSet.contains(cellValue)) {
                    return false;
                } else {
                    hashSet.add(cellValue);
                }
            }
        }
        return true;
    }

    private boolean verifySquare(int i, int j, Sheet sheet) {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            Row row = sheet.getRow(rowIndex + 3 * i);
            for (int cellIndex = 0; cellIndex < 3; cellIndex++) {
                Cell cell = row.getCell(cellIndex + 3 * j);
                Integer cellValue = getNumericValue(cell);
                if (cellValue != null) {
                    if (hashSet.contains(cellValue)) {
                        return false;
                    } else {
                        hashSet.add(cellValue);
                    }
                }
            }
        }
        return true;
    }


}





