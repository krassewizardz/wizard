package wizard.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import wizard.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;

class PdfHeaderAndFooter extends PdfPageEventHelper {
    private String date;
    private int pageNumber;
    private String user;
    public PdfHeaderAndFooter(User u)  {
        pageNumber = 0;
        this.user = u.getName();
        Date currentDate = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        date = dt.format(currentDate);
    }

    public void onEndPage(PdfWriter writer, Document document, User user) {
        PdfContentByte cb = writer.getDirectContent();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.BLACK);
        Phrase footer = new Phrase("Seite "+ ++pageNumber+"\n"+"Erstellt am "+ date +" von "+user, font);
        Phrase header = new Phrase("Erstellt am "+ date +" von "+user, font);
        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                footer,
                document.right(),
                document.bottom() + 5, 0);
//        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
//                header,
//                document.right(),
//                document.top() - 10, 0);

//        BaseColor lineColor = new BaseColor(166,166,166);
//        cb.setColorStroke(lineColor);
//        cb.moveTo(Utilities.millimetersToPoints(198), 806);
//        cb.lineTo(36, 806);
        cb.closePathStroke();
    }
}
