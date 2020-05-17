package pl.edu.agh.mwo.java;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        File file = new File("sudoku.xlsx");
        try {
            Workbook wb = WorkbookFactory.create(file);
            SudokuBoardChecker sbc = new SudokuBoardChecker(wb);
            for(int i=0; i<7; i++) {
                boolean structueValid = sbc.verifyBoardStructure(i);
                if (structueValid) {
                    sbc.verifyBoard(i);
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
