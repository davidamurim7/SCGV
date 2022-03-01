/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author David
 */
public class Metodos {
    
    public static String transDataHoraIngPort(String dataHora){
        String[] d = dataHora.split(" ");
        String[] d1 = d[0].split("-");
        String d2 = d[1].substring(0,8);
        return d1[2]+"/"+d1[1]+"/"+d1[0]+"  "+d2;
    }
    
    public static String transDataIngPort(String data){
        String[] d = data.split("-");
        return d[2]+"/"+d[1]+"/"+d[0];
    }
    
    public static void copiarArquivo(File arquivo, File destino) throws IOException {
        if (destino.exists()) {
            destino.delete();
        }
        FileChannel arquivoChannel = null;
        FileChannel destinoChannel = null;
        try {
            arquivoChannel = new FileInputStream(arquivo).getChannel();
            destinoChannel = new FileOutputStream(destino).getChannel();
            arquivoChannel.transferTo(0, arquivoChannel.size(),
                    destinoChannel);
        } finally {
            if (arquivoChannel != null && arquivoChannel.isOpen()) {
                arquivoChannel.close();
            }
            if (destinoChannel != null && destinoChannel.isOpen()) {
                destinoChannel.close();
            }
        }
    }
    
    public static boolean validarEmail(String email) {
        if ((email == null) || (email.trim().length() == 0)) {
            return false;
        }
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static int subtraiDatas(Date d1, Date d2){
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(d1);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(d2);
        int dias = (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (24 * 60 * 60 * 1000));
        return dias;
    }
    
    public static String diaSemana(int numberDiaSemana){
        switch(numberDiaSemana){
            case 1:
                return "Domingo";
            case 2:
                return "Segunda - feira";
            case 3:
                return "Terça - feira";
            case 4:
                return "Quarta - feira";
            case 5:
                return "Quinta - feira";
            case 6:
                return "Sexta - feira";
            case 7:
                return "Sábado";
                
        }
        return "";
    }
    
    public static String mes(int numberMes){
        switch(numberMes){
            case 0:
                return "Janeiro";
            case 1:
                return "Fevereiro";
            case 2:
                return "Março";
            case 3:
                return "Abril";
            case 4:
                return "Maio";
            case 5:
                return "Junho";
            case 6:
                return "Julho";
            case 7:
                return "Agosto";
            case 8:
                return "Setembro";
            case 9:
                return "Outubro";
            case 10:
                return "Novembro";
            case 11:
                return "Dezembro";
        }
        return "";
    }
}
