package fatec.bancodedados.util;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import fatec.bancodedados.dao.NotaFiscalDAO;
import fatec.bancodedados.dao.ProdutoDAO;
import fatec.bancodedados.dto.ProdutoMaisVendido;
import fatec.bancodedados.model.NotaFiscal;
import fatec.bancodedados.model.ProdutoNota;

import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class GeradorDeRelatorios {

    // =================================================================================
    // 1. ESTILOS E CONSTANTES PADRONIZADOS
    // =================================================================================
    private static final String TITULO_PRINCIPAL = "SISTEMA DE VENDAS";
    private static final Color COR_PRINCIPAL_TEXTO = new DeviceRgb(204, 102, 51); // Laranja escuro
    private static final Color COR_HEADER_TABELA = new DeviceRgb(252, 227, 214);   // Laranja claro
    private static final Color COR_BORDA_TABELA = new DeviceRgb(204, 204, 204);   // Cinza

    private final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    // =================================================================================
    // 2. MÉTODOS PÚBLICOS (PONTOS DE ENTRADA)
    // =================================================================================

    public void gerarHistoricoCompleto() throws FileNotFoundException {
        List<NotaFiscal> nfs = new NotaFiscalDAO().getNotaFiscais();
        String path = "src/fatec/bancodedados/invoice/historicoCompleto.pdf";
        Document document = iniciarDocumentoPdf(path);

        adicionarCabecalho(document, "Histórico de Compras", null);

        for (NotaFiscal nf : nfs) {
            adicionarBlocoNotaFiscal(document, nf);
        }

        document.close();
        System.out.println("Relatório de histórico gerado em: " + path);
    }

    public void gerarRelatorioVendasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) throws FileNotFoundException {
        List<NotaFiscal> nfs = new NotaFiscalDAO().getNotaFicaisPorData(inicio, fim);
        String path = "src/fatec/bancodedados/invoice/relatorioVendasPeriodo.pdf";
        Document document = iniciarDocumentoPdf(path);

        String periodo = String.format("De: %s até %s", inicio.format(formatadorData), fim.format(formatadorData));
        adicionarCabecalho(document, "Relatório de Vendas por Período", periodo);

        for (NotaFiscal nf : nfs) {
            adicionarBlocoNotaFiscal(document, nf);
        }

        document.close();
        System.out.println("Relatório de vendas por período gerado em: " + path);
    }

    public void gerarRelatorioProdutosMaisVendidos() throws FileNotFoundException {
        List<ProdutoMaisVendido> produtos = new ProdutoDAO().getProdutosMaisVendidos();
        String path = "src/fatec/bancodedados/invoice/produtosMaisVendidos.pdf";
        Document document = iniciarDocumentoPdf(path);
        
        adicionarCabecalho(document, "Produtos Mais Vendidos", null);
        
        Table tabela = new Table(UnitValue.createPercentArray(new float[]{50f, 25f, 25f}))
                .setWidth(UnitValue.createPercentValue(100));

        // Cabeçalho
        tabela.addHeaderCell(criarCelulaCabecalho("Produto"));
        tabela.addHeaderCell(criarCelulaCabecalho("Valor Unitário"));
        tabela.addHeaderCell(criarCelulaCabecalho("Quantidade Vendida"));

        int totalVendido = 0;
        for (ProdutoMaisVendido pmv : produtos) {
            tabela.addCell(criarCelulaDados(pmv.getNome(), TextAlignment.LEFT));
            tabela.addCell(criarCelulaDados(formatadorMoeda.format(pmv.getPrecoVenda()), TextAlignment.CENTER));
            tabela.addCell(criarCelulaDados(String.valueOf(pmv.getQuantidadeVendida()), TextAlignment.CENTER));
            totalVendido += pmv.getQuantidadeVendida();
        }
        
        // Rodapé com o total
        tabela.addCell(new Cell(1, 2)
            .add(new Paragraph("Total de Itens Vendidos").setBold())
            .setTextAlignment(TextAlignment.RIGHT)
            .setBorder(Border.NO_BORDER));
        tabela.addCell(criarCelulaDados(String.valueOf(totalVendido), TextAlignment.CENTER).setBold());

        document.add(tabela);
        document.close();
        System.out.println("Relatório de produtos mais vendidos gerado em: " + path);
    }


    // =================================================================================
    // 3. MÉTODOS AUXILIARES ESTRUTURAIS (REUTILIZADOS)
    // =================================================================================

    private Document iniciarDocumentoPdf(String path) throws FileNotFoundException {
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        return new Document(pdfDocument);
    }

    private void adicionarCabecalho(Document document, String subtitulo, String periodo) {
        // Título principal
        Paragraph titulo = new Paragraph(TITULO_PRINCIPAL)
                .setFontSize(26)
                .setFontColor(COR_PRINCIPAL_TEXTO)
                .setMarginBottom(5);
        document.add(titulo);

        // Tabela para alinhar subtítulo e data
        Table subcabecalho = new Table(UnitValue.createPercentArray(new float[]{70f, 30f}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(20f);
        
        // Coluna da Esquerda (Subtítulo e Período)
        Paragraph pEsquerda = new Paragraph(subtitulo).setBold();
        if (periodo != null && !periodo.isEmpty()) {
            pEsquerda.add("\n").add(periodo).setFontSize(10).setBold();
        }
        Cell cellEsquerda = new Cell().add(pEsquerda).setBorder(Border.NO_BORDER);
        
        // Coluna da Direita (Data de Geração)
        String dataGeracao = "Gerado em: " + LocalDate.now().format(formatadorData);
        Paragraph pDireita = new Paragraph(dataGeracao).setFontSize(10);
        Cell cellDireita = new Cell().add(pDireita).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER);

        subcabecalho.addCell(cellEsquerda);
        subcabecalho.addCell(cellDireita);
        document.add(subcabecalho);
    }

    private void adicionarBlocoNotaFiscal(Document document, NotaFiscal nf) {
        // Tabela para o cabeçalho "Nota Fiscal: #X"
        Table tabelaCabecalhoNF = new Table(UnitValue.createPercentArray(new float[]{100f}))
                .setWidth(UnitValue.createPercentValue(100))
                .setBackgroundColor(COR_HEADER_TABELA)
                .setMarginTop(10f);
        tabelaCabecalhoNF.addCell(
                new Cell().add(new Paragraph("Nota Fiscal: #" + nf.getCodNota()).setBold())
                        .setBorder(Border.NO_BORDER).setPadding(5)
        );
        document.add(tabelaCabecalhoNF);

        // Tabela para as informações do cliente
        Table tabelaInfos = new Table(UnitValue.createPercentArray(new float[]{34f, 33f, 33f}))
                .setWidth(UnitValue.createPercentValue(100));
        tabelaInfos.addCell(criarCelulaInfo("Cliente\n" + nf.getCliente().getNome()));
        tabelaInfos.addCell(criarCelulaInfo("Data Compra\n" + nf.getDataVenda()));
        tabelaInfos.addCell(criarCelulaInfo("Total Itens\n" + nf.getQtdTotal()));
        document.add(tabelaInfos);

        // Tabela de produtos
        document.add(criarTabelaProdutos(nf));
    }

    private Table criarTabelaProdutos(NotaFiscal nf) {
        Table tabela = new Table(UnitValue.createPercentArray(new float[]{40f, 20f, 20f, 20f}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(20f); // Espaço após cada bloco de nota fiscal

        // Cabeçalhos
        tabela.addHeaderCell("Produto");
        tabela.addHeaderCell("Valor Unt.");
        tabela.addHeaderCell("Qtd.");
        tabela.addHeaderCell("Valor Total");

        // Itens
        for (ProdutoNota item : nf.getItens()) {
            double valorTotalItem = item.getProduto().getPrecoVenda() * item.getQtdVendida();
            tabela.addCell(criarCelulaDados(item.getProduto().getNome(), TextAlignment.LEFT));
            tabela.addCell(criarCelulaDados(formatadorMoeda.format(item.getProduto().getPrecoVenda()), TextAlignment.RIGHT));
            tabela.addCell(criarCelulaDados(String.valueOf(item.getQtdVendida()), TextAlignment.CENTER));
            tabela.addCell(criarCelulaDados(formatadorMoeda.format(valorTotalItem), TextAlignment.RIGHT));
        }

        // Rodapé com o Subtotal
        Cell cellLabelSubtotal = new Cell(1, 3)
                .add(new Paragraph("Subtotal").setBold())
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT);
        Cell cellValorSubtotal = new Cell()
                .add(new Paragraph(formatadorMoeda.format(nf.getSubTotal())).setBold())
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT);
        
        tabela.addCell(cellLabelSubtotal);
        tabela.addCell(cellValorSubtotal);

        return tabela;
    }

    // =================================================================================
    // 4. MÉTODOS AUXILIARES DE CRIAÇÃO DE CÉLULAS (ESTILO)
    // =================================================================================

    private Cell criarCelulaCabecalho(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setBold())
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(COR_HEADER_TABELA);
    }

    private Cell criarCelulaDados(String texto, TextAlignment alinhamento) {
        return new Cell()
                .add(new Paragraph(texto))
                .setTextAlignment(alinhamento)
                .setBorder(new SolidBorder(COR_BORDA_TABELA, 0.5f));
    }

    private Cell criarCelulaInfo(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setFontSize(9))
                .setBorder(new SolidBorder(COR_BORDA_TABELA, 0.5f));
    }
}