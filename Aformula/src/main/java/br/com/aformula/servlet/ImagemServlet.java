package br.com.aformula.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "ImagemServlet", urlPatterns = {"/image"})
public class ImagemServlet extends HttpServlet {
  
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Decodifica o caminho recebido
	    String encodedPath = request.getParameter("path");
	    String imagePath = URLDecoder.decode(encodedPath, StandardCharsets.UTF_8.name());
	    
	    File imageFile = new File(imagePath);
	    if (imageFile.exists()) {
	        response.setContentType("image/jpeg");
	        try (FileInputStream fis = new FileInputStream(imageFile);
	             OutputStream os = response.getOutputStream()) {
	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = fis.read(buffer)) != -1) {
	                os.write(buffer, 0, bytesRead);
	            }
	        }
	    } else {
	        response.sendError(HttpServletResponse.SC_NOT_FOUND);
	    }
	}
}
