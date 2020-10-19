package com.supplyco.jsonqr.controller;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.supplyco.jsonqr.model.Contents;
import com.supplyco.jsonqr.model.UploadFile;

@Controller
public class HomeController {

	@RequestMapping(value = "/")
	public ModelAndView test(HttpServletResponse response) {

		return new ModelAndView("invoicebuild");
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public void post(HttpServletResponse response, @RequestParam("filejson") CommonsMultipartFile[] file,
			@RequestParam("fileinvoice") CommonsMultipartFile[] fileinvoice) throws IOException {

		if (file != null && file.length > 0) {
			for (CommonsMultipartFile aFile : file) {

				System.out.println("Saving file: " + aFile.getOriginalFilename());

				UploadFile uploadFile = new UploadFile();
				uploadFile.setFileName(aFile.getOriginalFilename());
				uploadFile.setData(aFile.getBytes());

				BufferedReader br;
				List<String> result = new ArrayList<String>();
				try {

					String line;
					InputStream is = aFile.getInputStream();
					br = new BufferedReader(new InputStreamReader(is));
					while ((line = br.readLine()) != null) {
						result.add(line);
					}

				} catch (IOException e) {
					System.err.println(e.getMessage());
				}

				for (String jsonstr : result) {

					JsonParser parser = new JsonParser();
					JsonElement rootNode = parser.parse(jsonstr);

					if (rootNode.isJsonObject()) {
						JsonObject details = rootNode.getAsJsonObject();
						JsonElement nameNode = details.get("SignedInvoice");
						System.out.println("SignedInvoice : " + nameNode.getAsString());
					}
				}
			}
		}

		// invoice upload

		if (fileinvoice != null && fileinvoice.length > 0) {
			for (CommonsMultipartFile aFile : fileinvoice) {

				System.out.println("Saving file: " + aFile.getOriginalFilename());

				UploadFile uploadFile = new UploadFile();
				uploadFile.setFileName(aFile.getOriginalFilename());
				uploadFile.setData(aFile.getBytes());
				
				String[] regex = uploadFile.getFileName().split("ISSUE");
				
				String sub = regex[1].substring(0, regex[1].length()-4);
				
				System.out.println("sub isu "+sub);
		
				for(String s : regex) {
					System.out.println("Regex : "+s);
				}
				
				int isuno = Integer.parseInt(sub);
				
				int lastno = (Math.abs(isuno) % 10)+1;
				
				System.out.println("Last isu digit :-------"+lastno );
				

				BufferedReader br;
				List<String> result = new ArrayList<String>();
				try {

					String line;
					InputStream is = aFile.getInputStream();
					br = new BufferedReader(new InputStreamReader(is));
					while ((line = br.readLine()) != null) {
						result.add(line);
					}

				} catch (IOException e) {
					System.err.println(e.getMessage());
				}

				List<String> li = new ArrayList<String>();

				for (String str : result) {



					li.add(Decode(lastno,0, str) + "\n");
				}
				
				for(String s : li) {
					System.out.println("-->"+s);
				}
				// PDF(li);
				
				
				pdfprint(li);
			}
		}

		showPdf(response);
	}

	public String Decode(int last,int a, String msg) {
		
		String Decriptedmsg = "";
		
		if (last==10) {
			last=5;
		}
		
	    if(a == 0){
	        StringBuffer sb = new StringBuffer(msg);
	        for(int i = 0; i < sb.length(); i++){
	            int temp = 0;
	            temp = (int)sb.charAt(i);
	            temp = temp - last;
	            sb.setCharAt(i, (char)temp);
	            Decriptedmsg = sb.toString();
	        }
	        //jTextArea2.setText(EncryptedMsg);
	   }
	    else if(a == 1){
	        //jTextArea1.setText("");
	        //jTextArea2.setText("");

	    }
	    return Decriptedmsg;}

	public void pdfprint(List<String> body) {

		String[] tableTitleList = { "Slno", "ItemCode", "Commodity", "Unit", "Qty", "Bags", "S.Rate", "Disc%", "Tax%",
				"RateWtax", "MRP", "SaleValue" };
		Document document = new Document();
		BaseColor myColor = WebColors.getRGBColor("#A00000");
		document.setPageSize(PageSize.A4.rotate());

		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("E:\\Einvoice.pdf"));
			document.open();

			// Add Image
		//	Image image1 = Image.getInstance("/home/codergap/vishnu/QRCode.png");
			Image image1 = Image.getInstance("E:\\QRCode.png");
			// Fixed Positioning
			image1.setAbsolutePosition(50f, 435f);
			// Scale to new height and new width of image
			image1.scaleAbsolute(160, 160);
			// Add to document
			document.add(image1);

			float[] columnWidths = { 3f, 7f, 20f, 3f, 3f, 3f, 5f, 5f, 5f, 6f, 6f, 6f };
			PdfPTable table = new PdfPTable(columnWidths);
			table.setWidthPercentage(100);
			
			float[] columnWidths2 = { 6f, 6f};
			PdfPTable table2 = new PdfPTable(columnWidths2);
			table2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.setWidthPercentage(25);
			
			// PdfPTable table = new PdfPTable(11); // 3 columns.
			Paragraph p, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19;

			p1 = new Paragraph();
			p1.add(new Chunk(body.get(0), FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));

			p2 = new Paragraph();
			p2.add(body.get(1));

			p3 = new Paragraph();
			p3.add(body.get(2));

			p4 = new Paragraph();
			p4.add(body.get(3));

			p5 = new Paragraph();
			p5.add(body.get(4));

			p6 = new Paragraph();
			p6.add(body.get(5));

			Chunk linebreak = new Chunk(new DottedLineSeparator());

			p7 = new Paragraph();

			p8 = new Paragraph();
			p8.add(" ");
			
			p9 = new Paragraph();
			
			p10 = new Paragraph();
			p10.add(" ");
			
			p11 = new Paragraph();
			p11.add("Name and Signature of the authority");

			List<String> head = Arrays.asList(tableTitleList);

			for (String title : head) {
				table.addCell(title);
			}

			int i = 0;
			int j = 0;
			int itemcount = 0;
			String[] perticulars = new String[20];
			for (Object li : body) {

				List<String> columsData;

				if(i==7) {
					p7.add(li.toString());
				}
				
				if (i > 8 && i < 15) {
					p7.add(li.toString());
				}
				if (i > 15) {

					char c = li.toString().trim().charAt(0);
					String cc = Character.toString(c);

					try {
						int check = Integer.parseInt(cc);

						perticulars[j] = li.toString();
						columsData = Arrays.asList(li.toString().split(","));

						int columcount = 12;
						int columckeck = 0;
						for (String s : columsData) {

							if (columckeck < columcount)
								table.addCell(s);

							columckeck++;
						}

					} catch (Exception e) {
						// TODO: handle exception

						System.out.println(e.getMessage());
					}

					j++;
				}
				i++;

			}

			System.out.println("body total : " + body.size());

			List<String> content = new ArrayList<String>();
			int n =0;
			int lastf = body.size() - 9;
			do {
				content.add(body.get(lastf));
				p9.add(body.get(lastf));
				lastf++;
				n++;
			} while (lastf < body.size());
			
			List<Contents> l2 = setContentTable(content) ;
			
			for(Contents s : l2) {
				System.out.println("lable-->"+s.getLable());
				System.out.println("Amont-->"+s.getAmount().trim());
				
				table2.addCell(s.getLable());
				
				table2.addCell(s.getAmount());
			}
			

		

			p1.setAlignment(Element.ALIGN_CENTER);
			p2.setAlignment(Element.ALIGN_CENTER);
			p3.setAlignment(Element.ALIGN_CENTER);
			p4.setAlignment(Element.ALIGN_CENTER);
			p5.setAlignment(Element.ALIGN_CENTER);
			p6.setAlignment(Element.ALIGN_CENTER);
			p9.setAlignment(Element.ALIGN_RIGHT);
//			p11.setAlignment(Element.ALIGN_RIGHT);
			

			document.add(p1);
			document.add(p2);
			document.add(p3);
			document.add(p4);
			document.add(p5);
			document.add(p6);
			document.add(linebreak);
			document.add(p7);
			document.add(p8);
			document.add(table);
			document.add(p10);
			document.add(table2);
			document.add(p11);

//		 
//		    String imageUrl = "file:\\E:\\QRCode.png";
//		    Image image2 = Image.getInstance(new URL(imageUrl));
//		    document.add(image2);

			document.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Contents> setContentTable(List<String> content) {
		
		List<Contents> li = new ArrayList<Contents>();
		
		for(String s : content) {
			Contents cnts = new Contents();
			
			String[] regex =s.split(":");  
			
			cnts.setLable(regex[0]);
			cnts.setAmount(regex[1]);
			li.add(cnts);
		}
		
		return li;
	}

	public void showPdf(HttpServletResponse response) throws IOException {
	    response.setContentType("application/pdf");
	    InputStream inputStream = new FileInputStream(new File("E:\\Einvoice.pdf"));
	    int nRead;
	    while ((nRead = inputStream.read()) != -1) {
	        response.getWriter().write(nRead);
	    }
	} 
}
