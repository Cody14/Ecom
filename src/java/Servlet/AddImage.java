/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import dao.ProductDao;
import domain.Product;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Gasana
 */
@MultipartConfig
@WebServlet(name = "AddImage", urlPatterns = {"/pages/AddImage"})
public class AddImage extends HttpServlet {

    
    private ProductDao productDao = new ProductDao();
    private Product product = new Product();
    List<Product> products = new ArrayList<>();
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
  
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);
       
                System.out.println("IN DO POST");
		Part file = request.getPart("image");
                Part sFile1 = request.getPart("image1");
                Part sFile2 = request.getPart("image2");
                Part sFile3 = request.getPart("image3");
                
		String imageFileName = file.getSubmittedFileName(); // get selected image
                String sideImageFileName1 = sFile1.getSubmittedFileName();
                String sideImageFileName2 = sFile2.getSubmittedFileName();
                String sideImageFileName3 = sFile3.getSubmittedFileName();
                
                products = productDao.findAll(Product.class);
                  if(!products.isEmpty()){
                     product = products.get(products.size()-1);  
                  
		imageFileName = "m-p"+product.getId()+"-"+product.getId()+".png";
                sideImageFileName1 = "s1-p"+product.getId()+"-"+product.getId()+".png";
                sideImageFileName2 = "s2-p"+product.getId()+"-"+product.getId()+".png";
                sideImageFileName3 = "s3-p"+product.getId()+"-"+product.getId()+".png";
                
		String uploadPath="C:/Users/Gasana/Documents/NetBeansProjects/E-Com/web/uploads/productImages/"+imageFileName; // upload path + iamge
                String uploadPathS1 = "C:/Users/Gasana/Documents/NetBeansProjects/E-Com/web/uploads/productImages/"+sideImageFileName1;
                String uploadPathS2 = "C:/Users/Gasana/Documents/NetBeansProjects/E-Com/web/uploads/productImages/"+sideImageFileName2;
                String uploadPathS3 = "C:/Users/Gasana/Documents/NetBeansProjects/E-Com/web/uploads/productImages/"+sideImageFileName3;
                
                
		String myPath="../uploads/productImages/m-p"+product.getId()+"-";
                String myPaths1="../uploads/productImages/s1-p"+product.getId()+"-";
                String myPaths2="../uploads/productImages/s2-p"+product.getId()+"-";
                String myPaths3="../uploads/productImages/s3-p"+product.getId()+"-";
                
                product.setMainPhotoUrl(myPath);
                product.setSidePhoto1Url(myPaths1);
                product.setSidePhoto2Url(myPaths2);
                product.setSidePhoto3Url(myPaths3);
                
                productDao.updateObject(product);
		
		// uploading selected image into images folder
		try {
			
			FileOutputStream fos = new FileOutputStream(uploadPath);
                        FileOutputStream fos1 = new FileOutputStream(uploadPathS1);
                        FileOutputStream fos2 = new FileOutputStream(uploadPathS2);
                        FileOutputStream fos3 = new FileOutputStream(uploadPathS3);
                        
			InputStream is = file.getInputStream();
                        InputStream is1 = sFile1.getInputStream();
                        InputStream is2 = sFile2.getInputStream();
                        InputStream is3 = sFile3.getInputStream();
			
			byte[] data = new byte[is.available()];
                        byte[] data1 = new byte[is1.available()];
                        byte[] data2 = new byte[is2.available()];
                        byte[] data3 = new byte[is3.available()];
                        
			is.read(data);
                        is1.read(data1);
                        is2.read(data2);
                        is3.read(data3);
                        
                        
			fos.write(data);
                        fos1.write(data1);
                        fos2.write(data2);
                        fos3.write(data3);
                        
			fos.close();
			fos1.close();
                        fos2.close();
                        fos3.close();   
		} catch (Exception e) {
			System.out.println("ERRRRRORRRRR DO POST TRY CATCH "+e);
		}
       
       
            }else{
                      // PRODUCT LIST EMPTY
                  }
                  
                  
                  
       
       
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
