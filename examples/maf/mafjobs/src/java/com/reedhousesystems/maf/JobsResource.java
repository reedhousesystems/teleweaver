/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reedhousesystems.maf;

import java.io.IOException;
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
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * REST Web Service
 *
 * @author mamello
 */
@Path("jobs")
public class JobsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of JobsResource
     */
    public JobsResource() {
    }
    
    /**
     * This return a JSON array of all the jobs based on the constraints specified
     * @return JASONArray of the jobs
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Job> getTheJobs(@QueryParam("qualification")String qualification,
                                      @QueryParam("prefone")String prefone,
                                      @QueryParam("preftwo")String preftwo,
                                      @QueryParam("prefthree")String prefthree,
                                      @QueryParam("location")String location){
        
        ArrayList<Job> jobs = new ArrayList<Job>();

        try {
                Directory indexDir = FSDirectory.open(Paths.get("jobsindex"));
                IndexReader reader = DirectoryReader.open(indexDir);
                IndexSearcher searcher = new IndexSearcher(reader);
                Analyzer analyzer = new StandardAnalyzer();

                QueryParser parser = new QueryParser(DESCRIPTION,  analyzer);
                
                //basic sanitize
                qualification = (qualification != null && !qualification.equals("")) ? qualification : "";
                prefone = (prefone != null && !prefone.equals("")) ? prefone : "";
                preftwo = (preftwo != null && !preftwo.equals("")) ? preftwo : "";
                prefthree = (prefthree != null && !prefthree.equals("")) ? prefthree : "";
                location = (location != null && !location.equals("")) ? location : "";
                
                String constraints = qualification + " " + prefone + " " + preftwo + " " + prefthree + " " + location;
                
                System.out.println("Searching for term : \"" + constraints.trim() + "\", in index of size : " + reader.maxDoc());    
                
                Query query = (constraints.trim().equals("") ? parser.parse("job"): parser.parse(constraints));

                TopDocs results = searcher.search(query, 100);

                ScoreDoc[] hits = results.scoreDocs;

                System.out.println("number of hits : " + hits.length);

                for(int d = 0; d < hits.length; d++){
                        Document one = searcher.doc(hits[d].doc);
                        jobs.add(new Job(one.get(ID), one.get(TITLE), one.get(LINK), one.get(DATE), one.get(DESCRIPTION)));
                        System.out.println("> " + one.get(TITLE) + " | " + one.get(DATE) );
                }
        } catch (ParseException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }finally{
            return jobs;
        }
    }
    
    final String ITEM = "item";
    final String TITLE = "title";
    final String LINK = "link";
    final String COMMENTS = "comments";
    final String DATE = "pubdate";
    final String ID = "guid";
    final String DESCRIPTION = "description";
    final String CREATOR = "dc:creator";

    
}
