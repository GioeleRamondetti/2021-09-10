package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	public Model() {
		
	}
	private YelpDao dao=new YelpDao();
	private Graph<Business,DefaultWeightedEdge> grafo;
	private double distanzaMax=0;
	private int narchi;
	private int nvertici;
	private Set<Business> resStars=new HashSet<>();
	private List<Business> best;
	private double distanzatot=0;
	public List<String> listacitta(){
		return dao.getAllcity();
	}
	
	public List<Business> listacitta(String citta){
		return dao.getAllLocalsInCIty(citta);
	}
	
	public int getNarchi() {
		return narchi;
	}

	public int getNvertici() {
		return nvertici;
	}

	public String distanzaMax(Business b,String citta){
		double max=-1;
		String locale="";
		for(Business c2 : Graphs.neighborListOf(this.grafo,b)) {
			double peso = LatLngTool.distance(new LatLng(b.getLatitude(), b.getLongitude()),new LatLng(c2.getLatitude(), c2.getLongitude()), LengthUnit.KILOMETER);
			if(peso>max) {
				max=peso;
				locale=c2.getBusinessName();
			}
		}
		String finale=locale+" ="+max+" km";
		return finale;
	}
	
	public List<Business> listaocalicitta(String citta){
		return dao.getAllLocalsInCIty(citta);
	}
	
	public void creagrafo(String citta) {
		double max=-2;
		this.grafo=new SimpleWeightedGraph<Business,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.getAllLocalsInCIty(citta));
		
		for(Business c1 : dao.getAllLocalsInCIty(citta)) {
			for(Business c2 : dao.getAllLocalsInCIty(citta)) {
				if(!c1.equals(c2)) {
					double peso = LatLngTool.distance(new LatLng(c1.getLatitude(), c1.getLongitude()),new LatLng(c2.getLatitude(), c2.getLongitude()), LengthUnit.KILOMETER);
					Graphs.addEdge(this.grafo, c1, c2, peso);
					if(peso>max) {
						max=peso;
					}
				}
			}
		}
		//this.distanzaMax=max;
		this.narchi=grafo.edgeSet().size();
		this.nvertici=grafo.vertexSet().size();
	}

	private Double distanzaMax() {
		return this.distanzaMax;
	}

	public List<String> listaocalicittaString(String value) {
		// TODO Auto-generated method stub
		return dao.getAllLocaliInCIty(value);
	}

	public Business getB(String value,String citta) {
		
		return dao.getBusinessbyName(value,citta);
	}
	
	
	public void getArchiMaggiorePesoMedio(int mediaRec){
		for(Business a: grafo.vertexSet()) {
			if(a.getStars()>mediaRec) {
				this.resStars.add(a);
				
			}
		}
		
	}
	
	public double getDistanzatot() {
		return distanzatot;
	}



	public List<Business> getpercorsomax(Business sorgente,Business destinazione,int mediaRec ){
		best=new LinkedList<>();
		getArchiMaggiorePesoMedio(mediaRec);
		List<Business> parziale =new LinkedList<>();
		parziale.add(sorgente);
		cerca(parziale,destinazione);
		return this.best;
	}
	private void cerca(List<Business> parziale,Business destinazione) {
		// condizione terminazione
		if(parziale.get(parziale.size()-1).equals(destinazione)) {
			if(parziale.size()>best.size()) {
				best=new ArrayList<>(parziale);
			}
			return;
			
		}
		// scorro i vicini dell'ultimo inserito ed esploro
		 for(Business v :Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			 if(!parziale.contains(v) && this.resStars.contains(v)) {
				 // evito cicli
				 parziale.add(v);
				 this.distanzatot= this.distanzatot+ grafo.getEdgeWeight(grafo.getEdge(v, parziale.get(parziale.size()-1)));
				 cerca(parziale, destinazione);
				 parziale.remove(parziale.size()-1);
				 this.distanzatot= this.distanzatot- grafo.getEdgeWeight(grafo.getEdge(v, parziale.get(parziale.size()-1)));
				 }
		 }
		 
		
	}

	
	
	
}
