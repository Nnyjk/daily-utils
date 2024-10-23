package com.daily.excel.builder;

import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.WorksheetCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangxiao
 * @since V1.0.0 2024/10/23
 */
public final class ExcelBuilder {

    private final Object response;

    private final List<SheetBuilder> sheetBuilder = new ArrayList<>();

    private final WorkbookDesigner workbookDesigner = new WorkbookDesigner();

    private ExcelBuilder(Object response) {
        this.response = response;
    }

    public SheetBuilder addSheet(String sheetName) {
        SheetBuilder sheetBuilder = new SheetBuilder(this, sheetName);
        this.sheetBuilder.add(sheetBuilder);
        return sheetBuilder;
    }

    public void build() {
        Workbook workbook = workbookDesigner.getWorkbook();
        WorksheetCollection sheets = workbook.getWorksheets();
        for (SheetBuilder sheetBuilder : sheetBuilder) {
            sheetBuilder.process(sheets.get(sheets.add()));
        }
    }

}
