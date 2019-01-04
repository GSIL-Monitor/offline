package common.util;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * todo
 * @author ying_zhou 2017/11/24 15:14
 */
public class ExcelUtils {
    public static ExportBuilder createExportBuilder(){
        return new ExportBuilder();
    }

    /**
     *
     example:
     ExcelUtils.ExportBuilder exportBuilder = ExcelUtils
     .createExportBuilder()
     .setHeaders(headers)
     .setTitle("通知数据导出")
     .setDataTable(dataTable)
     .build();
     exportBuilder.writeToStream(bout);
     */
    public static class ExportBuilder{
        // 显示的导出表的标题
        private String title="导出数据";
        // 导出表的列名
        private String[] headers;
        //数据源
        private List<String[]> dataTable = new ArrayList<>();

        private Workbook wb;
        private Sheet sheet;

        private CellStyle stylePlain;
        private CellStyle styleHeader;

        public ExportBuilder() {
            wb=new XSSFWorkbook();
            sheet= wb.createSheet();
            //文字样式
            stylePlain=wb.createCellStyle();
            Font fontPlain=wb.createFont();
            fontPlain.setFontHeight((short)( 20*10));
            stylePlain.setFont(fontPlain);

            //表头样式
            styleHeader=wb.createCellStyle();
            Font fontHeader=wb.createFont();
            fontHeader.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
            fontHeader.setFontName("Calibri");
            fontHeader.setFontHeight((short)( 20*10));
            fontHeader.setBold(true);
            styleHeader.setFont(fontHeader);
            styleHeader.setAlignment(HorizontalAlignment.CENTER);
            styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);

        }

        public ExportBuilder build(){
            int indexRow=0;
            Row headerRow=sheet.createRow(indexRow++);
            //设置表头
            for (int index = 0; index < headers.length; index++) {
                Cell cell= headerRow.createCell(index);
                cell.setCellStyle(styleHeader);
                cell.setCellValue(headers[index]);
            }
            //生成单元格
            for (int i=0;i<dataTable.size();i++){
                Row row=sheet.createRow(indexRow++);
                String[] dr=dataTable.get(i);
                for (int colIndex=0; colIndex<dr.length;colIndex++){
                    Cell cell= row.createCell(colIndex);
                    cell.setCellStyle(stylePlain);
                    cell.setCellValue(dr[colIndex]);
                }
            }
            return this;
        }
        public void writeToStream(OutputStream bout) throws IOException {
            wb.write(bout);
        }
        public String getTitle() {
            return title;
        }

        public ExportBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public String[] getHeaders() {
            return headers;
        }

        public ExportBuilder setHeaders(String[] headers) {
            this.headers = headers;
            return this;
        }

        public List<String[]> getDataTable() {
            return dataTable;
        }

        public ExportBuilder setDataTable(List<String[]> dataTable) {
            this.dataTable = dataTable;
            return this;
        }
    }
}
