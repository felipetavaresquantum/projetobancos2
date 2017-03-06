package utilitarios;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FuncoesDiversas 
{
    public static String QuoteStringDuplas(String vrString) 
    {
        if (vrString == null)
        {
            return null;
        }
        
        if (vrString.equalsIgnoreCase(""))
        {
          return "";
        }
        
        int lastPos = (vrString.length() - 1);
   
        if (lastPos < 0 || (vrString.charAt(0) == '"' && vrString.charAt(lastPos) == '"')) 
        {
          return vrString;
        }
          
        return '"' + vrString + '"';
    }
    
    public static String QuoteStringSimples(String vrString) 
    {
        StringBuilder Str = new StringBuilder();
        
        Str.append("'").append(vrString).append("'");

        return Str.toString();
    }
    
    public static String FormataDataSQL(Date vrData)
    {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String vrDataFormatada = formatter.format(vrData);
        
        return vrDataFormatada;
    }
    
    public static String SoNumeros(String vrString)
    {
        return vrString.replaceAll("\\D", "");
    }
}