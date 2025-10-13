package fatec.bancodedados.view;

import fatec.bancodedados.dao.ProdutoDAO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import fatec.bancodedados.model.Produto;
import fatec.bancodedados.util.CustomFilter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Beatriz Camargo
 */
public class CadastroProduto extends javax.swing.JFrame {

    /**
     * Creates new form CadastroProduto
     */
    public CadastroProduto() {
        initComponents();
        listarProdutos();        
        panelCod.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        tfDescricao = new javax.swing.JTextField();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        tfQuantidade = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        panelCod = new javax.swing.JPanel();
        tfCodProduto = new javax.swing.JTextField();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        btnLimpar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        NomeErrorLabel = new javax.swing.JLabel();
        QuantidadeErrorLabel = new javax.swing.JLabel();
        PrecoErrorLabel = new javax.swing.JLabel();
        tfPreco = new javax.swing.JTextField();
        javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        tabelaProdutos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(0, 51, 51));
        jPanel1.setToolTipText("");

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/box.png"))); // NOI18N
        jLabel1.setText("Cadastro de Produtos");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Novo Produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel2.setText("Nome");

        tfNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfNomeFocusLost(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel3.setText("Descrição");

        tfDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                erformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel4.setText("Preço");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel5.setText("Quantidade");

        tfQuantidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfQuantidadeFocusLost(evt);
            }
        });

        btnSalvar.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/add.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        tfCodProduto.setEnabled(false);

        jLabel6.setText("Código");

        javax.swing.GroupLayout panelCodLayout = new javax.swing.GroupLayout(panelCod);
        panelCod.setLayout(panelCodLayout);
        panelCodLayout.setHorizontalGroup(
            panelCodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCodLayout.createSequentialGroup()
                .addGroup(panelCodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfCodProduto)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(364, 364, 364))
        );
        panelCodLayout.setVerticalGroup(
            panelCodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCodLayout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfCodProduto)
                .addGap(8, 8, 8))
        );

        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/clean.png"))); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/pencil.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnDeletar.setBackground(new java.awt.Color(204, 204, 255));
        btnDeletar.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        btnDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/delete.png"))); // NOI18N
        btnDeletar.setText("Deletar");
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        NomeErrorLabel.setForeground(new java.awt.Color(255, 51, 51));

        QuantidadeErrorLabel.setForeground(new java.awt.Color(255, 51, 51));

        PrecoErrorLabel.setForeground(new java.awt.Color(255, 51, 51));

        tfPreco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfPrecoFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelCod, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfNome)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfDescricao)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(tfPreco, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnDeletar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(217, 217, 217)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(PrecoErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(QuantidadeErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(NomeErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDeletar, btnEditar, btnLimpar, btnSalvar});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(panelCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NomeErrorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(QuantidadeErrorLabel)
                    .addComponent(PrecoErrorLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnLimpar)
                    .addComponent(btnDeletar)
                    .addComponent(btnEditar)
                    .addComponent(btnSalvar))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDeletar, btnEditar, btnLimpar, btnSalvar});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Produtos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N
        jPanel3.setToolTipText("");

        tabelaProdutos.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        tabelaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código ", "Nome", "Descrição", "Preço", "Quantidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaProdutos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelaProdutos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        int linha = tabelaProdutos.getSelectedRow();
        
        if(linha < 0){
            JOptionPane.showMessageDialog(this, "Nenhuma linha selecionada! Por favor, selecione uma", "Selecione uma linha", JOptionPane.ERROR_MESSAGE);
        } else {
            int resposta = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir?",
                "Exclusão",
                JOptionPane.YES_NO_OPTION);

            if(resposta == JOptionPane.YES_OPTION){
                DefaultTableModel model = (DefaultTableModel) tabelaProdutos.getModel();

                ProdutoDAO pDAO = new ProdutoDAO();
                pDAO.excluir(Integer.parseInt(model.getValueAt(linha, 0).toString()));

                model.removeRow(linha);
            }
        }
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int linha = tabelaProdutos.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tabelaProdutos.getModel();

        if(linha < 0 ){
            JOptionPane.showMessageDialog(this, "Nenhuma linha selecionada! Por favor, selecione a linha para editar", "Selecione uma linha", JOptionPane.ERROR_MESSAGE);
        } else {
            panelCod.setVisible(true);
            tfCodProduto.setText(model.getValueAt(linha, 0).toString());
            tfNome.setText(model.getValueAt(linha, 1).toString());
            tfDescricao.setText(model.getValueAt(linha, 2) == null ?
                "" : model.getValueAt(linha, 2).toString());
            tfPreco.setText(model.getValueAt(linha, 3).toString());
            
            tfQuantidade.setText(model.getValueAt(linha, 4).toString());
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        panelCod.setVisible(false);
        tfCodProduto.setText("");
        tfNome.setText("");
        tfDescricao.setText("");
        tfPreco.setText("");
        tfQuantidade.setText("");
        QuantidadeErrorLabel.setText("");
        NomeErrorLabel.setText("");
        PrecoErrorLabel.setText("");
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        String codProd = tfCodProduto.getText();
        String nome = tfNome.getText();
        String descricao = tfDescricao.getText();
        String preco = tfPreco.getText();
        
        String quantidade = tfQuantidade.getText();
        
        if(!validarFormularioCompleto()){
            JOptionPane.showMessageDialog(this, "Por favor, corrija os campos em vermelho.", "Formulário Inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try{
            DefaultTableModel model = (DefaultTableModel) tabelaProdutos.getModel();

            if(!codProd.isEmpty()){
                int linha = tabelaProdutos.getSelectedRow();
                panelCod.setVisible(true);

                Produto p = new Produto(Integer.parseInt(codProd),
                    nome,
                    Double.parseDouble(preco),
                    Integer.parseInt(quantidade));
                p.setDescricao(descricao);

                model.setValueAt(p.getNome(), linha, 1);
                model.setValueAt(p.getDescricao() == null ?
                    "" : p.getDescricao(), linha, 2);
                model.setValueAt(p.getPrecoVenda(), linha, 3);
                model.setValueAt(p.getQtdeEstoque(), linha, 4);

                ProdutoDAO pDAO = new ProdutoDAO();
                pDAO.editar(p);

                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            } else{
                Produto p = new Produto(nome, Double.parseDouble(preco),
                    Integer.parseInt(quantidade));
                p.setDescricao(descricao);
                
                ProdutoDAO pDAO = new ProdutoDAO();
                p = pDAO.inserir(p);

                model.addRow(new Object[]{p.getCodProd(), p.getNome(),
                    p.getDescricao(), p.getPrecoVenda(), p.getQtdeEstoque()});
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Os campos preço e quantidade tem que ser númericos", "Insira campos númericos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void erformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_erformed
        // TODO add your handling code here:
    }//GEN-LAST:event_erformed

    private void tfNomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfNomeFocusLost
        String nome = tfNome.getText().trim();
        
        if(nome.isEmpty()){
            NomeErrorLabel.setText("O Nome é obrigatório!");
        }else{
            NomeErrorLabel.setText("");
        }
    }//GEN-LAST:event_tfNomeFocusLost

    private void tfQuantidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfQuantidadeFocusLost
        String quantidade = tfQuantidade.getText().trim();
        
        if(quantidade.isEmpty()){
            QuantidadeErrorLabel.setText("A quantidade é obrigatória!");
        }else if (Integer.parseInt(quantidade) < 0){
            QuantidadeErrorLabel.setText("A quantidade tem que ser maior que 0");
        }else{
            QuantidadeErrorLabel.setText("");
        }
    }//GEN-LAST:event_tfQuantidadeFocusLost

    private void tfPrecoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfPrecoFocusLost
        String preco = tfPreco.getText().trim();
        
        if(preco.isEmpty()){
            PrecoErrorLabel.setText("O preço é obrigatório!");
        }else if(Double.parseDouble(preco) <= 0){
            PrecoErrorLabel.setText("O preço tem que ser maior que 0");
        }else {
            PrecoErrorLabel.setText("");
        }
    }//GEN-LAST:event_tfPrecoFocusLost

    private void listarProdutos(){
        DefaultTableModel model = (DefaultTableModel) tabelaProdutos.getModel();
        model.setRowCount(0);
        
        ProdutoDAO pDAO = new ProdutoDAO();
        List<Produto> listaDeProdutos = pDAO.getProdutos();
        
        for(Produto p: listaDeProdutos){
            if(!p.isStatus()) continue;
            model.addRow(new Object[]{
                p.getCodProd(), 
                p.getNome(), 
                p.getDescricao(), 
                p.getPrecoVenda(), 
                p.getQtdeEstoque()
            });
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroProduto().setVisible(true);
            }
        });
        
    }
    private void carregarValidacao(){
        ((AbstractDocument) tfQuantidade.getDocument())
        .setDocumentFilter(new CustomFilter(4, CustomFilter.Tipo.NUMEROS));
    }
    
    private boolean validarFormularioCompleto() {
        boolean isFormularioValido = true;

        // --- Validação do Nome ---
        String nome = tfNome.getText().trim();
        if (nome.isEmpty()) {
            NomeErrorLabel.setText("O Nome é obrigatório!");
            isFormularioValido = false;
        } else {
            NomeErrorLabel.setText("");
        }

        // --- Validação do Preço ---
                String preco = tfPreco.getText().trim();
        
        if(preco.isEmpty()){
            PrecoErrorLabel.setText("O preço é obrigatório!");
            isFormularioValido = false;
        }else if(Double.parseDouble(preco) <= 0){
            PrecoErrorLabel.setText("O preço tem que ser maior que 0");
            isFormularioValido = false;
        }else {
            PrecoErrorLabel.setText("");
        }

        // --- Validação da Quantidade ---
        String quantidadeStr = tfQuantidade.getText().trim();
        if (quantidadeStr.isEmpty()) {
            QuantidadeErrorLabel.setText("A quantidade é obrigatória!");
            isFormularioValido = false;
        } else {
            try {
                int quantidade = Integer.parseInt(quantidadeStr);
                if (quantidade < 0) {
                    QuantidadeErrorLabel.setText("A quantidade deve ser maior que 0");
                    isFormularioValido = false;
                } else {
                    QuantidadeErrorLabel.setText("");
                }
            } catch (NumberFormatException e) {
                QuantidadeErrorLabel.setText("Quantidade inválida! Use apenas números inteiros.");
                isFormularioValido = false;
            }
        }
        return isFormularioValido;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel NomeErrorLabel;
    private javax.swing.JLabel PrecoErrorLabel;
    private javax.swing.JLabel QuantidadeErrorLabel;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JPanel panelCod;
    private javax.swing.JTable tabelaProdutos;
    private javax.swing.JTextField tfCodProduto;
    private javax.swing.JTextField tfDescricao;
    private javax.swing.JTextField tfNome;
    private javax.swing.JTextField tfPreco;
    private javax.swing.JTextField tfQuantidade;
    // End of variables declaration//GEN-END:variables
}
