/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reedhousesystems.maf;

import java.io.File;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.lucene.index.Term;

/**
 * REST Web Service
 *
 * @author mamello
 */
@Path("repo")
public class RepoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RepoResource
     */
    public RepoResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String buildRepo(@QueryParam("feed")String feed) throws IOException{
        
        ArrayList<Job> jobs = new ArrayList<Job>();
        feed = feed != null && !feed.trim().equals("") ? feed : "http://www.jobmail.co.za/rss";
        jobs = parseRSSFeed(new URL(feed));
        
        String indexresponses = jobs != null ? indexJobs(jobs) : "issues ";
        
        return "Successful : " + indexresponses; 
    }
    
    private String indexJobs(ArrayList<Job> thejobs) throws IOException {
            String response = "[Not Successful]";
		if(thejobs != null){
                        java.nio.file.Path indexpath = Paths.get("jobsindex");
			Directory indexDir = FSDirectory.open(indexpath);
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig conf = new IndexWriterConfig(analyzer);
			conf.setOpenMode(OpenMode.CREATE_OR_APPEND);						
			IndexWriter writer = new IndexWriter(indexDir, conf);
                        
                        IndexReader reader = DirectoryReader.open(indexDir);
                        int thenew = 0;
		
			for (Job one : thejobs){
                            Term indexTerm = new Term(ID, one.getId());
                            long numOfTerm = reader.docFreq(indexTerm);
                            
                            System.out.println(numOfTerm + " - freq for : " + indexTerm);
                            
                            if(numOfTerm <= 0){
				Document doc = new Document();				
				doc.add(new StringField(ID, one.getId(), Field.Store.YES));
				doc.add(new StringField(TITLE, one.getTitle(), Field.Store.YES));
				doc.add(new StringField(DATE, one.getDate(), Field.Store.YES));
				doc.add(new TextField(DESCRIPTION, one.getDescription(), Field.Store.YES));
				doc.add(new StringField(LINK, one.getLink(), Field.Store.YES));
				doc.add(new TextField(COMMENTS, one.getComments(), Field.Store.YES));
				writer.addDocument(doc);
                                
                                thenew++;
                            }else{
                                System.out.println("document already exists in the index");
                            }
			}
			
			System.out.println(response = "Added " + thenew + " jobs to the index of size (" + reader.maxDoc() +")");
			writer.close();
		}	                
            return response;
	}
    
    private ArrayList<Job> parseRSSFeed(URL thefeed) {		
            try {
                ArrayList <Job> jobs = new ArrayList<Job>();
                
                //handling a list of feeds
                
                HttpURLConnection urlCon = (HttpURLConnection) thefeed.openConnection (); 

                urlCon.setReadTimeout (5000);
                urlCon.connect ();
                
                System.out.println("just connected to the url " + thefeed);
                
                InputStream instream = urlCon.getInputStream ();
                
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                //org.w3c.dom.Document doc = factory.newDocumentBuilder().parse(new File("/data/Dev/themakana/TeleJobs/resources/jobs.xml"));
                org.w3c.dom.Document doc = factory.newDocumentBuilder().parse(instream);
                Element root = doc.getDocumentElement();

                XPath xPath = XPathFactory.newInstance().newXPath();

                XPathExpression expression = xPath.compile("//item");

                NodeList nl = (NodeList) expression.evaluate(root, XPathConstants.NODESET);

                System.out.println("Found the following number of items : " + nl.getLength());

                for (int a = 0; a < nl.getLength(); a++){
                        Node node = nl.item(a);

                        NodeList kids = node.getChildNodes();

                        Job tmp = new Job();

                        for(int b = 0; b < kids.getLength(); b++){
                                Node it = kids.item(b);

                                if(it.getNodeType() == Node.ELEMENT_NODE){

                                        switch(((Element)it).getNodeName().toLowerCase()){
                                                case ID: tmp.setId(((Element)it).getTextContent()); break;
                                                case TITLE: tmp.setTitle(((Element)it).getTextContent()); break;
                                                case LINK: tmp.setLink(((Element)it).getTextContent()); break;
                                                case COMMENTS: tmp.setComments(((Element)it).getTextContent()); break;
                                                case DESCRIPTION: tmp.setDescription(((Element)it).getTextContent()); break;
                                                case DATE: tmp.setDate(((Element)it).getTextContent()); break;
                                                case DATE1: tmp.setDate(((Element)it).getTextContent()); break;
                                                default: break;
                                        }
                                }
                        }

                        jobs.add(tmp);
                }

                return jobs;

            } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
                e.printStackTrace();
                return null;
            }		
	}
    
    final String ITEM = "item";
    final String TITLE = "title";
    final String LINK = "link";
    final String COMMENTS = "comments";
    final String DATE = "pubdate";
    final String DATE1 = "dc:date";
    final String ID = "guid";
    final String DESCRIPTION = "description";
    final String CREATOR = "dc:creator";
}
