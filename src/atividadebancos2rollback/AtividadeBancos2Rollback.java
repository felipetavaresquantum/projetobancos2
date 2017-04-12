package atividadebancos2rollback;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import utilitarios.Alerta;

public class AtividadeBancos2Rollback 
{
    public static void main(String[] args) 
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            FrmPrincipal FramePrincipal = new FrmPrincipal();
            
            VInicial PainelInicial = new VInicial();
            
            FramePrincipal.add(PainelInicial);
            FramePrincipal.setContentPane(PainelInicial);
            FramePrincipal.pack();
            FramePrincipal.setLocationRelativeTo(null);
            FramePrincipal.setResizable(false);
            FramePrincipal.setVisible(true);
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            Alerta.MostrarAlerta("Erro ao chamar a tela. Erro: " + e.toString(), "Erro Chamada Tela");
        }
    }
}