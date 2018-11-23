package util;

import javax.swing.JOptionPane;
import model.Materia;
import model.Sala;


public class Alert {

    public static int materiaAlertaExclusao(Materia c) {
        return JOptionPane.showConfirmDialog(null, "Você quer realmente excluir a Materia " + c.getNome()+ "?",
                "EXCLUIR", JOptionPane.YES_NO_OPTION);
    }

    public static void materiaAlterarSucesso(Materia c) {
        JOptionPane.showMessageDialog(null,
                "A matéria "+c.getNome()+ " foi alterada com sucesso.");
    }

    public static void materiaInserirSucesso(Materia c) {
        JOptionPane.showMessageDialog(null,
                "A matéria "+c.getNome()+ " foi inserida com sucesso.");
    }
    
    public static int salaAlertaExclusao(Sala c) {
        return JOptionPane.showConfirmDialog(null, "Você quer realmente excluir a Sala " + c.getNome()+ "?",
                "EXCLUIR", JOptionPane.YES_NO_OPTION);
    }

    public static void salaAlterarSucesso(Sala c) {
        JOptionPane.showMessageDialog(null,
                "A Sala "+c.getNome()+ " foi alterada com sucesso.");
    }

    public static void salaInserirSucesso(Sala c) {
        JOptionPane.showMessageDialog(null,
                "A Sala "+c.getNome()+ " foi inserida com sucesso.");
    }
    
}

//PessoaTableModel(recebe uma lista), varClasse List, extends AbstractTableModel, deixar netbeans inserir campos, copiar getValueAt() e fazer getColumnName() a partir dele.

//PessoaPesquisarTela(), varClasse list e dao, criar metodo carregarTela(), chamar no btPesquisar(cpNome.getText()) e quando a janela ganha foco("").

//PessoaCadastrarTela(), varClasse dao, criar o metodo salvar(cria nova pessoa e seta vars com nomes dos campos, finaliza com dao.inserir(p) e this.dispose();
// chama o metodo salvar no btSalvar.

//PessoaVisualizarTela(p), copiar PessoaCadastrarTela e colar PessoaVisualizarTela, substituir todos os campos de texto por labels
//com mesmo nome de var, alterar botoes para Alterar e Excluir, varClasse dao e p, criar carregarDados(pegar dados de p e insere nos campos),
//chamar carregarDados() no construtor.

//PessoaAlterarTela(p), copiar PessoaCadastrarTela e colar PessoaAlterarTela, varClasse dao e p, copiar PessoaVisualizarTela.carregarDados(), chamar carregarDados() no construtor
//mudar dao.inserir(p) para dao.alterar(p), remover new Pessoa de salvar().

//Para excluir use util.Alert.alertaExclusao(p.getNome()), se o retorno for zero dao.deletar(p).