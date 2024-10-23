package com.daily.excel.anno;

/**
 * @author yangxiao
 * @since V1.0.0 2024/10/23
 */
public @interface ExportColumn {
    /**
     * 字段名称
     * @return name
     */
    String name();
    /**
     * 列排序，默认按字段顺序
     * @return int
     */
    int order() default 0;

    /**
     * 是否自动扩展，默认false
     * @return expand
     */
    boolean expand() default false;

    /**
     * 在数据集中根据指定字段值，自动扩展，默认为空。
     * 仅在自动扩展为true时启用生效
     * @return field
     */
    String expandField() default "";
}
