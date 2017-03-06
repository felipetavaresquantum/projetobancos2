package utilitarios;

import javax.swing.JOptionPane;
         
public class Alerta 
{
    public static void MostrarAlerta(String Mensagem, String Titulo)
    {
        JOptionPane.showMessageDialog(null, Mensagem, Titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void MostrarAlertaErro(String Mensagem, String Titulo)
    {
        JOptionPane.showMessageDialog(null, Mensagem, Titulo, JOptionPane.ERROR_MESSAGE);
    }
}