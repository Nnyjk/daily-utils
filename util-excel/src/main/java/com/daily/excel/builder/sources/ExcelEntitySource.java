package com.daily.excel.builder.sources;

import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;
import com.daily.excel.anno.ExportColumn;
import com.daily.excel.builder.ExcelSource;
import com.daily.excel.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yangxiao
 * @since V1.0.0 2024/10/23
 */
public class ExcelEntitySource<T> extends ExcelSource {

    private Class<T> entityClass;

    private List<T> entities;

    @Override
    protected int[] process(Worksheet worksheet, int row, int column) throws IllegalAccessException {
        Cells cells = worksheet.getCells();

        List<Field> fields = ReflectionUtil.getAllFields(entityClass).stream()
                .filter(field -> field.isAnnotationPresent(ExportColumn.class))
                .sorted((f1, f2) -> {
                    ExportColumn ec1 = f1.getAnnotation(ExportColumn.class);
                    ExportColumn ec2 = f2.getAnnotation(ExportColumn.class);
                    return Integer.compare(ec1.order(), ec2.order());
                }).collect(Collectors.toList());

        row++;
        // 写入表头
        for (int i = row; i < fields.size(); i++) {
            Field field = fields.get(i);
            ExportColumn exportColumn = field.getAnnotation(ExportColumn.class);
            cells.get(row, i).setValue(exportColumn.name());
        }

        row++;
        // 写入数据
        int tempRow = 0, tempCol = column;
        for (; tempRow < entities.size(); tempRow++) {
            T entity = entities.get(tempRow);
            int col = 0;
            for (; col < fields.size(); col++) {
                Field field = fields.get(col);
                field.setAccessible(true);
                Object value = field.get(entity);
                cells.get(tempRow + row, col).setValue(value);

                // 处理自动扩展
                ExportColumn exportColumn = field.getAnnotation(ExportColumn.class);
                if (exportColumn.expand() && !exportColumn.expandField().isEmpty()) {
                    @SuppressWarnings("unchecked")
                    Map<String, List<Object>> expandData = (Map<String, List<Object>>) value;
                    List<Object> expandValues = expandData.get(exportColumn.expandField());
                    for (Object expandValue : expandValues) {
                        cells.get(tempRow + row, ++col).setValue(expandValue);
                    }
                }
            }
            tempCol = Math.max(tempCol, col);
        }

        return new int[]{tempRow + row, tempCol};
    }
}
