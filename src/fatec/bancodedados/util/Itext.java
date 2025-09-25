/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatec.bancodedados.util;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import java.io.FileNotFoundException;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author isaqu
 */
public class Itext {
    public static void main(String[] args) throws FileNotFoundException{
        String path = "invoice.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);
        
         // ==== Cabeçalho ====
        Paragraph titulo = new Paragraph("SISTEMA DE VENDAS")
                .setFontSize(24)
                .setBold()
                .setBackgroundColor(new DeviceRgb(220, 240, 255)) // azul clarinho
                .setMarginBottom(10f);

        document.add(titulo);
        
        // Parágrafo com título à esquerda e data à direita
        Paragraph header = new Paragraph()
                .setFontSize(12);

        // Define um tab stop na posição final da página (direita)
        header.addTabStops(new TabStop(520, TabAlignment.RIGHT)); // 520 é a largura da página menos margens

        // Adiciona o texto à esquerda
        header.add("Produtos mais vendidos");

        // Adiciona o tab e depois o texto à direita
        header.add(new Tab());
        header.add("Gerado em: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        document.add(header);

        document.add(new Paragraph("\n")); // espaço
        
         // ==== Tabela ====
        float[] colWidths = {200f, 200f, 200f};
        Table table = new Table(UnitValue.createPercentArray(colWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        // Cabeçalho da tabela
        table.addHeaderCell(new Cell().add(new Paragraph("Produto").setBold())
                .setBackgroundColor(new DeviceRgb(200, 230, 250))
                .setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Valor unitário").setBold())
                .setBackgroundColor(new DeviceRgb(200, 230, 250))
                .setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Quantidade Vendida").setBold())
                .setBackgroundColor(new DeviceRgb(200, 230, 250))
                .setTextAlignment(TextAlignment.CENTER));

        // Dados fictícios
        String[][] dados = {
                {"Produto", "Produto", "20"},
                {"Produto", "Produto", "13"},
                {"Produto", "Produto", "10"},
                {"Produto", "Produto", "8"},
                {"Produto", "Produto", "2"}
        };

        for (String[] linha : dados) {
            table.addCell(new Cell().add(new Paragraph(linha[0]))).setTextAlignment(TextAlignment.CENTER);
            table.addCell(new Cell().add(new Paragraph(linha[1]))).setTextAlignment(TextAlignment.CENTER);
            table.addCell(new Cell().add(new Paragraph(linha[2]).setTextAlignment(TextAlignment.CENTER)));
        }
        
          // ==== Rodapé (Total) ====
        table.addCell(new Cell().add(new Paragraph("").setBold()).setBorderRight(Border.NO_BORDER)
                .setBackgroundColor(new DeviceRgb(230, 250, 255)));
        table.addCell(new Cell().add(new Paragraph("Total").setBold()).setBorderRight(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER)
                .setBackgroundColor(new DeviceRgb(230, 250, 255)).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().add(new Paragraph("53").setBold()).setBorderLeft(Border.NO_BORDER)
                .setBackgroundColor(new DeviceRgb(230, 250, 255))
                .setTextAlignment(TextAlignment.CENTER));

        document.add(table);

        
        document.close();
    }
}
