package wizard.services;

import wizard.models.Report;
import java.nio.file.Path;


public interface ExportServiceProvider {
    void exportToFile(Report r, Path d);
}