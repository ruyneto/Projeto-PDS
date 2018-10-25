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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ruyneto
 */
public class Gerador {
    public static String DEST = "Relatorio/relatorio.pdf";
    public static void main(String args[]){
        File file = new File(DEST);
        file.getParentFile().mkdirs(); 
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
        document.add(gerarTabela());
        document.add(chapter);                                        
        document.close();
    }
    public static PdfPTable gerarTabela(){
        // a table with three columns
        PdfPTable table = new PdfPTable(2);        
        // the cell object
        PdfPCell cell;
        Font cabeçalho = FontFactory.getFont(FontFactory.TIMES_ROMAN,20, Font.BOLD);
        
        //Inserir cabeçalho
        cell = new PdfPCell(new Phrase("Matéria",cabeçalho));
        cell.setHorizontalAlignment(1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Quantidade de reservas",cabeçalho));
        cell.setHorizontalAlignment(1);
        table.addCell(cell);        

        //Inserir cabeçalho
        // we add a cell with colspan 3
        cell = new PdfPCell(new Phrase("Cell with colspan 3"));
        table.addCell(cell);
        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        table.addCell(cell);
        // we add the four remaining cells with addCell()
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;    
    }
}
