package com.prashant.webCrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class crawlMain {

	protected static Document webdoc;
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private static String domain ;
	private static String host ;

	private static HashMap<String, String> mapInternalUrl = new HashMap<>();
	private static HashMap<String, String> mapImages = new HashMap<>();
	private static HashMap<String, String> mapExternalUrl = new HashMap<>();

	public static void main(String[] args) {
		String url = "";
		// Set the default url, in-case of no user input.
		if (args.length<1){
			url = "http://wiprodigital.com/";
		}
		startCrawl(url);
	}

	/**
	 * This method will initiate the crawl, with given url
	 * And will get all the url details (External and Internal)
	 * @param firstUrl
	 */
	private static void startCrawl(String firstUrl) {
		if (!loadDocFromUrl(firstUrl)){
			System.out.println("Error : Could Not load the Url");
		} else{
			configure(firstUrl);
			getAllLinks();
			getAllImages();
			createXmlMap();

		}
	}

	/**
	 * This method is get the host of given url
	 * @param url
	 */
	private static void configure(String url){
		domain = url;
		try {
			host = new URI(domain).getHost();
		} catch (URISyntaxException e) {
			System.out.println("Error in getting url "+e);
		}
	}

	/**
	 * In this method the internal and external links will be fetched from jsoup doc
	 */
	private static void getAllLinks(){

		// Title Page
		String strTitel=webdoc.title();
		System.out.println("\n Title : "+strTitel);

		// Get data from href attribute
		Elements eAllLinks = webdoc.select("a[href]");
		for (Element link : eAllLinks) {
			URI uri;
			try {
				uri = new URI(link.attr("href"));
				if (Objects.equals(uri.getHost(), host)){
					System.out.println("Internal Link : " + link.attr("href").trim().toString()+",  Data :"+link.text());
					mapInternalUrl.put(link.text(),link.attr("href").trim().toString());
				} else{
					mapExternalUrl.put(link.text(),link.attr("href").trim().toString());
				}

			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * In this method image links will be fetched from jsoup doc
	 */
	private static void getAllImages(){
		Elements images = webdoc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
		for (Element image : images) {
			System.out.println("Image Link : " + image.attr("src") +" , alt : " + image.attr("alt"));
			mapImages.put(image.attr("alt").toString(),image.attr("src").toString());
		}
	}


	/**
	 * The Document will be created from url with all the detail of url.
	 * @param url
	 * @return
	 */
	public static boolean loadDocFromUrl(String url) {
		Connection connection = null;
		try{
			connection = Jsoup.connect(url).userAgent(USER_AGENT);
			webdoc = connection.get();
			// 200 is the HTTP OK status code
			if(connection.response().statusCode() == 200){
				System.out.println("\nReceived page : " + url);
				return true;
			}
			if(!connection.response().contentType().contains("text/html")){
				System.out.println("ERROR :: Unable to retrieve HTML");
				return false;
			}

			if(webdoc == null)
			{
				System.out.println("ERROR! Document returned null");
				return false;
			}
			return true;
		} catch (IOException eIO){
			System.out.println("Error : "+eIO);
			return false;
		}
	}

	/**
	 * This method will create the sitemap
	 */
	private static void createXmlMap(){
		try {
			String siteMapName = "sitemap.xml";
			File file = new File(siteMapName);
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			String xmlBody = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
			out.write(xmlBody);
			out.write("<sitemap>");
			for (String extKey : mapExternalUrl.keySet()){
				if(mapExternalUrl.get(extKey) != null){
					String extLink = "\n<externalLink>\n\t<url>"+mapExternalUrl.get(extKey)+"s</url>\n</externalLink>";
					out.write(extLink);
				}
			}
			for (String intKey : mapInternalUrl.keySet()){
				if(mapInternalUrl.get(intKey) != null){
					String intLink = "\n<internalLink>\n\t<url>"+mapInternalUrl.get(intKey)+"</url>\n</internalLink>";
					out.write(intLink);
				}
			}
			for (String imgKey : mapImages.keySet()){
				if(mapImages.get(imgKey) != null){
					String imgLink = "\n<imagesLink>\n\t<url>"+mapImages.get(imgKey)+"</url>\n</imagesLink>";
					out.write(imgLink);
				}
			}

			out.write("\n</sitemap>");
			out.close();
			System.out.println("\n"+file.getAbsolutePath()+" is created.");
		} catch (IOException e) {
			System.out.println("Unable to create sitemap");
		}
	}
}
