package br.com.changefsm.extractor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.changefsm.models.ClassChanged;
import ch.uzh.ifi.seal.changedistiller.ChangeDistiller;
import ch.uzh.ifi.seal.changedistiller.ChangeDistiller.Language;
import ch.uzh.ifi.seal.changedistiller.distilling.FileDistiller;
import ch.uzh.ifi.seal.changedistiller.model.entities.SourceCodeChange;

public class ExtractChangesInClasses {
	
	private List<ClassChanged> classesChanged;
	private Logger log = LogManager.getLogger(ExtractChangesInClasses.class);
	
	public ExtractChangesInClasses() {}
	
	public List<ClassChanged> extractChanges(List<File> oldJavaClasses, List<File> newJavaClasses) {
		classesChanged = new ArrayList<ClassChanged>();
		ClassChanged cc;
		for (File fileNew : newJavaClasses) {
			for (File fileOld : oldJavaClasses) {

				int lastPoint = fileNew.getPath().lastIndexOf("\\");
				String auxNew = fileNew.getPath().substring(lastPoint);
				
				lastPoint = fileOld.getPath().lastIndexOf("\\");
				String auxOld = fileOld.getPath().substring(lastPoint);
				if (auxNew.equals(auxOld)) {

					FileDistiller fd = ChangeDistiller.createFileDistiller(Language.JAVA);
					fd.extractClassifiedSourceCodeChanges(fileOld, fileNew);
					if (!fd.getSourceCodeChanges().isEmpty()) {
						cc = new ClassChanged();
						for (SourceCodeChange change : fd.getSourceCodeChanges()) {

							if ((!change.getChangeType().toString().equals("DOC_UPDATE"))
									&& (!change.getChangeType().toString().equals("COMMENT_UPDATE"))
									&& (!change.getChangeType().toString().equals("DOC_DELETE"))
									&& (!change.getChangeType().toString().equals("COMMENT_DELETE"))
									&& (!change.getChangeType().toString().equals("DOC_INSERT"))
									&& (!change.getChangeType().toString().equals("COMMENT_INSERT"))
									&& (!change.getChangeType().toString().equals("COMMENT_MOVE"))) {
								cc.getChanges().add(change);
							}
						}
						if(!cc.getChanges().isEmpty()) {
							cc.setClassFile(fileNew);
							classesChanged.add(cc);
						}
						break;
					}
				}
			}
		}
		log.info("The classes with changes are: " + this.classesChanged);
		return classesChanged;
	}

	public List<ClassChanged> getClassesChanged() {
		return classesChanged;
	}

	public void setClassesChangeds(List<ClassChanged> classesChangeds) {
		this.classesChanged = classesChangeds;
	}
}