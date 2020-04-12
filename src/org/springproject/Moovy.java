package org.springproject;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.tinylog.Logger;

@Path("moovy")
public class Moovy{
	private String name;
	public Moovy (){
		name  = "unknown";
	}

	@GET
	@Path("hello")
	public String sayHello(@QueryParam("fname")String fname, @QueryParam("lname")String lname){
		if((fname!=null&&fname.length()>0)&&(lname!=null&&fname.length()>0)) {
			Logger.info("Got a proper name {} {}",fname,lname);
			return "hello, "+fname+" "+lname;
		}else {
			Logger.info("Did not get a proper name");
			return "hello, "+name;
		}
		
	}
	@POST
	@Path("form")
	public String processForm(@FormParam("vehicles")String vehicles,
							  @FormParam("title")String title, 
							  @FormParam("description")String description, 
							  @FormParam("price")Float price){
		Logger.warn("Used the old form");
		return String.format("The form for your %s has been accepted with title: %s, description: %s, price: %s",vehicles, title,description,price);
		
	}
	@POST
	@Path("form2")
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public String processForm2(
							 @FormDataParam("vehicles")String vehicles
							  ,@FormDataParam("title")String title
							  ,  @FormDataParam("description")String description 
							  , @FormDataParam("price")Float price
							  , @FormDataParam("picture")InputStream fileInputStream
							  , @FormDataParam("picture")FormDataContentDisposition fdcd
							  ){
		
		try {
			Logger.warn("Used the new form");
			Logger.info("Used the correct form and got the file of type {}",fdcd.getType());
			byte[] buffer = new byte[1024];
			File targetFile = File.createTempFile("tmp", null, new File("/Users/raaghavagarwal/Programming/MySpringProject/Upload"));
			//File targetFile = new File("/Users/raaghavagarwal/Programming/MySpringProject/Upload/targetFile.tmp");
			if(targetFile.exists()) {
				Logger.info("it exists");
			}else {
				Logger.info("Should create a file.");
				targetFile.createNewFile();
			}
			OutputStream outStream = new FileOutputStream(targetFile);
			int read=0;
			while((read = fileInputStream.read(buffer))!= -1){
				outStream.write(buffer, 0, read);
			}
			outStream.flush();
			outStream.close();
			fileInputStream.close();
			return String.format("The form for your %s has been accepted with title: %s, description: %s, price: %s, file %s [%d size]",vehicles, title,description,price
					,fdcd.getName(),fdcd.getSize()
					);
		}
		catch(Exception e)
		{
			Logger.error(e);
		}
		
		return "Something went wrong";
		
	}
}