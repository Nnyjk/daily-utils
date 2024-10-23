package com.daily.excel.builder;

import com.aspose.cells.Worksheet;

/**
 * @author yangxiao
 * @since V1.0.0 2024/10/23
 */
public abstract class ExcelSource {

    protected abstract void process(Worksheet worksheet);
}
