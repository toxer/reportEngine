package it.infocamere.cont2.reportv2.dao.entity.viewprofile;

public class Views {
	public interface completeView extends minimalView {
	};
	// vanno distinte altrimenti il lazy load dell'ente carica
	// i report i quali devono avere gli enti a loro volta in lazy load.
	// in questo modo quando carico i report carico solo il lazy degli enti
	// quando carico gli enti carico il lazy dei report
	public interface completeEnteView extends completeView {
	};

	public interface completeReportView extends completeView {
	};

	public interface minimalView {
	};
}
