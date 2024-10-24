package com.daily.excel.builder;

import com.aspose.cells.Worksheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangxiao
 * @since V1.0.0 2024/10/23
 */
public final class SheetBuilder {

    private final ExcelBuilder excelBuilder;
    /**
     * 当前sheet页包含的数据源
     */
    private final List<ExcelSource> sources = new ArrayList<>();
    /**
     * Sheet页中的静态信息
     */
    private final Map<String, String> staticInfo = new HashMap<>();
    /**
     * sheet名称
     */
    private final String sheetName;

    SheetBuilder(ExcelBuilder excelBuilder, String sheetName) {
        this.excelBuilder = excelBuilder;
        this.sheetName = sheetName;
    }

    void process(Worksheet sheet) throws Exception {
        sheet.setName(this.sheetName);
        int row = 0;
        int column = 0;
        for (ExcelSource source : sources) {
            int[] pair = source.process(sheet, row, column);
            row = pair[0];
            column = Math.max(column, pair[1]);
        }
    }

    /**
     * 顺序添加数据源
     * @param source 数据源
     * @return sheetBuilder
     */
    public SheetBuilder addSource(ExcelSource source) {
        sources.add(source);
        return this;
    }


    /**
     * 当前sheet构建完成，返回至整个excel构建器
     * @return excelBuilder
     */
    public ExcelBuilder build() {
        return this.excelBuilder;
    }

}
