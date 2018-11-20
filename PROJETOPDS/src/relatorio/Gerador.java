/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorio;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.DiaDAO;
import dao.HorarioDAO;
import dao.MateriaDAO;
import dao.MonitorDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DiaDaSemana;
import model.Horario;
import model.Materia;
import model.Monitor;

/**
 *
 * @author ruyneto
 */
public class Gerador {
    public static String DEST = "Relatorio/relatorio.pdf";
    public static String periodo;
    public static void gerarRelatorio(String str){
        File file = new File(DEST);
        file.getParentFile().mkdirs(); 
        periodo = str;
        try {
            createPdf(DEST);
        } catch (IOException ex) {
            Logger.getLogger(Gerador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(Gerador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void createPdf(String dest) throws IOException, DocumentException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        Chunk chunk = new Chunk("SIGEM - Relatório de Monitorias.", chapterFont);
        Chapter chapter = new Chapter(new Paragraph(chunk), 1);
        chapter.setNumberDepth(0);
        chapter.add(new Paragraph("Relatório gerado as "+sdf.format(cal.getTime()), paragraphFont));
        document.add(chapter);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(gerarSecaoMonitoresMaisRequisitados());
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(gerarSecaoMateriasMaisRequisitadas());        
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(gerarSecaoHorariosMaisRequisitados());
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(gerarSecaodiadasemanamaisrequisitados());
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(gerarSecaomonitoresqueoferecemmaishorarios());          
        document.close();
    }
    public static Paragraph gerarSecaoMonitoresMaisRequisitados(){
        Chunk titulo = new Chunk("1 - Monitores mais requisitados e quantidades de requisições.",FontFactory.getFont(FontFactory.HELVETICA, 18, Font.NORMAL));
        Paragraph paragrafo = new Paragraph(titulo);
        paragrafo.add(gerarTabelamonitoresMaisRequisitados());
        return paragrafo;
    }
    public static PdfPTable gerarTabelamonitoresMaisRequisitados(){
        // a table with three columns
        PdfPTable table = new PdfPTable(2);        
        // the cell object
        PdfPCell cell;
        Font cabeçalho = FontFactory.getFont(FontFactory.TIMES_ROMAN,20, Font.BOLD);
        
        //Inserir cabeçalho
        cell = new PdfPCell(new Phrase("Monitor",cabeçalho));
        cell.setHorizontalAlignment(1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Quantidade",cabeçalho));
        cell.setHorizontalAlignment(1);
        table.addCell(cell);        
        MonitorDAO lista = new MonitorDAO();
        
        lista.monitoresMaisRequisitados(periodo).forEach((n)->{
                          PdfPCell celula;
                        celula = new PdfPCell(new Phrase(((Monitor) n.get(0)).getNome()));
                        table.addCell(celula);
                        celula = new PdfPCell(new Phrase(((Vector) n).get(1).toString()));
                        table.addCell(celula); 
        });
        //Inserir cabeçalho
        // we add a cell with colspan 3
        // now we add a cell with rowspan 2
        return table;    
    }
    public static Paragraph gerarSecaoMateriasMaisRequisitadas(){
        Chunk titulo = new Chunk("2 - Matérias mais requisitadas e quantidades de requisições.",FontFactory.getFont(FontFactory.HELVETICA, 18, Font.NORMAL));
        Paragraph paragrafo = new Paragraph(titulo);
        paragrafo.add(gerarTabelaMateriasMaisRequisitadas());
        return paragrafo;
    }
    public static PdfPTable gerarTabelaMateriasMaisRequisitadas(){
        // a table with three columns
        PdfPTable table = new PdfPTable(2);        
        // the cell object
        PdfPCell cell;
        Font cabeçalho = FontFactory.getFont(FontFactory.TIMES_ROMAN,20, Font.BOLD);
        
        //Inserir cabeçalho
        cell = new PdfPCell(new Phrase("Matéria",cabeçalho));
        cell.setHorizontalAlignment(1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Quantidade",cabeçalho));
        cell.setHorizontalAlignment(1);
        table.addCell(cell);        
        MateriaDAO lista = new MateriaDAO();
        
               lista.materiasMaisRequisitadas(periodo).forEach((n)->{
                          PdfPCell celula;
                        celula = new PdfPCell(new Phrase(((Materia) n.get(0)).getNome()));
                        table.addCell(celula);
                        celula = new PdfPCell(new Phrase(((Vector) n).get(1).toString()));
                        table.addCell(celula); 
        }); 
        //Inserir cabeçalho
        // we add a cell with colspan 3
        // now we add a cell with rowspan 2
        return table;    
    }
    
 public static Paragraph gerarSecaoHorariosMaisRequisitados(){
        Chunk titulo = new Chunk("3 - Horários mais requisitados e quantidades de requisições.",FontFactory.getFont(FontFactory.HELVETICA, 18, Font.NORMAL));
        Paragraph paragrafo = new Paragraph(titulo);
        paragrafo.add(gerarTabelaHorariosMaisRequisitados());
        return paragrafo;
    }
    public static PdfPTable gerarTabelaHorariosMaisRequisitados(){
        // a table with three columns
        PdfPTable table = new PdfPTable(2);        
        // the cell object
        PdfPCell cell;
        Font cabeçalho = FontFactory.getFont(FontFactory.TIMES_ROMAN,20, Font.BOLD);
        
        //Inserir cabeçalho
        cell = new PdfPCell(new Phrase("Horario",cabeçalho));
        cell.setHorizontalAlignment(1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Quantidade",cabeçalho));
        cell.setHorizontalAlignment(1);
        table.addCell(cell);        
        HorarioDAO lista = new HorarioDAO();
        
               lista.horariosMaisRequisitados(periodo).forEach((n)->{
                          PdfPCell celula;
                        celula = new PdfPCell(new Phrase(((Horario) n.get(0)).getHoraInicio()));
                        table.addCell(celula);
                        celula = new PdfPCell(new Phrase(((Vector) n).get(1).toString()));
                        table.addCell(celula); 
        }); 
        //Inserir cabeçalho
        // we add a cell with colspan 3
        // now we add a cell with rowspan 2
        return table;    
    }    
 public static Paragraph gerarSecaodiadasemanamaisrequisitados(){
        Chunk titulo = new Chunk("4 - Dias semanais mais requisitados e quantidade.",FontFactory.getFont(FontFactory.HELVETICA, 18, Font.NORMAL));
        Paragraph paragrafo = new Paragraph(titulo);
        paragrafo.add(gerarTabeladiadasemanamaisrequisitados());
        return paragrafo;
    }
    public static PdfPTable gerarTabeladiadasemanamaisrequisitados(){
        // a table with three columns
        PdfPTable table = new PdfPTable(2);        
        // the cell object
        PdfPCell cell;
        Font cabeçalho = FontFactory.getFont(FontFactory.TIMES_ROMAN,20, Font.BOLD);
        
        //Inserir cabeçalho
        cell = new PdfPCell(new Phrase("Dia",cabeçalho));
        cell.setHorizontalAlignment(1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Quantidade",cabeçalho));
        cell.setHorizontalAlignment(1);
        table.addCell(cell);        
        DiaDAO lista = new DiaDAO();
        
               lista.diaDaSemanaMaisRequisitados(periodo).forEach((n)->{
                          PdfPCell celula;
                        celula = new PdfPCell(new Phrase(((DiaDaSemana) n.get(0)).getNome()));
                        table.addCell(celula);
                        celula = new PdfPCell(new Phrase(((Vector) n).get(1).toString()));
                        table.addCell(celula); 
        }); 
        //Inserir cabeçalho
        // we add a cell with colspan 3
        // now we add a cell with rowspan 2
        return table;    
    }    
 public static Paragraph gerarSecaomonitoresqueoferecemmaishorarios(){
        Chunk titulo = new Chunk("5 - Monitores e quantidade de horas oferecidas em ordem reversa.",FontFactory.getFont(FontFactory.HELVETICA, 18, Font.NORMAL));
        Paragraph paragrafo = new Paragraph(titulo);
        paragrafo.add(gerarTabelamonitoresqueoferecemmaishorarios());
        return paragrafo;
    }
    public static PdfPTable gerarTabelamonitoresqueoferecemmaishorarios(){
        // a table with three columns
        PdfPTable table = new PdfPTable(2);        
        // the cell object
        PdfPCell cell;
        Font cabeçalho = FontFactory.getFont(FontFactory.TIMES_ROMAN,20, Font.BOLD);
        
        //Inserir cabeçalho
        cell = new PdfPCell(new Phrase("Monitor",cabeçalho));
        cell.setHorizontalAlignment(1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Quantidade",cabeçalho));
        cell.setHorizontalAlignment(1);
        table.addCell(cell);        
        MonitorDAO lista = new MonitorDAO();
        
               lista.monitoresQueOferecemMaisHorarios(periodo).forEach((n)->{
                          PdfPCell celula;
                        celula = new PdfPCell(new Phrase(((Monitor) n.get(0)).getNome()));
                        table.addCell(celula);
                        celula = new PdfPCell(new Phrase(((Vector) n).get(1).toString()));
                        table.addCell(celula); 
        }); 
        //Inserir cabeçalho
        // we add a cell with colspan 3
        // now we add a cell with rowspan 2
        return table;    
    }  
}
