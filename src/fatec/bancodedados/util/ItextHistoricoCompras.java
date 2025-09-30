package fatec.bancodedados.util;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import fatec.bancodedados.dao.ClienteDAO;
import fatec.bancodedados.dao.NotaFiscalDAO;
import fatec.bancodedados.model.Cliente;
import fatec.bancodedados.model.NotaFiscal;
import fatec.bancodedados.model.Produto;
import fatec.bancodedados.model.ProdutoNota;
import java.io.FileNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ItextHistoricoCompras {

    private static final DeviceRgb VERDE_CLARO = new DeviceRgb(229, 245, 228);
    
    public ItextHistoricoCompras() throws FileNotFoundException{
        List<NotaFiscal> nfs = new NotaFiscalDAO().getNotaFiscais();
        gerarRelatorio(nfs);
    }
    
    public void gerarRelatorio(List<NotaFiscal> nfs) throws FileNotFoundException{
        String path = "src/fatec/bancodedados/invoice/historicoCompras.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);

        // ==== Cabeçalho Principal ====
        Paragraph titulo = new Paragraph("SISTEMA DE VENDAS")
                .setFontSize(22)
                .setBold()
                .setBackgroundColor(VERDE_CLARO)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginBottom(10f)
                .setPaddingTop(10f)
                .setPaddingBottom(10f);

        document.add(titulo);

        // ==== Subcabeçalho com título e data ====
        Paragraph header = new Paragraph()
                .setFontSize(12)
                .setMarginBottom(15f);

        header.addTabStops(new TabStop(520, TabAlignment.RIGHT));
        header.add("Histórico compras por cliente");
        header.add(new Tab());
        header.add("Gerado em: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        document.add(header);

        for(NotaFiscal nf : nfs){
            gerarBlocoNotaFiscal(document, nf);
        }

        document.close();
        System.out.println("PDF gerado com sucesso em: " + path);
    }
        
    private static void gerarBlocoNotaFiscal(Document document, NotaFiscal nf) {
        List<ProdutoNota> itens = nf.getItens();
        // ==== Cabeçalho da Nota Fiscal ====
        Table nfHeaderTable = new Table(UnitValue.createPercentArray(new float[]{100f}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginTop(10f);

        Cell cellNfHeader = new Cell()
                .add(new Paragraph("Nota Fiscal: #" + nf.getCodNota()).setBold())
                .setBackgroundColor(VERDE_CLARO)
                .setBorder(Border.NO_BORDER);

        nfHeaderTable.addCell(cellNfHeader);
        document.add(nfHeaderTable);

        // ==== Informações do Cliente ====
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{33f, 33f, 33f}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(5f);
        
        Cliente cl = new ClienteDAO().getCliente(nf.getCodCliente());

        infoTable.addCell(createBorderlessCell("Cliente\n" + cl.getNome()));
        infoTable.addCell(createBorderlessCell("Data Compra\n" + nf.getDataVenda()));
        infoTable.addCell(createBorderlessCell("Quantidade Vendida\n" + nf.getQtdTotal()));

        document.add(infoTable);

        // ==== Tabela de Produtos ====
        Table produtosTable = new Table(UnitValue.createPercentArray(new float[]{40f, 20f, 20f, 20f}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(15f);
        
        
        produtosTable.addHeaderCell(new Cell().add(new Paragraph("Produto")));
        produtosTable.addHeaderCell(new Cell().add(new Paragraph("Valor Unt.")));
        produtosTable.addHeaderCell(new Cell().add(new Paragraph("Quant. Vendida")));
        produtosTable.addHeaderCell(new Cell().add(new Paragraph("Valor Total")));

        for (ProdutoNota item : itens) {
            Produto p = item.getProduto();
            double total = p.getPrecoVenda() * item.getQtdVendida();
            produtosTable.addCell(new Cell().add(new Paragraph(p.getNome())));
            produtosTable.addCell(new Cell().add(new Paragraph((Double.toString(p.getPrecoVenda())))));
            produtosTable.addCell(new Cell().add(new Paragraph(Integer.toString(item.getQtdVendida()))));
            produtosTable.addCell(new Cell().add(new Paragraph(Double.toString(total))));
        }
        // ==== Subtotal ====
        Cell subtotalLabelCell = new Cell(1, 3)
                .add(new Paragraph("Subtotal").setTextAlignment(TextAlignment.RIGHT))
                .setBorder(Border.NO_BORDER)
                .setBorderTop(new SolidBorder(1));

        Cell subtotalValueCell = new Cell()
                .add(new Paragraph(Double.toString(nf.getSubTotal())))
                .setBorder(Border.NO_BORDER)
                .setBorderTop(new SolidBorder(1));

        produtosTable.addCell(subtotalLabelCell);
        produtosTable.addCell(subtotalValueCell);

        document.add(produtosTable);
    }

    private static Cell createBorderlessCell(String text) {
        return new Cell().add(new Paragraph(text)).setBorder(Border.NO_BORDER);
    }
}
;;