//package com.pronostico.penca.beans;
//
//import java.io.Serializable;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.logging.Logger;
//
//import javax.annotation.PostConstruct;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.inject.Inject;
//
//import com.pronostico.penca.ejb.api.ITorneoEJBLocal;
//import com.pronostico.penca.model.Equipo;
//import com.pronostico.penca.model.Fecha;
//import com.pronostico.penca.model.Grupo;
//import com.pronostico.penca.model.Partido;
//import com.pronostico.penca.model.Torneo;
//
//@ManagedBean(name = "pruebaEJBBean")
//@RequestScoped
//
//public class PruebaEJB implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//	
//	Logger logger = Logger.getLogger("PruebaEJB");
//
//
//	@Inject
//	ITorneoEJBLocal ejbTorneo;
//	
//	private String inputIngresado;
//	private String mensajeAMostrar;
//
//	@PostConstruct
//	public void init() {
//		System.out.println("Prueba EJB Bean init()");
//	}
//
//	public String getInputIngresado() {
//		return inputIngresado;
//	}
//
//	public void setInputIngresado(String inputIngresado) {
//		this.inputIngresado = inputIngresado;
//	}
//
//	public String getMensajeAMostrar() {
//		return mensajeAMostrar;
//	}
//
//	public void setMensajeAMostrar(String mensajeAMostrar) {
//		this.mensajeAMostrar = mensajeAMostrar;
//	}
//
//	public void mostrarTorneo() {
//		Long id = new Long(inputIngresado);
//		Torneo t = ejbTorneo.obtenerTorneoPorId(id);
//		
//		logger.info(t.toString());
//		
//		mensajeAMostrar = t.toString();
//	}
//	
//	public String creacionTorneo() {
//		
//		List<Equipo> equipos = listaEquipos();
//		Fecha f1 = partidosFecha(equipos);
//
//		List<Fecha> fechas = new ArrayList<>();
//		fechas.add(f1);
//		
//		Grupo g = new Grupo();
//		g.setNombre("Campeonato Apertura 2018");
//		g.setCantEquipos(equipos.size());
//		List<Grupo> grupos = new ArrayList<Grupo>();
//		grupos.add(g);
//		g.setFechas(fechas);
//		
//		Torneo t = new Torneo();
//		t.setNombre("Primera División de Uruguay");
//		t.setLogo("http://www.auf.org.uy/Portal/images/logocamp.png");
//		t.setEquipos(equipos);
//		t.setGrupos(grupos);
//
//		ejbTorneo.altaTorneo(t);
//
//		return "ok";
//	}
//	
//	public Fecha partidosFecha(List<Equipo> equipos) {
//		
//		Partido p1 = new Partido();
//		p1.setEquipoLocal(equipos.get(0));
//		p1.setEquipoVisitante(equipos.get(1));
//		
//		Partido p2 = new Partido();
//		p2.setEquipoLocal(equipos.get(2));
//		p2.setEquipoVisitante(equipos.get(3));
//		
//		Partido p3 = new Partido();
//		p3.setEquipoLocal(equipos.get(4));
//		p3.setEquipoVisitante(equipos.get(5));
//		
//		Partido p4 = new Partido();
//		p4.setEquipoLocal(equipos.get(6));
//		p4.setEquipoVisitante(equipos.get(7));
//		
//		Partido p5 = new Partido();
//		p5.setEquipoLocal(equipos.get(8));
//		p5.setEquipoVisitante(equipos.get(9));
//		
//		Partido p6 = new Partido();
//		p6.setEquipoLocal(equipos.get(10));
//		p6.setEquipoVisitante(equipos.get(11));
//		
//		Partido p7 = new Partido();
//		p7.setEquipoLocal(equipos.get(12));
//		p7.setEquipoVisitante(equipos.get(13));
//		
//		Partido p8 = new Partido();
//		p8.setEquipoLocal(equipos.get(14));
//		p8.setEquipoVisitante(equipos.get(15));
//		
//		List<Partido> partidos = new ArrayList<Partido>();
//		partidos.add(p1);
//		partidos.add(p2);
//		partidos.add(p3);
//		partidos.add(p4);
//		partidos.add(p5);
//		partidos.add(p6);
//		partidos.add(p7);
//		partidos.add(p8);
//		
//		Fecha f = new Fecha();
//		f.setPartidos(partidos);
//		
//		return f;
//		
//	}
//
//	public List<Equipo> listaEquipos() {
//		
//		List<Equipo> listaEquipos = new ArrayList<Equipo>();
//
//		Equipo cerro = new Equipo();
//		cerro.setNombre("Cerro");
//		listaEquipos.add(cerro);
//		
//		Equipo liverpool = new Equipo();
//		liverpool.setNombre("Liverpool");
//		listaEquipos.add(liverpool);
//		
//		Equipo wanderers = new Equipo();
//		wanderers.setNombre("Wanderers");
//		listaEquipos.add(wanderers);
//		
//		Equipo elTanqueSisley = new Equipo();
//		elTanqueSisley.setNombre("El Tanque Sisley");
//		listaEquipos.add(elTanqueSisley);
//		
//		Equipo riverPlate = new Equipo();
//		riverPlate.setNombre("River Plate");
//		listaEquipos.add(riverPlate);
//		
//		Equipo bostonRiver = new Equipo();
//		bostonRiver.setNombre("Boston River");
//		listaEquipos.add(bostonRiver);
//		
//		Equipo racing = new Equipo();
//		racing.setNombre("Racing");
//		listaEquipos.add(racing);
//		
//		Equipo peniarol = new Equipo();
//		peniarol.setNombre("Peñarol");
//		listaEquipos.add(peniarol);
//		
//		Equipo defensorSporting = new Equipo();
//		defensorSporting.setNombre("Defensor Sporting");
//		listaEquipos.add(defensorSporting);
//		
//		Equipo atenas = new Equipo();
//		atenas.setNombre("Atenas");
//		listaEquipos.add(atenas);
//		
//		Equipo progreso = new Equipo();
//		progreso.setNombre("Progreso");
//		listaEquipos.add(progreso);
//		
//		Equipo danubio = new Equipo();
//		danubio.setNombre("Danubio");
//		listaEquipos.add(danubio);
//		
//		Equipo torque = new Equipo();
//		torque.setNombre("Torque");
//		listaEquipos.add(torque);
//		
//		Equipo nacional = new Equipo();
//		nacional.setNombre("Nacional");
//		listaEquipos.add(nacional);
//		
//		Equipo fenix = new Equipo();
//		fenix.setNombre("Fénix");
//		listaEquipos.add(fenix);
//		
//		Equipo ramplaJuniors = new Equipo();
//		ramplaJuniors.setNombre("Rampla Juniors");
//		listaEquipos.add(ramplaJuniors);
//		
//		return listaEquipos;
//		
//	}
//
//	public List<Equipo> listaSeleccionesWC2018() {
//		List<Equipo> listaEquipos = new ArrayList<Equipo>();
//
//		Equipo alemania = new Equipo();
//		alemania.setNombre("Alemania");
//		Equipo brasil = new Equipo();
//		brasil.setNombre("Brasil");
//		Equipo portugal = new Equipo();
//		portugal.setNombre("Portugal");
//		Equipo argentina = new Equipo();
//		argentina.setNombre("Argentina");
//		Equipo belgica = new Equipo();
//		belgica.setNombre("Bélgica");
//		Equipo espania = new Equipo();
//		espania.setNombre("España");
//		Equipo polonia = new Equipo();
//		polonia.setNombre("Polonia");
//		Equipo suiza = new Equipo();
//		suiza.setNombre("Suiza");
//		Equipo francia = new Equipo();
//		francia.setNombre("Francia");
//		Equipo peru = new Equipo();
//		peru.setNombre("Perú");
//		Equipo dinamarca = new Equipo();
//		dinamarca.setNombre("Dinamarca");
//		Equipo colombia = new Equipo();
//		colombia.setNombre("Colombia");
//		Equipo inglaterra = new Equipo();
//		inglaterra.setNombre("Inglaterra");
//		Equipo mexico = new Equipo();
//		mexico.setNombre("México");
//		Equipo croacia = new Equipo();
//		croacia.setNombre("Croacia");
//		Equipo suecia = new Equipo();
//		suecia.setNombre("Suecia");
//		Equipo uruguay = new Equipo();
//		uruguay.setNombre("Uruguay");
//		Equipo islandia = new Equipo();
//		islandia.setNombre("Islandia");
//		Equipo senegal = new Equipo();
//		senegal.setNombre("Senegal");
//		Equipo costaRica = new Equipo();
//		costaRica.setNombre("Costa Rica");
//		Equipo tunez = new Equipo();
//		tunez.setNombre("Túnez");
//		Equipo egipto = new Equipo();
//		egipto.setNombre("Egipto");
//		Equipo iran = new Equipo();
//		iran.setNombre("Irán");
//		Equipo serbia = new Equipo();
//		serbia.setNombre("Serbia");
//		Equipo australia = new Equipo();
//		australia.setNombre("Australia");
//		Equipo marruecos = new Equipo();
//		marruecos.setNombre("Marruecos");
//		Equipo nigeria = new Equipo();
//		nigeria.setNombre("Nigeria");
//		Equipo panama = new Equipo();
//		panama.setNombre("Panamá");
//		Equipo japon = new Equipo();
//		japon.setNombre("Japón");
//		Equipo corea = new Equipo();
//		corea.setNombre("República de Corea");
//		Equipo arabiaSaudita = new Equipo();
//		arabiaSaudita.setNombre("Arabia Saudita");
//		Equipo rusia = new Equipo();
//		rusia.setNombre("Rusia");
//
//		listaEquipos.add(alemania);
//		listaEquipos.add(brasil);
//		listaEquipos.add(portugal);
//		listaEquipos.add(argentina);
//		listaEquipos.add(belgica);
//		listaEquipos.add(espania);
//		listaEquipos.add(polonia);
//		listaEquipos.add(suiza);
//		listaEquipos.add(francia);
//		listaEquipos.add(peru);
//		listaEquipos.add(dinamarca);
//		listaEquipos.add(colombia);
//		listaEquipos.add(inglaterra);
//		listaEquipos.add(mexico);
//		listaEquipos.add(croacia);
//		listaEquipos.add(suecia);
//		listaEquipos.add(uruguay);
//		listaEquipos.add(islandia);
//		listaEquipos.add(senegal);
//		listaEquipos.add(costaRica);
//		listaEquipos.add(tunez);
//		listaEquipos.add(egipto);
//		listaEquipos.add(iran);
//		listaEquipos.add(serbia);
//		listaEquipos.add(australia);
//		listaEquipos.add(marruecos);
//		listaEquipos.add(nigeria);
//		listaEquipos.add(panama);
//		listaEquipos.add(japon);
//		listaEquipos.add(corea);
//		listaEquipos.add(arabiaSaudita);
//		listaEquipos.add(rusia);
//		
////		listaEquipos = ejbTorneo.guardarEquipos(listaEquipos);
//		
//		mensajeAMostrar = listaEquipos.toString();
//		
//		return listaEquipos;
//
//	}
//	
//	public void crearWC2018() throws ParseException {
//		
//		Torneo wc2018 = ejbTorneo.obtenerTorneoPorId(new Long(1l));
//		if (wc2018 == null) {
//			wc2018 = new Torneo();
//			wc2018.setNombre("Copa del mundo Rusia 2018");
//			wc2018.setLogo("https://fsprdcdnpublic.azureedge.net/global-pictures/tournaments-sq-4/254645_w");
//			
//			wc2018.setEquipos(listaSeleccionesWC2018());
//		}
//		List<Equipo> equipos = wc2018.getEquipos();
//		DateFormat format = new SimpleDateFormat("MMM dd, YYYY HH:mm", Locale.ENGLISH); 
//
//		//#######################################################################
//		//Grupo A
//		Grupo grupoA = new Grupo();
//		grupoA.setNombre("Grupo A");
//		grupoA.setCantEquipos(4);
//		List<Fecha> fechasA = new ArrayList<Fecha>();
//		
//		Partido p1 = new Partido();
//		Date date = format.parse("Jun 14, 2018 15:00");
//		p1.setFecha(date);
//		p1.setEquipoLocal(getEquipoPorNombre("Rusia", equipos));
//		p1.setEquipoVisitante(getEquipoPorNombre("Arabia Saudita", equipos));
//		
//		Partido p2 = new Partido();
//		p2.setFecha(date);
//		date = format.parse("Jun 15, 2018 12:00");
//		p2.setEquipoLocal(getEquipoPorNombre("Egipto", equipos));
//		p2.setEquipoVisitante(getEquipoPorNombre("Uruguay", equipos));
//		
//		Partido p17 = new Partido();
//		date = format.parse("Jun 19, 2018 18:00");
//		p17.setFecha(date);
//		p17.setEquipoLocal(getEquipoPorNombre("Rusia", equipos));
//		p17.setEquipoVisitante(getEquipoPorNombre("Egipto", equipos));
//		
//		Partido p18 = new Partido();
//		date = format.parse("Jun 20, 2018 15:00");
//		p18.setFecha(date);
//		p18.setEquipoLocal(getEquipoPorNombre("Uruguay", equipos));
//		p18.setEquipoVisitante(getEquipoPorNombre("Arabia Saudita", equipos));
//		
//		Partido p33 = new Partido();
//		date = format.parse("Jun 25, 2018 14:00");
//		p33.setFecha(date);
//		p33.setEquipoLocal(getEquipoPorNombre("Uruguay", equipos));
//		p33.setEquipoVisitante(getEquipoPorNombre("Rusia", equipos));
//		
//		Partido p34 = new Partido();
//		date = format.parse("Jun 25, 2018 14:00");
//		p34.setFecha(date);
//		p34.setEquipoLocal(getEquipoPorNombre("Arabia Saudita", equipos));
//		p34.setEquipoVisitante(getEquipoPorNombre("Egipto", equipos));
//		
//		//Partidos de la fecha 1
//		List<Partido> partidos1A = new ArrayList<Partido>();
//		List<Partido> partidos2A = new ArrayList<Partido>();
//		List<Partido> partidos3A = new ArrayList<Partido>();
//		partidos1A.add(p1);
//		partidos1A.add(p2);
//		partidos2A.add(p17);
//		partidos2A.add(p18);
//		partidos3A.add(p33);
//		partidos3A.add(p34);
//
//		Fecha fecha1A = new Fecha();
//		Fecha fecha2A = new Fecha();
//		Fecha fecha3A = new Fecha();
//		fecha1A.setNombre("Fecha 1");
//		fecha1A.setPartidos(partidos1A);
//		fecha2A.setNombre("Fecha 2");
//		fecha2A.setPartidos(partidos2A);
//		fecha3A.setNombre("Fecha 3");
//		fecha3A.setPartidos(partidos3A);
//
//		fechasA.add(fecha1A);
//		fechasA.add(fecha2A);
//		fechasA.add(fecha3A);
//		
//		grupoA.setFechas(fechasA);
//
//
//		//#######################################################################
//		
//		
//		//#######################################################################
//		//Grupo B
//		Grupo grupoB = new Grupo();
//		grupoB.setNombre("Grupo B");
//		grupoB.setCantEquipos(4);
//		List<Fecha> fechasB = new ArrayList<Fecha>();
//		
//		Partido p3 = new Partido();
//		date = format.parse("Jun 15, 2018 18:00");
//		p3.setFecha(date);
//		p3.setEquipoLocal(getEquipoPorNombre("Portugal", equipos));
//		p3.setEquipoVisitante(getEquipoPorNombre("España", equipos));
//		
//		Partido p4 = new Partido();
//		date = format.parse("Jun 15, 2018 15:00");
//		p4.setFecha(date);
//		p4.setEquipoLocal(getEquipoPorNombre("Marruecos", equipos));
//		p4.setEquipoVisitante(getEquipoPorNombre("Irán", equipos));
//		
//		Partido p19 = new Partido();
//		date = format.parse("Jun 20, 2018 12:00");
//		p19.setFecha(date);
//		p19.setEquipoLocal(getEquipoPorNombre("Portugal", equipos));
//		p19.setEquipoVisitante(getEquipoPorNombre("Marruecos", equipos));
//		
//		Partido p20 = new Partido();
//		date = format.parse("Jun 20, 2018 18:00");
//		p20.setFecha(date);
//		p20.setEquipoLocal(getEquipoPorNombre("Irán", equipos));
//		p20.setEquipoVisitante(getEquipoPorNombre("España", equipos));
//		
//		Partido p35 = new Partido();
//		date = format.parse("Jun 25, 2018 18:00");
//		p35.setFecha(date);
//		p35.setEquipoLocal(getEquipoPorNombre("Irán", equipos));
//		p35.setEquipoVisitante(getEquipoPorNombre("Portugal", equipos));
//		
//		Partido p36 = new Partido();
//		date = format.parse("Jun 25, 2018 18:00");
//		p36.setFecha(date);
//		p36.setEquipoLocal(getEquipoPorNombre("España", equipos));
//		p36.setEquipoVisitante(getEquipoPorNombre("Marruecos", equipos));
//		
//		//Partidos de la fecha 1
//		List<Partido> partidos1B = new ArrayList<Partido>();
//		List<Partido> partidos2B = new ArrayList<Partido>();
//		List<Partido> partidos3B = new ArrayList<Partido>();
//		partidos1B.add(p3);
//		partidos1B.add(p4);
//		partidos2B.add(p19);
//		partidos2B.add(p20);
//		partidos3B.add(p35);
//		partidos3B.add(p36);
//
//		Fecha fecha1B = new Fecha();
//		Fecha fecha2B = new Fecha();
//		Fecha fecha3B = new Fecha();
//		fecha1B.setNombre("Fecha 1");
//		fecha1B.setPartidos(partidos1B);
//		fecha2B.setNombre("Fecha 2");
//		fecha2B.setPartidos(partidos2B);
//		fecha3B.setNombre("Fecha 3");
//		fecha3B.setPartidos(partidos3B);
//
//		fechasB.add(fecha1B);
//		fechasB.add(fecha2B);
//		fechasB.add(fecha3B);
//		
//		grupoB.setFechas(fechasB);
//		//#######################################################################
//		
//		
//		//#######################################################################
//		//Grupo C
//		Grupo grupoC = new Grupo();
//		grupoC.setNombre("Grupo C");
//		grupoC.setCantEquipos(4);
//		List<Fecha> fechasC = new ArrayList<Fecha>();
//		
//		Partido p5 = new Partido();
//		date = format.parse("Jun 16, 2018 10:00");
//		p5.setFecha(date);
//		p5.setEquipoLocal(getEquipoPorNombre("Francia", equipos));
//		p5.setEquipoVisitante(getEquipoPorNombre("Australia", equipos));
//		
//		Partido p6 = new Partido();
//		date = format.parse("Jun 16, 2018 16:00");
//		p6.setFecha(date);
//		p6.setEquipoLocal(getEquipoPorNombre("Perú", equipos));
//		p6.setEquipoVisitante(getEquipoPorNombre("Dinamarca", equipos));
//		
//		Partido p21 = new Partido();
//		date = format.parse("Jun 21, 2018 15:00");
//		p21.setFecha(date);
//		p21.setEquipoLocal(getEquipoPorNombre("Francia", equipos));
//		p21.setEquipoVisitante(getEquipoPorNombre("Perú", equipos));
//		
//		Partido p22 = new Partido();
//		date = format.parse("Jun 21, 2018 12:00");
//		p22.setFecha(date);
//		p22.setEquipoLocal(getEquipoPorNombre("Dinamarca", equipos));
//		p22.setEquipoVisitante(getEquipoPorNombre("Australia", equipos));
//		
//		Partido p37 = new Partido();
//		date = format.parse("Jun 26, 2018 14:00");
//		p37.setFecha(date);
//		p37.setEquipoLocal(getEquipoPorNombre("Dinamarca", equipos));
//		p37.setEquipoVisitante(getEquipoPorNombre("Francia", equipos));
//		
//		Partido p38 = new Partido();
//		date = format.parse("Jun 26, 2018 14:00");
//		p38.setFecha(date);
//		p38.setEquipoLocal(getEquipoPorNombre("Australia", equipos));
//		p38.setEquipoVisitante(getEquipoPorNombre("Perú", equipos));
//		
//		//Partidos de la fecha 1
//		List<Partido> partidos1C = new ArrayList<Partido>();
//		List<Partido> partidos2C = new ArrayList<Partido>();
//		List<Partido> partidos3C = new ArrayList<Partido>();
//		partidos1C.add(p5);
//		partidos1C.add(p6);
//		partidos2C.add(p21);
//		partidos2C.add(p22);
//		partidos3C.add(p37);
//		partidos3C.add(p38);
//
//		Fecha fecha1C = new Fecha();
//		Fecha fecha2C = new Fecha();
//		Fecha fecha3C = new Fecha();
//		fecha1C.setNombre("Fecha 1");
//		fecha1C.setPartidos(partidos1C);
//		fecha2C.setNombre("Fecha 2");
//		fecha2C.setPartidos(partidos2C);
//		fecha3C.setNombre("Fecha 3");
//		fecha3C.setPartidos(partidos3C);
//
//		fechasC.add(fecha1C);
//		fechasC.add(fecha2C);
//		fechasC.add(fecha3C);
//		
//		grupoC.setFechas(fechasC);
//		//#######################################################################
//		
//		
//		//#######################################################################
//		//Grupo D
//		Grupo grupoD = new Grupo();
//		grupoD.setNombre("Grupo D");
//		grupoD.setCantEquipos(4);
//		List<Fecha> fechasD = new ArrayList<Fecha>();
//		
//		Partido p7 = new Partido();
//		date = format.parse("Jun 16, 2018 13:00");
//		p7.setFecha(date);
//		p7.setEquipoLocal(getEquipoPorNombre("Argentina", equipos));
//		p7.setEquipoVisitante(getEquipoPorNombre("Islandia", equipos));
//		Partido p8 = new Partido();
//		date = format.parse("Jun 16, 2018 19:00");
//		p8.setFecha(date);
//		p8.setEquipoLocal(getEquipoPorNombre("Croacia", equipos));
//		p8.setEquipoVisitante(getEquipoPorNombre("Nigeria", equipos));
//		
//		Partido p23 = new Partido();
//		date = format.parse("Jun 21, 2018 18:00");
//		p23.setFecha(date);
//		p23.setEquipoLocal(getEquipoPorNombre("Argentina", equipos));
//		p23.setEquipoVisitante(getEquipoPorNombre("Croacia", equipos));
//		Partido p24 = new Partido();
//		date = format.parse("Jun 22, 2018 15:00");
//		p24.setFecha(date);
//		p24.setEquipoLocal(getEquipoPorNombre("Nigeria", equipos));
//		p24.setEquipoVisitante(getEquipoPorNombre("Islandia", equipos));
//		
//		Partido p39 = new Partido();
//		date = format.parse("Jun 26, 2018 18:00");
//		p39.setFecha(date);
//		p39.setEquipoLocal(getEquipoPorNombre("Nigeria", equipos));
//		p39.setEquipoVisitante(getEquipoPorNombre("Argentina", equipos));
//		Partido p40 = new Partido();
//		date = format.parse("Jun 26, 2018 18:00");
//		p40.setFecha(date);
//		p40.setEquipoLocal(getEquipoPorNombre("Islandia", equipos));
//		p40.setEquipoVisitante(getEquipoPorNombre("Croacia", equipos));
//		
//		//Partidos de la fecha 1
//		List<Partido> partidos1D = new ArrayList<Partido>();
//		List<Partido> partidos2D = new ArrayList<Partido>();
//		List<Partido> partidos3D = new ArrayList<Partido>();
//		partidos1D.add(p7);
//		partidos1D.add(p8);
//		partidos2D.add(p23);
//		partidos2D.add(p24);
//		partidos3D.add(p39);
//		partidos3D.add(p40);
//
//		Fecha fecha1D = new Fecha();
//		Fecha fecha2D = new Fecha();
//		Fecha fecha3D = new Fecha();
//		fecha1D.setNombre("Fecha 1");
//		fecha1D.setPartidos(partidos1D);
//		fecha2D.setNombre("Fecha 2");
//		fecha2D.setPartidos(partidos2D);
//		fecha3D.setNombre("Fecha 3");
//		fecha3D.setPartidos(partidos3D);
//
//		fechasD.add(fecha1D);
//		fechasD.add(fecha2D);
//		fechasD.add(fecha3D);
//		
//		grupoD.setFechas(fechasD);
//		//#######################################################################
//		
//		
//		//#######################################################################
//		//Grupo E
//		Grupo grupoE = new Grupo();
//		grupoE.setNombre("Grupo E");
//		grupoE.setCantEquipos(4);
//		List<Fecha> fechasE = new ArrayList<Fecha>();
//		
//		Partido p9 = new Partido();
//		date = format.parse("Jun 17, 2018 18:00");
//		p9.setFecha(date);
//		p9.setEquipoLocal(getEquipoPorNombre("Brasil", equipos));
//		p9.setEquipoVisitante(getEquipoPorNombre("Suiza", equipos));
//		
//		Partido p10 = new Partido();
//		date = format.parse("Jun 17, 2018 12:00");
//		p10.setFecha(date);
//		p10.setEquipoLocal(getEquipoPorNombre("Costa Rica", equipos));
//		p10.setEquipoVisitante(getEquipoPorNombre("Serbia", equipos));
//		
//		Partido p25 = new Partido();
//		date = format.parse("Jun 22, 2018 12:00");
//		p25.setFecha(date);
//		p25.setEquipoLocal(getEquipoPorNombre("Brasil", equipos));
//		p25.setEquipoVisitante(getEquipoPorNombre("Costa Rica", equipos));
//		
//		Partido p26 = new Partido();
//		date = format.parse("Jun 22, 2018 18:00");
//		p26.setFecha(date);
//		p26.setEquipoLocal(getEquipoPorNombre("Serbia", equipos));
//		p26.setEquipoVisitante(getEquipoPorNombre("Suiza", equipos));
//		
//		Partido p41 = new Partido();
//		date = format.parse("Jun 27, 2018 18:00");
//		p41.setFecha(date);
//		p41.setEquipoLocal(getEquipoPorNombre("Serbia", equipos));
//		p41.setEquipoVisitante(getEquipoPorNombre("Brasil", equipos));
//		
//		Partido p42 = new Partido();
//		date = format.parse("Jun 27, 2018 18:00");
//		p42.setFecha(date);
//		p42.setEquipoLocal(getEquipoPorNombre("Suiza", equipos));
//		p42.setEquipoVisitante(getEquipoPorNombre("Costa Rica", equipos));
//		
//		//Partidos de la fecha 1
//		List<Partido> partidos1E = new ArrayList<Partido>();
//		List<Partido> partidos2E = new ArrayList<Partido>();
//		List<Partido> partidos3E = new ArrayList<Partido>();
//		partidos1E.add(p9);
//		partidos1E.add(p10);
//		partidos2E.add(p25);
//		partidos2E.add(p26);
//		partidos3E.add(p41);
//		partidos3E.add(p42);
//
//		Fecha fecha1E = new Fecha();
//		Fecha fecha2E = new Fecha();
//		Fecha fecha3E = new Fecha();
//		fecha1E.setNombre("Fecha 1");
//		fecha1E.setPartidos(partidos1E);
//		fecha2E.setNombre("Fecha 2");
//		fecha2E.setPartidos(partidos2E);
//		fecha3E.setNombre("Fecha 3");
//		fecha3E.setPartidos(partidos3E);
//
//		fechasE.add(fecha1E);
//		fechasE.add(fecha2E);
//		fechasE.add(fecha3E);
//		
//		grupoE.setFechas(fechasE);
//		//#######################################################################
//		
//		
//		//#######################################################################
//		//Grupo F
//		Grupo grupoF = new Grupo();
//		grupoF.setNombre("Grupo F");
//		grupoF.setCantEquipos(4);
//		List<Fecha> fechasF = new ArrayList<Fecha>();
//		
//		Partido p11 = new Partido();
//		date = format.parse("Jun 17, 2018 15:00");
//		p11.setFecha(date);
//		p11.setEquipoLocal(getEquipoPorNombre("Alemania", equipos));
//		p11.setEquipoVisitante(getEquipoPorNombre("México", equipos));
//		
//		Partido p12 = new Partido();
//		date = format.parse("Jun 18, 2018 12:00");
//		p12.setFecha(date);
//		p12.setEquipoLocal(getEquipoPorNombre("Suecia", equipos));
//		p12.setEquipoVisitante(getEquipoPorNombre("República de Corea", equipos));
//		
//		Partido p27 = new Partido();
//		date = format.parse("Jun 23, 2018 18:00");
//		p27.setFecha(date);
//		p27.setEquipoLocal(getEquipoPorNombre("Alemania", equipos));
//		p27.setEquipoVisitante(getEquipoPorNombre("Suecia", equipos));
//		
//		Partido p28 = new Partido();
//		date = format.parse("Jun 23, 2018 15:00");
//		p28.setFecha(date);
//		p28.setEquipoLocal(getEquipoPorNombre("República de Corea", equipos));
//		p28.setEquipoVisitante(getEquipoPorNombre("México", equipos));
//		
//		Partido p43 = new Partido();
//		date = format.parse("Jun 27, 2018 14:00");
//		p43.setFecha(date);
//		p43.setEquipoLocal(getEquipoPorNombre("República de Corea", equipos));
//		p43.setEquipoVisitante(getEquipoPorNombre("Alemania", equipos));
//		
//		Partido p44 = new Partido();
//		date = format.parse("Jun 27, 2018 14:00");
//		p44.setFecha(date);
//		p44.setEquipoLocal(getEquipoPorNombre("México", equipos));
//		p44.setEquipoVisitante(getEquipoPorNombre("Suecia", equipos));
//		
//		//Partidos de la fecha 1
//		List<Partido> partidos1F = new ArrayList<Partido>();
//		List<Partido> partidos2F = new ArrayList<Partido>();
//		List<Partido> partidos3F = new ArrayList<Partido>();
//		partidos1F.add(p11);
//		partidos1F.add(p12);
//		partidos2F.add(p27);
//		partidos2F.add(p28);
//		partidos3F.add(p43);
//		partidos3F.add(p44);
//
//		Fecha fecha1F = new Fecha();
//		Fecha fecha2F = new Fecha();
//		Fecha fecha3F = new Fecha();
//		fecha1F.setNombre("Fecha 1");
//		fecha1F.setPartidos(partidos1F);
//		fecha2F.setNombre("Fecha 2");
//		fecha2F.setPartidos(partidos2F);
//		fecha3F.setNombre("Fecha 3");
//		fecha3F.setPartidos(partidos3F);
//
//		fechasF.add(fecha1F);
//		fechasF.add(fecha2F);
//		fechasF.add(fecha3F);
//		
//		grupoF.setFechas(fechasF);
//		//#######################################################################
//		
//		
//		//#######################################################################
//		//Grupo G
//		Grupo grupoG = new Grupo();
//		grupoG.setNombre("Grupo G");
//		grupoG.setCantEquipos(4);
//		List<Fecha> fechasG = new ArrayList<Fecha>();
//		
//		Partido p13 = new Partido();
//		date = format.parse("Jun 18, 2018 15:00");
//		p13.setFecha(date);
//		p13.setEquipoLocal(getEquipoPorNombre("Bélgica", equipos));
//		p13.setEquipoVisitante(getEquipoPorNombre("Panamá", equipos));
//		Partido p14 = new Partido();
//		date = format.parse("Jun 18, 2018 18:00");
//		p14.setFecha(date);
//		p14.setEquipoLocal(getEquipoPorNombre("Túnez", equipos));
//		p14.setEquipoVisitante(getEquipoPorNombre("Inglaterra", equipos));
//		
//		Partido p29 = new Partido();
//		date = format.parse("Jun 23, 2018 12:00");
//		p29.setFecha(date);
//		p29.setEquipoLocal(getEquipoPorNombre("Bélgica", equipos));
//		p29.setEquipoVisitante(getEquipoPorNombre("Túnez", equipos));
//		Partido p30 = new Partido();
//		date = format.parse("Jun 24, 2018 12:00");
//		p30.setFecha(date);
//		p30.setEquipoLocal(getEquipoPorNombre("Inglaterra", equipos));
//		p30.setEquipoVisitante(getEquipoPorNombre("Panamá", equipos));
//		
//		Partido p45 = new Partido();
//		date = format.parse("Jun 28, 2018 18:00");
//		p45.setFecha(date);
//		p45.setEquipoLocal(getEquipoPorNombre("Inglaterra", equipos));
//		p45.setEquipoVisitante(getEquipoPorNombre("Bélgica", equipos));
//		Partido p46 = new Partido();
//		date = format.parse("Jun 28, 2018 18:00");
//		p46.setFecha(date);
//		p46.setEquipoLocal(getEquipoPorNombre("Panamá", equipos));
//		p46.setEquipoVisitante(getEquipoPorNombre("Túnez", equipos));
//		//Partidos de la fecha
//		List<Partido> partidos = new ArrayList<Partido>();
//		partidos.add(p45);
//		partidos.add(p46);
//		
//		//Partidos de la fecha 1
//		List<Partido> partidos1G = new ArrayList<Partido>();
//		List<Partido> partidos2G = new ArrayList<Partido>();
//		List<Partido> partidos3G = new ArrayList<Partido>();
//		partidos1G.add(p13);
//		partidos1G.add(p14);
//		partidos2G.add(p29);
//		partidos2G.add(p30);
//		partidos3G.add(p45);
//		partidos3G.add(p46);
//
//		Fecha fecha1G = new Fecha();
//		Fecha fecha2G = new Fecha();
//		Fecha fecha3G = new Fecha();
//		fecha1G.setNombre("Fecha 1");
//		fecha1G.setPartidos(partidos1G);
//		fecha2G.setNombre("Fecha 2");
//		fecha2G.setPartidos(partidos2G);
//		fecha3G.setNombre("Fecha 3");
//		fecha3G.setPartidos(partidos3G);
//
//		fechasG.add(fecha1G);
//		fechasG.add(fecha2G);
//		fechasG.add(fecha3G);
//		
//		grupoG.setFechas(fechasG);
//		//#######################################################################
//		
//		
//		//#######################################################################
//		//Grupo H
//		Grupo grupoH = new Grupo();
//		grupoH.setNombre("Grupo H");
//		grupoH.setCantEquipos(4);
//		List<Fecha> fechasH = new ArrayList<Fecha>();
//		
//		Partido p15 = new Partido();
//		date = format.parse("Jun 19, 2018 15:00");
//		p15.setFecha(date);
//		p15.setEquipoLocal(getEquipoPorNombre("Polonia", equipos));
//		p15.setEquipoVisitante(getEquipoPorNombre("Senegal", equipos));
//		
//		Partido p16 = new Partido();
//		date = format.parse("Jun 19, 2018 12:00");
//		p16.setFecha(date);
//		p16.setEquipoLocal(getEquipoPorNombre("Colombia", equipos));
//		p16.setEquipoVisitante(getEquipoPorNombre("Japón", equipos));
//		
//		Partido p31 = new Partido();
//		date = format.parse("Jun 24, 2018 18:00");
//		p31.setFecha(date);
//		p31.setEquipoLocal(getEquipoPorNombre("Polonia", equipos));
//		p31.setEquipoVisitante(getEquipoPorNombre("Colombia", equipos));
//		
//		Partido p32 = new Partido();
//		date = format.parse("Jun 24, 2018 15:00");
//		p32.setFecha(date);
//		p32.setEquipoLocal(getEquipoPorNombre("Japón", equipos));
//		p32.setEquipoVisitante(getEquipoPorNombre("Senegal", equipos));
//		
//		Partido p47 = new Partido();
//		date = format.parse("Jun 28, 2018 14:00");
//		p47.setFecha(date);
//		p47.setEquipoLocal(getEquipoPorNombre("Japón", equipos));
//		p47.setEquipoVisitante(getEquipoPorNombre("Polonia", equipos));
//		
//		Partido p48 = new Partido();
//		date = format.parse("Jun 28, 2018 14:00");
//		p48.setFecha(date);
//		p48.setEquipoLocal(getEquipoPorNombre("Senegal", equipos));
//		p48.setEquipoVisitante(getEquipoPorNombre("Colombia", equipos));	
//		
//		//Partidos de la fecha 1
//		List<Partido> partidos1H = new ArrayList<Partido>();
//		List<Partido> partidos2H = new ArrayList<Partido>();
//		List<Partido> partidos3H = new ArrayList<Partido>();
//		partidos1H.add(p15);
//		partidos1H.add(p16);
//		partidos2H.add(p31);
//		partidos2H.add(p32);
//		partidos3H.add(p47);
//		partidos3H.add(p48);
//
//		Fecha fecha1H = new Fecha();
//		Fecha fecha2H = new Fecha();
//		Fecha fecha3H = new Fecha();
//		fecha1H.setNombre("Fecha 1");
//		fecha1H.setPartidos(partidos1H);
//		fecha2H.setNombre("Fecha 2");
//		fecha2H.setPartidos(partidos2H);
//		fecha3H.setNombre("Fecha 3");
//		fecha3H.setPartidos(partidos3H);
//
//		fechasH.add(fecha1H);
//		fechasH.add(fecha2H);
//		fechasH.add(fecha3H);
//		
//		grupoH.setFechas(fechasH);
//		//#######################################################################
//
//		List<Grupo> grupos = new ArrayList<Grupo>();
//		grupos.add(grupoA);
//		grupos.add(grupoB);
//		grupos.add(grupoC);
//		grupos.add(grupoD);
//		grupos.add(grupoE);
//		grupos.add(grupoF);
//		grupos.add(grupoG);
//		grupos.add(grupoH);
//		
//		wc2018.setGrupos(grupos);
//		
//		wc2018 = ejbTorneo.altaTorneo(wc2018);
//		
//		mensajeAMostrar = wc2018.toString();
//		
//	}
//	
//	private Equipo getEquipoPorNombre(String nombre, List<Equipo> equipos) {
//		Equipo encontrado = null;
//		
//		for (Equipo equipo : equipos) {
//			if (equipo.getNombre().equalsIgnoreCase(nombre)) {
//				encontrado = equipo;
//			}
//		}
//		
//		return encontrado;
//	}
//	
//}
